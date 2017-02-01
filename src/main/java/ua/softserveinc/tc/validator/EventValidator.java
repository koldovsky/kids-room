package ua.softserveinc.tc.validator;


import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;

import java.util.List;

/**
 * Validator to validation the given list of EventDto objects for correctness.
 * The correctness means that start and end date are not null, have correct
 * format, start date are not in the past, end date are not before start date,
 * start and end dates are in the range of workings hours.
 *
 */
public interface EventValidator {

    /**
     * Checks EventDto for errors that manager can not fix without the help of an administrator
     * Return list of error messages. If there are no errors then empty list will be returned
     *
     * @param eventDto
     * @return list of errors messages
     */
    List<String> generalEventValidation(EventDto eventDto);

    /**
     * Validate RecurentEventDto and returns the list of errors messages that occurred.
     * If there areno errors then empty list will be returned
     *
     * @param  recurrentEventDto
     * @return list of errors messages
     */
    List<String> reccurentEventValidation(RecurrentEventDto recurrentEventDto);

    /**
     * Validate EventDto and returns the list of errors messages that occurred. If there are
     * no errors then empty list will be returned
     *
     * @param eventDto
     * @return list of errors messages
     */
    List<String> singleEventValidation(EventDto eventDto);

    /**
     * Validate RecurentEventDto and returns the list of errors messages that occurred.
     * If there areno errors then empty list will be returned
     *
     * @param  monthlyEventDto
     * @return list of errors messages
     */
    List<String> monthlyEventValidation(MonthlyEventDto monthlyEventDto);

}
