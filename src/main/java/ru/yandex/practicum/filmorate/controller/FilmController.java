package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    int id = 0;

    @GetMapping("/films")
    public Collection<Film> getAllFilms() {
        log.debug("Всего фильмов = {}", films.size());
        return films.values();
    }

    @PostMapping(value = "/films")
    public Film addFilm(@RequestBody Film film) {


        if (film.getName().isBlank() || film.getName() == null) {
            throw new ValidationException("Название не может быть пустым");
        }

            /*for (Film film1 : films.values()) {
                if (film1.getName().equals(film.getName())) {
                    throw new ValidationException("Фильм " +
                            film.getName() + " уже есть в коллекции.");
                }
            }*/
        if (film.getDescription().isBlank()||film.getDescription()== null||film.getDescription().length() > 200) {
            throw new ValidationException("Описание слишком длинное, макс. длина 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        if (film.getDuration().getSeconds() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        film.setId(++id);
        films.put(id, film);
        log.debug("Фильм добавлен, всего фильмов = {}", films.size());

        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) {
       // boolean exception = true;

        if (film.getName().isBlank() || film.getName() == null) {
            throw new ValidationException("Название не может быть пустым");
        }

        if (film.getDescription().isBlank()||film.getDescription()== null||film.getDescription().length() > 200) {
            throw new ValidationException("Описание слишком длинное, макс. длина 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        if (film.getDuration().getSeconds() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        films.put(film.getId(), film);

            /*for (Film film1 : films.values()) {
                if (film1.getName().equals(film.getName())) {
                    film.setOldName(null);
                    throw new ValidationException("Фильм " +
                            film.getName() + " уже есть в коллекции.");
                }
                if (film1.getName().equals(film.getOldName())) {
                    film.setId(film1.getId());
                    films.put(film1.getId(), film);
                    film.setOldName(null);
                    exception = false;
                }
            }
            if (exception) {
                film.setOldName(null);
                throw new ValidationException("Фильм " +
                        film.getOldName() + " отсутствует в коллекции.");
            }*/

        return film;
    }


}
