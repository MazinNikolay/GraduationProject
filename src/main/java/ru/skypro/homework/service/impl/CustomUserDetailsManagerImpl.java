package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.CustomUserDetailsManager;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsManagerImpl implements CustomUserDetailsManager {
    private final UserEntityRepository userEntityRepository;

    @Override
    public void createUser(UserDetails user, Register register) {
        UserEntity userEntity = register.toUserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEnabled(user.isEnabled());
        userEntityRepository.save(userEntity);
    }

    @Override
    public boolean userExists(String username) {
        return userEntityRepository.existsByUsername(username);
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByUsername(username);
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User not found: " + username);

        }
        return userEntityRepository.findByUsername(username);
    }
}
