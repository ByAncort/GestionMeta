package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
public class MailController {
    @Autowired
    private MailService mailService;

    @GetMapping("/send-mail")
    public void send(){
//        mailService.SendGmail("diego.cortes@metacontrol.cl","test","test");
            mailService.notificacionTask();
    }

}
