package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.OperationsUserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return operationsUserService.getUser(id);
    }

    @PostMapping(value = "/users")
    public User addUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.addUser(user);
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        return operationsUserService.addFriend(id, friendId);
    }

    @GetMapping(value = "/users/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        return operationsUserService.getFriends(id);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        return operationsUserService.deleteFriend(id, friendId);
    }

    @GetMapping(value = "/users/{id}/friends/common/{otherId}")
    public List<User> generalFriends(@PathVariable int id, @PathVariable int otherId) {
        return operationsUserService.generalFriends(id, otherId);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> errorException(final ValidationException e) {
        return Map.of("ERROR", "ОШИБКА ВВОДА",
                "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> errorException(final IllegalArgumentException e) {
        return Map.of("ERROR", "ОШИБКА ВВОДА",
                "errorMessage", e.getMessage());
    }


}
