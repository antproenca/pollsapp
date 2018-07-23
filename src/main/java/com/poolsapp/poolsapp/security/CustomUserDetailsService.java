package com.poolsapp.poolsapp.security;

import com.poolsapp.poolsapp.Exception.ResourceNotFoundException;
import com.poolsapp.poolsapp.model.User;
import com.poolsapp.poolsapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
O primeiro metodo loadUserByUsername() é usado pelo spring security. finByUsernameorEmail permite que o usuário logue
usando username ou email.
O segundo metodo loadUserById será usado pelo JWTAuthenticationFilter.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        //Permite que usuário acesse via username ou email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                    new UsernameNotFoundException("Usuário não encontrado para este username ou email : "
                            + usernameOrEmail));
        return UserPrincipal.create(user);
    }

    //Método utilizado pelo JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }

}
