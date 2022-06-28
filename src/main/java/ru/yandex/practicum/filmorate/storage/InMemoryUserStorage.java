package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.db.impl.UserDbStorageImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InMemoryUserStorage implements UserStorage {
    private final Validator validator;
    private final UserDbStorageImpl userDbStorage;

    public List<User> getAllUsers() {
        return userDbStorage.getAllUser();
    }

    public User addUser(User user) {
        validator.userValidator(user);
        user.setId(readIdDb() + 1);
        userDbStorage.addUser(user);
        log.debug("Пользователь добавлен");
        return user;
    }

    public User updateUser(User user) {
        validator.userValidator(user);
        validator.invalidId(user.getId());
        userDbStorage.updateUser(user);
        log.debug("Пользователь успешно изменен");
        return user;
    }


    public Integer readIdDb() {
        return userDbStorage.readIdDb();
    }
}
