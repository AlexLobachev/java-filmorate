package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class User {

    private Integer id;
    @Email(message = "Некорректный Email")
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private TreeSet<Integer> idFriends = new TreeSet<>();
}
