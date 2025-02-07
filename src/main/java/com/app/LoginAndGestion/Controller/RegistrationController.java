package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Model.User;
import com.app.LoginAndGestion.Service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
public class RegistrationController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String regex = "^[a-zA-Z0-9._%+-]+@metacontrol\\.cl$";

        if (!Pattern.matches(regex,user.getEmail())) {
            return ResponseEntity.badRequest().body("Correo no permitido");
        }
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User usuarioGuradado= userLoginService.saveUser(user);

        return ResponseEntity.ok(usuarioGuradado);
    }
}
