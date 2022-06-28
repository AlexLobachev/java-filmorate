package ru.yandex.practicum.filmorate.db;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface userDbStorage {
//

    User getUser(Integer userId);

    List<User> getAllUser();

    void addUser(User user);

    void updateUser(User user);

    void addFriend(Integer userId, Integer friendId);

    List<User> getFriends(Integer userId);

    void deleteFriend(Integer userId, Integer friendId);

    List<User> generalFriends(Integer userId, Integer otherId);

    void sendRequest(Integer userId, Integer requestId);

    void rejectRequest(Integer userId, Integer requestId);
}
