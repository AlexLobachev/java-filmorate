package ru.yandex.practicum.filmorate.db.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageImplTest {

    private final UserStorage userStorage;

    private final UserService userService;


    @Test
    void getAllUser() {
        User user = new User(
                1,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        user = new User(
                2,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);

        assertEquals(userStorage.getAllUsers().size(), 5);
    }

    @Test
    void addUserAndGet() {
        User user = new User(
                1,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        assertEquals(userService.getUser(1).getId(), 1);
    }

    @Test
    void updateUser() {
        User user = new User(
                1,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        user = new User(
                1,
                "NoTest@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.updateUser(user);
        assertEquals(userService.getUser(1).getEmail(), "NoTest@mail.ru");
    }

    @Test
    void addFriendAndGet() {
        User user = new User(
                1,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        user = new User(
                2,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        userService.addFriend(1, 2);
        userService.getFriends(1);
        assertEquals(userService.getFriends(1).get(0).getId(), 3);
    }


    @Test
    void deleteFriend() {
        User user = new User(
                1,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        user = new User(
                2,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        userService.addFriend(1, 2);
        userService.deleteFriend(1, 2);
        userService.getFriends(1);
        assertEquals(userService.getFriends(1).size(), 0);
    }

    @Test
    void generalFriends() {
        User user = new User(
                1,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        user = new User(
                2,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        user = new User(
                3,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        user = new User(
                4,
                "Test@mail.ru",
                "Test",
                "Test",
                LocalDate.of(1990, 5, 23));
        userStorage.addUser(user);
        userService.addFriend(1, 3);
        userService.addFriend(2, 3);

        List<User> friends = userService.generalFriends(1, 2);
        assertEquals(friends.get(0).getId(), 3);
    }

}