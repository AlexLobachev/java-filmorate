package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.db.impl.UserDbStorageImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Validator;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OperationsUserService implements UserService {
    private final UserDbStorageImpl userDbStorage;
    private final Validator validator;

    public User getUser(Integer userId) {
        validator.invalidId(userId);
        log.debug("Пользователь получен!");
        return userDbStorage.getUser(userId);
    }//

    public void addFriend(Integer userId, Integer friendId) {
        validator.invalidId(friendId);
        log.debug("Друг добавлен!");
        userDbStorage.addFriend(userId, friendId);
    }

    public List<User> getFriends(Integer userId) {
        log.debug("Список друзей получен!");
        return userDbStorage.getFriends(userId);
    }


    public List<User> generalFriends(Integer userId, Integer otherId) {
        log.debug("Список общих друзей получен!");
        return userDbStorage.generalFriends(userId, otherId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        userDbStorage.deleteFriend(userId, friendId);
        log.debug("Друг удален!");
    }

    public void sendRequest(Integer userId, Integer requestId) {
        validator.invalidId(userId);
        log.debug("Запрос отправлен!");
        userDbStorage.sendRequest(userId, requestId);
    }

    public void rejectRequest(Integer userId, Integer requestId) {
        validator.invalidId(userId);
        log.debug("Запрос отклонен!");
        userDbStorage.rejectRequest(userId, requestId);
    }
}

