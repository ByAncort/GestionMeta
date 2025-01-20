package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.UserLogin;
import com.app.LoginAndGestion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLoginService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserLogin> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            var userObj = user.get();

            // Convertir los roles a SimpleGrantedAuthority para que Spring Security los entienda
            List<GrantedAuthority> authorities = userObj.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))  // Asumiendo que Role tiene el nombre 'roleName'
                    .collect(Collectors.toList());

            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .authorities(authorities)  // Establecer las autoridades (roles)
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }

    public List<UserLogin>  listUser(){return userRepository.findAll();}
    public UserLogin saveUser(UserLogin user) {
        return userRepository.save(user);
    }
}
