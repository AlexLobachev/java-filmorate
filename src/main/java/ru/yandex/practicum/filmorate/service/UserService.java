package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User getUser(Integer userId);

    void addFriend(Integer userId, Integer friendId);

    List<User> getFriends(Integer userId);

    List<User> generalFriends(Integer userId, Integer otherId);

    void deleteFriend(Integer userId, Integer friendId);

    void rejectRequest(Integer userId, Integer requestId);

    void sendRequest(Integer userId, Integer requestId);
}
