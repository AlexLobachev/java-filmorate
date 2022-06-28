package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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
    @Pattern(regexp = "\\S*$")
    private String login;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthday;
    private String status;


}
