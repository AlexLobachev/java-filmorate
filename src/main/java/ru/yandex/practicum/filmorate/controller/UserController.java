package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    int id = 0;

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        log.debug("Всего пользователей = {}", users.size());

        return users.values();
    }

    @PostMapping(value = "/users")
    public User addUser(@RequestBody User user) {


        if (user.getEmail().isBlank() || user.getEmail() == null) {
            throw new ValidationException("Адрес не может быть пустым");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Некорректный Email");
        }
            /*for (User user1 : users.values()) {
                if (user1.getEmail().equals(user.getEmail())) {
                    throw new ValidationException("Пользователь с электронной почтой " +
                            user.getEmail() + " уже зарегистрирован.");
                }
            }*/
        if (user.getLogin().isBlank() || user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        }

        if (user.getName().isBlank() || user.getName() == null) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday().equals(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть сегодняшней или будущей");
        }
        user.setId(++id);
        users.put(id, user);
        log.debug("Пользователь добавлен, всего пользователей = {}", users.size());

        return user;

    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User user) {
        //boolean exception = true;

        if (user.getEmail().isBlank() || user.getEmail() == null) {
            throw new ValidationException("Адрес не может быть пустым");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Некорректный Email");
        }

        if (user.getLogin().isBlank() || user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        }
        if (user.getName().isBlank() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday().equals(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть сегодняшней или будущей");
        }

        users.put(user.getId(), user);

           /* for (User user1 : users.values()) {
                if (user1.getEmail().equals(user.getEmail())) {
                    user.setId(user1.getId());
                    users.put(user1.getId(), user);
                    exception = false;
                }
            }
            if (exception) {
                throw new ValidationException("Пользователь с электронной почтой " +
                        user.getEmail() + " не зарегистрирован.");
            }*/

        return user;
    }

    @DeleteMapping(value = "/users")
    public void clearUser() {
        users.clear();
        id = 0;
        log.debug("Список пользователь очищен = {}", users.size());
    }
}
