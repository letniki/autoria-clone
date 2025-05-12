package org.example.autoriaclone.service.mails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoriaclone.entity.Car;
import org.example.autoriaclone.entity.User;
import org.example.autoriaclone.enums.Role;
import org.example.autoriaclone.repository.UserRepository;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerMailSender {
    private final MailSender mailSender;
    private final UserRepository userRepository;

    public void sendMail(Car car){
        List<User> managers = userRepository.findByRole(Role.MANAGER.name());
        List<String> managerEmails = managers.stream().map(User::getEmail).toList();
        for (String managerEmail : managerEmails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("letutanikita12@gmail.com");
            message.setTo(managerEmail);
            message.setSubject("Moderation failed while posting a car with id: "+car.getId());
            message.setText("ID: "+car.getId()+"\n"+"Details: "+car.getDetails());
            mailSender.send(message);
        }
    }
}
