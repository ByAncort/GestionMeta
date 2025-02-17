package com.app.LoginAndGestion.Component;

import com.app.LoginAndGestion.Service.MailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DayliMail {
    @Autowired
    private MailService mailService;
    @Value("${spring.report.ejecucion}")
    private boolean ejecucion;



    @Async
    @Scheduled(cron = "0 0 9 * * MON-FRI")
    @Transactional
    public void executeDailyMail(){
        mailService.notificacionTask();
    }
    @Async
    @Scheduled(cron = "0 0 9 * * MON-FRI")
    @Transactional
    public void executeDailyMailJEFE(){
        if (ejecucion) {
            mailService.reportNotificationTask();
        }
    }
}
