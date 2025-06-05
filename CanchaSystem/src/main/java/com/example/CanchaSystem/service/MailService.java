package com.example.CanchaSystem.service;

import com.example.CanchaSystem.model.Reservation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("canchasystem@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendReminder(String to, Reservation reservation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Recordatorio de partido mañana");
        message.setText(String.format(
                "Hola %s,\n\nTe recordamos que mañana tenés un partido reservado en la cancha %s a las %s.\n\n¡Éxitos!",
                reservation.getClient().getName(),
                reservation.getCancha().getName(),
                reservation.getMatchDate().toString()
        ));
        message.setFrom("tu-correo@gmail.com");

        mailSender.send(message);
    }

}
