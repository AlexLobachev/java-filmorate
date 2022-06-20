package ru.yandex.practicum.filmorate.model;


import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @NonNull
    private Integer id;
    @Email(message = "Некорректный Email")
    @NonNull
    private String email;
    @NonNull
    private String login;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthday;
    private TreeSet<Integer> idFriends = new TreeSet<>();
}
