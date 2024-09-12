package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Пользователи")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = {@Content(
                    schema = @Schema(hidden = true))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(hidden = true)))})
    ResponseEntity<?> setPassword(@RequestBody(required = false) NewPassword newPassword){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        if (!userService.hasAccessToChangePassword(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.setPassword(newPassword);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true)))})
    ResponseEntity<User> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UpdateUser.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true)))})
    ResponseEntity<UpdateUser> updateUser(@RequestBody(required = false) UpdateUser updateUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    @PatchMapping(value = "/me/image", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = {@Content(
                    schema = @Schema(hidden = true))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<?> updateUserAvatar(@RequestParam("image") MultipartFile image) {
        userService.updateUserAvatar(image);
        return ResponseEntity.ok().build();
    }
}
