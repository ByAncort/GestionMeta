package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.DTO.TaskDTO;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String mailUsername;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private JavaMailSender mailSender;

    public void SendGmail(String TO,String Subjet,String Body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailUsername);
        message.setTo(TO);
        message.setSubject(Subjet);
        message.setText(Body);
        mailSender.send(message);
        System.out.println("succes");
    }

    public void notificacionTask(){
        List<TaskDTO> tasks= taskRepository.findAllForDocument(null,null,null)
        .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
        for(TaskDTO task:tasks){
            if (!"Completada".equals(task.getStatus())){
                for (String responsable:task.getResponsables()) {
                    for (String email : task.getMails()) {
                        String body = "Hola "+responsable+ ", Como vamos con: " + task.getName().toString();
                        SendGmail(email, "testing", body);
                    }
                }

            }
        }

    }


}

