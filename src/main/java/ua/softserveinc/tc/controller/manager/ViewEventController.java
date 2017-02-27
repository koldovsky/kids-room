package ua.softserveinc.tc.controller.manager;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.*;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.service.CalendarService;
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
    private GenericMapper<Event, EventDto> genericMapper;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Environment environment;

    @Autowired
    private EventValidator eventValidator;

    @GetMapping("/")
    public final String viewHome(Model model, Principal principal) {

        String resultView;
        String email;
        User user;

        if (principal == null) {
            model.addAttribute(EntryConstants.LOGIN_PROP, environment.getProperty("multiple.choose.login"));
            return EntryConstants.ENTRY_POINT;
        }

        email = principal.getName();
        user = userService.getUserByEmail(email);

        switch (user.getRole()) {
            case USER:
                model.addAttribute(UserConstants.Entity.ROOMS, roomService.getTodayActiveRooms());
                model.addAttribute(UserConstants.Entity.KIDS, userService.getEnabledChildren(user));
                model.addAttribute(UserConstants.Entity.USERID, user.getId());
                if (user.getChildren().isEmpty() || userService.getEnabledChildren(user).isEmpty()) {
                    resultView = ChildConstants.View.MY_KIDS;
                } else {
                    resultView = EventConstants.View.MAIN_PAGE;
                }
                break;
            case MANAGER:
                resultView = "redirect:/"+ManagerConstants.MANAGER_EDIT_BOOKING;
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


    @PostMapping(value = "createsingleevent", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> createSingleEvent(@RequestBody EventDto eventDto, Locale locale) {
        return getResponseEntityForSingleEvent(eventDto, locale);
    }

    @PostMapping(value = "geteventforupdate", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getEventForUpdate(@RequestBody EventDto eventDto, Locale locale) {
        return getResponseEntityForSingleEvent(eventDto, locale);
    }

    @PostMapping("geteventfordelete")
    @ResponseStatus(HttpStatus.OK)
    public void getEventForDelete(@RequestBody EventDto eventDto) {
        calendarService.deleteEvent(genericMapper.toEntity(eventDto));
    }

    @PostMapping(value = "getrecurrentevents", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getRecurrent(@RequestBody RecurrentEventDto recurrentEventDto, Locale locale) {
        if (eventValidator.generalEventValidation(recurrentEventDto).isEmpty()) {
            if (eventValidator.reccurentEventValidation(recurrentEventDto).isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Gson().toJson(calendarService.createRecurrentEvents(recurrentEventDto)));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(localizateMessage(eventValidator.reccurentEventValidation(recurrentEventDto), locale).toString());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(localizateMessage(eventValidator.generalEventValidation(recurrentEventDto), locale).toString());
    }

    @PostMapping(value = "getmonthlyevents", produces = "application/json")
    public ResponseEntity<String> getMonthly(@RequestBody MonthlyEventDto monthlyEventDto, Locale locale) {
        if (eventValidator.generalEventValidation(monthlyEventDto).isEmpty()) {
            if (eventValidator.monthlyEventValidation(monthlyEventDto).isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Gson().toJson(calendarService.createMonthlyEvents(monthlyEventDto)));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(localizateMessage(eventValidator.monthlyEventValidation(monthlyEventDto), locale).toString());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(localizateMessage(eventValidator.generalEventValidation(monthlyEventDto), locale).toString());

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


    private ResponseEntity<String> getResponseEntityForSingleEvent(EventDto eventDto, Locale locale) {
        List<String> generalErrorMessage = eventValidator.generalEventValidation(eventDto);
        List<String> singleErrorMessage = eventValidator.singleEventValidation(eventDto);
        if (generalErrorMessage.isEmpty()) {
            if (singleErrorMessage.isEmpty()) {
                calendarService.createOrUpdateEvent(genericMapper.toEntity(eventDto));
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(eventDto));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(localizateMessage(singleErrorMessage, locale).toString());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(localizateMessage(generalErrorMessage, locale).toString());
    }

    private List<String> localizateMessage(List<String> noLocalMessages, Locale locale) {
        List<String> localMessages = new ArrayList<>();
        noLocalMessages.forEach((message) ->
                localMessages.add(messageSource.getMessage(message, null, locale)));
        return localMessages;
    }

}
