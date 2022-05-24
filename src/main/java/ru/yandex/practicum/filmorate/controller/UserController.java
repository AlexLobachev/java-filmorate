package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;
    private ControllersValidator controllersValidator = new ControllersValidator();

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        log.debug("Всего пользователей = {}", users.size());

        return users.values();
    }

    @PostMapping(value = "/users")
    public User addUser(@RequestBody User user) {


        controllersValidator.userValidator(user);
        user.setId(++id);
        users.put(id, user);
        log.debug("Пользователь добавлен, всего пользователей = {}", users.size());

        return user;

    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User user) {
        controllersValidator.userValidator(user);
        users.put(user.getId(), user);
        log.debug("Пользователь успешно изменен, всего фильмов = {}", users.size());
        return user;
    }

    @DeleteMapping(value = "/users")
    public void clearUser() {
        users.clear();
        id = 0;
        log.debug("Список пользователь очищен = {}", users.size());
    }
}
