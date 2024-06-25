package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/sendMail")
    public ResponseEntity<Object> sendEmail(@RequestBody Student student) {
        try {
            SimpleMailMessage sm = new SimpleMailMessage();
            sm.setFrom("jakkanagaridivya.16@gmail.com");
            sm.setTo(student.getEmail());
            sm.setSubject("Welcome Divya");
            sm.setText("Hello " + student.getName() + "\n\n Welcome Divya");
            javaMailSender.send(sm);
            return generateResponse("Email sent successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            return generateResponse("Error sending email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private ResponseEntity<Object> generateResponse(String msg, HttpStatus st, Object response) {
        Map<String, Object> mp = new HashMap<>();
        mp.put("message", msg);
        mp.put("status", st.value());
        mp.put("data", response);
        return new ResponseEntity<>(mp, st);
    }
}
