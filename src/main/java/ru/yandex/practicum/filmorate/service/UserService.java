package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User addFriend(int id, int friendId);

    User getUser(int id);

    User deleteFriend(int id, int friendId);

    List<User> getFriends(int id);
}
