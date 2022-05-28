package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;
    private final Validator validator;

    @Autowired
    public InMemoryUserStorage(Validator validator) {
        this.validator = validator;
    }


    public Collection<User> getAllUsers() {
        log.debug("Всего пользователей = {}", users.size());
        return users.values();
    }

    public User addUser(User user) {
        validator.userValidator(user);
        user.setId(++id);
        users.put(id, user);
        log.debug("Пользователь добавлен, всего пользователей = {}", users.size());
        return user;
    }

    public User updateUser(User user) {
        validator.userValidator(user);
        validator.invalidId(user.getId());
        users.put(user.getId(), user);
        log.debug("Пользователь успешно изменен, всего пользователей = {}", users.size());
        return user;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

}
