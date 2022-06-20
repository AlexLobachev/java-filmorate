package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id;
    private final Validator validator;

    @Autowired
    public InMemoryFilmStorage(Validator validator) {
        this.validator = validator;
    }

    public Collection<Film> getAllFilms() {
        log.debug("Всего фильмов = {}", films.size());
        return films.values();
    }

    public Film addFilm(Film film) {
        validator.filmValidator(film);
        film.setId(++id);
        films.put(id, film);
        log.debug("Фильм добавлен, всего фильмов = {}", films.size());

        return film;
    }

    public Film updateFilm(Film film) {
        validator.filmValidator(film);
        validator.invalidId(film.getId());
        films.put(film.getId(), film);
        log.debug("Фильм успешно изменен, всего фильмов = {}", films.size());

        return film;
    }

    public Map<Integer, Film> getFilms() {
        return films;
    }
}
