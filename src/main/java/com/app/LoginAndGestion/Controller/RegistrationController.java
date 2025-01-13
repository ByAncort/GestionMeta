package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Model.UserLogin;
import com.app.LoginAndGestion.Service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signup", consumes = "application/json")
    public UserLogin createUser(@RequestBody UserLogin user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userLoginService.saveUser(user);
    }
}
