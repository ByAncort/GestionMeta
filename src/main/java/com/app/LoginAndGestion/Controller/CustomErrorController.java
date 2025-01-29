package com.app.LoginAndGestion.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController  implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode != null) {
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "page_errors/error-404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "page_errors/error-403";
            }
        }

        // Página genérica para otros errores.
        return "page_errors/error-default";
    }

    public String getErrorPath() {
        return "/error";
    }
}
