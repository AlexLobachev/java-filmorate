package ru.yandex.practicum.filmorate.db.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.db.userDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class UserDbStorageImpl implements userDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUser(Integer userId) {

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select *" +
                " from users " +
                "where user_id = ?", userId);

        if (sqlRowSet.next()) {
            User user = new User(sqlRowSet.getInt("user_id"),
                    Objects.requireNonNull(sqlRowSet.getString("email")),
                    Objects.requireNonNull(sqlRowSet.getString("login")),
                    Objects.requireNonNull(sqlRowSet.getString("name_user")),
                    Objects.requireNonNull(sqlRowSet.getDate("birthday")).toLocalDate());
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAllUser() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(" " +
                "select *" +
                " from users ");
        List<User> users = new ArrayList<>();
        while (sqlRowSet.next()) {
            User user = new User(sqlRowSet.getInt("user_id"),
                    Objects.requireNonNull(sqlRowSet.getString("email")),
                    Objects.requireNonNull(sqlRowSet.getString("login")),
                    Objects.requireNonNull(sqlRowSet.getString("name_user")),
                    Objects.requireNonNull(sqlRowSet.getDate("birthday")).toLocalDate());
            users.add(user);
        }
        return users;
    }

    @Override
    public void addUser(User user) {
        String sqlQuery = "insert into users (user_id,name_user, email, login,birthday) " +
                "values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
    }

    @Override
    public void updateUser(User user) {
        String sqlQuery = "" +
                "update users" +
                " set name_user = ?, email = ?, login = ?,birthday = ? " +
                "where user_id = ?";

        jdbcTemplate.update(sqlQuery, user.getName(), user.getEmail(), user.getLogin(), user.getBirthday(), user.getId());

    }
    /*@Override
    public void addFriend(Integer userId, Integer friendId) {
        String sqlQuery = "insert into users_friend (user_id, friend_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }*/

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        rejectRequest(userId, friendId);
        String sqlQuery = "insert into users_friend (user_id, friend_id, status ) " +
                "values (?, ?,?)";
        jdbcTemplate.update(sqlQuery, userId, friendId, "заявка принята");
    }

    @Override
    public List<User> getFriends(Integer userId) {
        List<User> friends = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select * " +
                "from users_friend " +
                "where user_id = ?", userId);
        while (sqlRowSet.next()) {
            if (sqlRowSet.getInt("friend_id") != 0) {
                friends.add(getUser(sqlRowSet.getInt("friend_id")));
            }
        }
        return friends;
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        String sqlQuery = "" +
                "delete from users_friend" +
                " where user_id = ?" +
                " AND friend_id = ?";

        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> generalFriends(Integer userId, Integer otherId) {
        List<User> generalFriends = new ArrayList<>();

        SqlRowSet sqlRowSet =
                jdbcTemplate.queryForRowSet("" +
                        " select f1.friend_id" +
                        " from users_friend f1 " +
                        " join users_friend f2 " +
                        " on f2.user_id = ? " +
                        " and f2.friend_id = f1.friend_id " +
                        " join users " +
                        " on users.user_id = f1.friend_id " +
                        " where f1.user_id = ? ", otherId, userId);

        while (sqlRowSet.next()) {
            generalFriends.add(getUser(sqlRowSet.getInt("friend_id")));
        }
        return generalFriends;
    }


    public void sendRequest(Integer userId, Integer requestId) {
        String sqlQuery = "insert into users_friend (user_id, status, request_id) " +
                "values (?,?,?)";
        jdbcTemplate.update(sqlQuery, userId, "Новая заявка в друзья", requestId);
    }

    public void rejectRequest(Integer userId, Integer requestId) {
        String sqlQuery = "" +
                "delete from users_friend" +
                " where user_id = ?" +
                " AND request_id = ?";
        jdbcTemplate.update(sqlQuery, userId, requestId);
    }

    public Integer readIdDb() {

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select user_id" +
                " from users " +
                "order by user_id desc limit 1"
        );

        if (sqlRowSet.next()) {
            return sqlRowSet.getInt("user_id");
        }
        return 0;
    }

}
