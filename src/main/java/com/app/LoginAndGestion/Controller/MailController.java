package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/send-mail")
    public void send(){
//        mailService.SendGmail("diego.cortes@metacontrol.cl","test","test");
            mailService.notificacionTask();
    }
    @PostMapping("/send-mail/jefe")
    public void sendjefe(){
//        mailService.SendGmail("diego.cortes@metacontrol.cl","test","test");
        try {
            mailService.reportNotificationTask();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
