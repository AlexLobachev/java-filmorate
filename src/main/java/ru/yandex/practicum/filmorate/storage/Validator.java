package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Component
public class Validator {


    protected void userValidator(User user) {

        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday().equals(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть сегодняшней или будущей");
        }

    }

    protected void filmValidator(Film film) {

        if (film.getDescription().isBlank() || film.getDescription() == null || film.getDescription().length() > 200) {
            throw new ValidationException("Описание слишком длинное, макс. длина 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        if (film.getDuration()/*.getSeconds()*/ < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }

    }

    public void invalidId(Integer id) {

        if (id < 0) {
            throw new IllegalArgumentException("Неверный ID");
        }
    }


}


