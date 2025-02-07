package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Model.User;
import com.app.LoginAndGestion.Repository.RolRepository;
import com.app.LoginAndGestion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    @Autowired
    private RolRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            var userObj = user.get();

            // Convertir los roles a SimpleGrantedAuthority para que Spring Security los entienda
            List<GrantedAuthority> authorities = userObj.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))  // Asumiendo que Role tiene el nombre 'roleName'
                    .collect(Collectors.toList());

            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .authorities(authorities)  // Establecer las autoridades (roles)
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }

    public List<User>  listUser(){return userRepository.findAll();}
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void assignRoleToUser(Long userId, Long roleId) {
        // Obtener el usuario
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Obtener el rol
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        // Asignar el rol al usuario
        user.addRole(role);
        // Guardar los cambios
        userRepository.save(user);
    }
}
