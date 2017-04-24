package ua.softserveinc.tc.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.DayDiscountService;
import ua.softserveinc.tc.service.PersonalDiscountService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/restful/discount")
public class DiscountController {

    @Autowired
    private DayDiscountService dayDiscountService;

    @Autowired
    private PersonalDiscountService personalDiscountService;

    @Autowired
    private UserService userService;

    @GetMapping("/{startDate}/{endDate}/{startTime}/{endTime}")
    public List<DayDiscountDTO> getDiscountsForCurrentPeriod(@PathVariable String startDate,
                                                             @PathVariable String endDate,
                                                             @PathVariable String startTime,
                                                             @PathVariable String endTime) {

        return dayDiscountService.getDayDiscountsForPeriod(LocalDate.parse(startDate), LocalDate.parse(endDate),
                LocalTime.parse(startTime), LocalTime.parse(endTime), true);
    }

    @GetMapping("/all")
    public List<DayDiscountDTO> getAllDailyDiscounts() {

        return dayDiscountService.findAllDailyDiscounts();
    }

    @GetMapping("/personal-discount/{userId}")
    public List<PersonalDiscountDTO> getPersonalDiscounts(@PathVariable Long userId) {

        return personalDiscountService.findPersonalDiscountByUserId(userId);
    }

    @GetMapping("/personal-discount")
    public List getPersonalDiscounts(Principal principal) {
        User currentUser = userService.getUserByEmail(principal.getName());

        return personalDiscountService.findPersonalDiscountByUserId(currentUser.getId());
    }
}
