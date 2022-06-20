package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.Validator;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OperationsUserService {
    private final InMemoryUserStorage inMemoryUserStorage;
    private final Validator validator;

    @Autowired
    public OperationsUserService(InMemoryUserStorage inMemoryUserStorage, Validator validator) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.validator = validator;
    }

    public User getUser(int id) {
        validator.invalidId(id);
        log.debug("Всего пользователей = {}", inMemoryUserStorage.getUsers().size());
        return inMemoryUserStorage.getUsers().get(id);
    }

    public User addFriend(int id, int friendId) {
        validator.invalidId(friendId);
        User user = inMemoryUserStorage.getUsers().get(id);
        user.getIdFriends().add(friendId);
        user = inMemoryUserStorage.getUsers().get(friendId);
        user.getIdFriends().add(id);
        log.debug("Друг = " + user.getName() + " добавлен!");
        return user;
    }

    public List<User> getFriends(int id) {
        final List<User> listFriends = new ArrayList<>();
        if (inMemoryUserStorage.getUsers().containsKey(id)) {
            User user = inMemoryUserStorage.getUsers().get(id);
            for (long user1 : user.getIdFriends())
                listFriends.add(inMemoryUserStorage.getUsers().get((int) user1));
        }
        log.debug("Список друзей получен! Всего друзей = {}", listFriends.size());
        return listFriends;
    }

    public List<User> generalFriends(int id, int otherId) {
        final List<User> listFriends = new ArrayList<>();
        if (inMemoryUserStorage.getUsers().containsKey(id) && inMemoryUserStorage.getUsers().containsKey(otherId)) {
            User user1 = inMemoryUserStorage.getUsers().get(id);
            User user2 = inMemoryUserStorage.getUsers().get(otherId);
            for (int userId : user1.getIdFriends()) {
                for (int friendId : user2.getIdFriends()) {
                    if (userId == friendId) {
                        listFriends.add(inMemoryUserStorage.getUsers().get(userId));
                    }
                }
            }
        }
        System.out.println(listFriends);
        log.debug("Список общих друзей получен! Всего общих друзей = {}", listFriends.size());


        return listFriends;
    }

    public User deleteFriend(int id, int friendId) {
        User user = inMemoryUserStorage.getUsers().get(id);
        user.getIdFriends().remove(friendId);
        log.debug("Друг = " + user.getName() + " удален!");
        return user;
    }
}



    /*public User addFriend(int id, long friendId) {
        final List<User> listFriends = new ArrayList<>();
        User user = inMemoryUserStorage.getUsers().get(id);
        user.setIdFriends(friendId);
        for (long user1 : user.getIdFriends()) {
            listFriends.add(inMemoryUserStorage.getUsers().get((int) user1));

        }
        userFriends.put(id, listFriends);

        return user;
    }

    public List<User> getFriends(int id) {
        return userFriends.get(id);
    }*/
