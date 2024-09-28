package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;

/**
 * Интерфейс для управления пользователями с расширенными возможностями.
 */
public interface CustomUserDetailsManager {

    /**
     * Создает нового пользователя.
     *
     * @param user Данные пользователя.
     * @param register Данные для регистрации пользователя.
     */
    void createUser(UserDetails user, Register register);

    /**
     * Проверяет, существует ли пользователь с указанным именем пользователя.
     *
     * @param username Имя пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    boolean userExists(String username);

    /**
     * Загружает пользователя по его имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Данные пользователя.
     * @throws UsernameNotFoundException Если пользователь не найден.
     */
    UserEntity loadUserByUsername(String username) throws UsernameNotFoundException;
}