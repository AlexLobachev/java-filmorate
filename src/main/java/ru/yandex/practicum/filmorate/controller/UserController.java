package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.OperationsUserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Valid
public class UserController {

    private final InMemoryUserStorage inMemoryUserStorage;
    private final OperationsUserService operationsUserService;

    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage, OperationsUserService operationsUserService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.operationsUserService = operationsUserService;
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Integer userId) {
        return operationsUserService.getUser(userId);
    }

    @PostMapping(value = "/users")
    public User addUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.addUser(user);
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    //@PutMapping(value = "/users/{id}/friends/{friendId}")
    @PutMapping(value = "/users/{id}/friendsAdd/{friendId}")
    public void addFriend(@PathVariable("id") Integer userId, @PathVariable Integer friendId) {
        operationsUserService.addFriend(userId, friendId);
    }

    @GetMapping(value = "/users/{id}/friends")
    public List<User> getFriends(@PathVariable("id") Integer userId) {
        return operationsUserService.getFriends(userId);
    }


    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Integer userId, @PathVariable Integer friendId) {
        operationsUserService.deleteFriend(userId, friendId);
    }

    @GetMapping(value = "/users/{id}/friends/common/{otherId}")
    public List<User> generalFriends(@PathVariable("id") Integer userId, @PathVariable Integer otherId) {
        return operationsUserService.generalFriends(userId, otherId);
    }

    //@PutMapping(value = "/users/{id}/sendRequest/{friendId}")
    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void sendRequest(@PathVariable("id") Integer userId, @PathVariable("friendId") Integer requestId) {
        operationsUserService.sendRequest(userId, requestId);
        addFriend(userId, requestId);   //заглушка для тестов
    }

    @PutMapping(value = "/users/{id}/rejectRequest/{requestId}")
    public void rejectRequest(@PathVariable("id") Integer userId, @PathVariable("requestId") Integer requestId) {
        operationsUserService.rejectRequest(userId, requestId);
    }

}
