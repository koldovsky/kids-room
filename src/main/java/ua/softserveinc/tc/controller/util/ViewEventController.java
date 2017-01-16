package ua.softserveinc.tc.controller.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.softserveinc.tc.constants.*;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.EventService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.EventValidator;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class ViewEventController {

    @Autowired
    GenericMapper<Event, EventDto> genericMapper;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private EventValidator eventValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EventService eventService;


    @GetMapping("/")
    public final String viewHome(Model model, Principal principal) {

        String resultView;

        if (principal == null) {
            return "entrypoint";
        }
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        switch (user.getRole()) {
            case USER:
                model.addAttribute(UserConstants.Entity.ROOMS, roomService.getTodayActiveRooms());
                model.addAttribute(UserConstants.Entity.KIDS, userService.getEnabledChildren(user));
                model.addAttribute(UserConstants.Entity.USERID, user.getId());
                if (user.getChildren().isEmpty() || userService.getEnabledChildren(user).isEmpty()) {
                    resultView = ChildConstants.View.MY_KIDS;
                } else
                    resultView = EventConstants.View.MAIN_PAGE;
                break;
            case MANAGER:
                model.addAttribute(UserConstants.Entity.ROOMS, userService.getActiveRooms(user));
                resultView = EventConstants.View.MAIN_PAGE;
                break;
            default:
                model.addAttribute(AdminConstants.ROOM_LIST, roomService.findAll());
                resultView = AdminConstants.EDIT_ROOM;
        }
        return resultView;
    }


    @GetMapping(value = "getevents/{id}", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getEvents(@PathVariable int id) {
        return new Gson().toJson(calendarService.findEventByRoomId(id));
    }


    @PostMapping(value = "getnewevent", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getAjax(@RequestBody EventDto eventDto) {
        if (eventValidator.logicValidEvent(eventDto).isEmpty()) {
            if (eventValidator.generalValidateEvent(eventDto).isEmpty()) {
                calendarService.create(genericMapper.toEntity(eventDto));
                return ResponseEntity.status(HttpStatus.OK)
                    .body(new Gson().toJson(eventDto));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(localizateMessage(eventValidator.generalValidateEvent(eventDto)).toString());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .body(localizateMessage(eventValidator.logicValidEvent(eventDto)).toString());
    }

    @PostMapping(value = "geteventforupdate", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getEventForUpdate(@RequestBody EventDto eventDto) {
        if (eventValidator.logicValidEvent(eventDto).isEmpty()) {
            if (eventValidator.generalValidateEvent(eventDto).isEmpty()) {
                calendarService.updateEvent(genericMapper.toEntity(eventDto));
                return ResponseEntity.status(HttpStatus.OK)
                    .body(new Gson().toJson(eventDto));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(localizateMessage(eventValidator.generalValidateEvent(eventDto)).toString());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(localizateMessage(eventValidator.logicValidEvent(eventDto)).toString());
    }

    @PostMapping("geteventfordelete")
    @ResponseStatus(HttpStatus.OK)
    public void getEventForDelete(@RequestBody EventDto eventDto) {
        calendarService.deleteEvent(genericMapper.toEntity(eventDto));
    }

    @PostMapping(value = "getrecurrentevents", produces = "application/json")
    public ResponseEntity<String> getRecurrent(@RequestBody RecurrentEventDto recurrentEventDto, BindingResult bindingResult) {
        eventValidator.validate(recurrentEventDto, bindingResult);
        ResponseEntity<String> response;
        if (eventValidator.isReccurrentValid(recurrentEventDto)) {
            if (bindingResult.hasErrors()) {
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new Gson().toJson(bindingResult.getFieldError().getCode()));
            } else {
                if (recurrentEventDto.getRecurrentId() != null) {
                    eventService.deleteRecurrentEvent(recurrentEventDto.getRecurrentId());
                }
                response = ResponseEntity.status(HttpStatus.OK).body(
                        new Gson().toJson(calendarService.createRecurrentEvents(recurrentEventDto)));
            }
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ValidationConstants.EVENT_RECCURRENT_END_MUST_BIGER_ONE_DAY_MSG);
        }
        return response;
    }

    @PostMapping(value = "getmonthlyevents", produces = "application/json")
    public ResponseEntity<String> getMonthly(@RequestBody MonthlyEventDto monthlyEventDto,
                                             BindingResult bindingResult) {
        eventValidator.validate(monthlyEventDto, bindingResult);
        ResponseEntity<String> response;
        if (bindingResult.hasErrors()) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Gson().toJson(bindingResult.getFieldError().getCode()));
        } else {
            if (monthlyEventDto.getRecurrentId() != null) {
                eventService.deleteRecurrentEvent(monthlyEventDto.getRecurrentId());
            }
            response = ResponseEntity.status(HttpStatus.OK).body(
                    new Gson().toJson(calendarService.createMonthlyEvents(monthlyEventDto)));
        }
        return response;
    }

    @GetMapping(value = "getroomproperty/{id}", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getRoomProperty(@PathVariable long id) {
        return calendarService.getRoomWorkingHours(id) + " " + calendarService.getRoomCapacity(id);
    }

    @GetMapping(value = "getRecurrentEventForEditing/{recurrentEventId}",
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getRecurrentBookingForEditing(@PathVariable Long recurrentEventId) {
        return new Gson().toJson(calendarService.getRecurrentEventForEditingById(recurrentEventId));
    }

    private List<String> localizateMessage (List<String> noLocalMessages){
        List<String> localMessages = new ArrayList<>();
         Locale locale = (Locale) request.getSession()
            .getAttribute(LocaleConstants.SESSION_LOCALE_ATTRIBUTE);
        locale = (locale == null) ? request.getLocale() : locale;
        for (String messages : noLocalMessages)
        localMessages.add( messageSource.getMessage(messages,null, locale));
        return localMessages;
    }

}
