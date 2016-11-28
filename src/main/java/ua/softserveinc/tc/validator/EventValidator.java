package ua.softserveinc.tc.validator;


import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.EventDto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;


@Component
public class EventValidator {

    private Integer dateEnd;
    private Integer dateStart;
    private Integer timeStart;
    private Integer timeEnd;
    private Integer dateNow;
    private Integer timeNow;


    public  boolean isSingleEventValid(EventDto eventDto) {
        return errorSingleMessage(eventDto).length() == 0;
    }

    private void dateSet(EventDto eventDto) {
        String nowDate= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        dateStart = Integer.parseInt(eventDto.getEndTime().substring(0,10).replace("-",""));
        dateEnd = Integer.parseInt(eventDto.getStartTime().substring(0,10).replace("-",""));
        timeStart = Integer.parseInt(eventDto.getStartTime().substring(11,16).replace(":",""));
        timeEnd = Integer.parseInt(eventDto.getEndTime().substring(11,16).replace(":",""));
        dateNow = Integer.parseInt(nowDate.substring(0,10).replace("-",""));
        timeNow = Integer.parseInt(nowDate.substring(11,16).replace(":",""));
    }

    public  String errorSingleMessage(EventDto eventDto)
    {
       dateSet(eventDto);
        StringBuilder stringBuilder= new StringBuilder();
        if (eventDto.getName().length() == 0) {
            stringBuilder.append(ValidationConstants.EVENT_EMPTY_TITLE_MSG);
        }
        if ((dateStart == null) || (dateEnd == null)) {
            stringBuilder.append("Empty data" + "\n");
        } else if( (dateStart < dateNow) && (timeStart < dateNow) ) {
            stringBuilder.append("Must create new event in Present time" + "\n");
        } else if (!Objects.equals(dateStart, dateEnd))
        {
            stringBuilder.append(ValidationConstants.EVENT_START_NOT_EQUALS_END_MSG);
        } else if (timeStart >= timeEnd) {
            stringBuilder.append(ValidationConstants.EVENT_START_TIME_BIGGER_END_MSG+"\n");
        }
        return stringBuilder.toString();
    }

}