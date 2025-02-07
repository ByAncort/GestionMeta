package com.app.LoginAndGestion.Component;

import com.app.LoginAndGestion.Service.MailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DayliMail {
    @Autowired
    private MailService mailService;

    @Async
    @Scheduled(cron = "0 0 9 * * ?")
    @Transactional
    public void executeDailyMail(){
        mailService.notificacionTask();
    }
}
