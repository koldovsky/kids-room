package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.InfoDeactivateRoomDto;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.User;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public interface MailService {

    void sendMessage(String email, String subject,
                     String text) throws MessagingException;

    void sendPaymentInfo(User user, String subject,
                         Long sumTotal) throws MessagingException;

    void sendRegisterMessage(String subject, User user,
                             String token) throws MessagingException;

    void sendChangePassword(String subject, User manager,
                            String token) throws MessagingException;

    void buildConfirmRegisterManager(String subject, User user,
                                     String token) throws MessagingException;

    void sendReminder(User recipient, String subject,
                      List<BookingDto> bookings) throws MessagingException;

    void sendDayOffReminder(User recipient, String subject,
                            DayOff dayOff) throws MessagingException;

    void sendDayOffReminderAsync(User recipient, String subject,
                                 DayOff dayOff) throws MessagingException;

    void sendReasonOfDeactivate(List<String> emailManagers, String roomName, String reason, List<InfoDeactivateRoomDto> list) throws MessagingException;
}
