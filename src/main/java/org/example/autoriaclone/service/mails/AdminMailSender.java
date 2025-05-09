package org.example.autoriaclone.service.mails;

import lombok.RequiredArgsConstructor;
import org.example.autoriaclone.entity.User;
import org.example.autoriaclone.enums.Role;
import org.example.autoriaclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminMailSender {
    private final MailSender mailSender;
    private final UserRepository userRepository;
    @Value("${spring.mail.username}")
    private String mailUsername;
    public void sendMail(String type, String object){
        List<User> admins = userRepository.findByRole(Role.ADMIN.name());

        List<String> adminsEmails = admins.stream().map(User::getEmail).toList();
        for (String adminEmail : adminsEmails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailUsername);
            message.setTo(adminEmail);
            message.setSubject(type+" : "+object+" not found in database");
            message.setText(type+": "+object+" not found in database. \n Add it please");
            mailSender.send(message);
        }
    }
}
