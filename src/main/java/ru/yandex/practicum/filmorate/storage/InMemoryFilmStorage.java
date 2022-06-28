package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.db.impl.FilmDbStorageImpl;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InMemoryFilmStorage implements FilmStorage {
    private final Validator validator;
    private final FilmDbStorageImpl filmDbStorageImpl;


    public List<Film> getAllFilms() {
        return filmDbStorageImpl.getAllFilm();
    }

    public Film addFilm(Film film) {
        validator.filmValidator(film);
        film.setId(readIdDb() + 1);
        filmDbStorageImpl.addFilm(film);
        log.debug("Фильм добавлен");
        return film;
    }//

    public Film updateFilm(Film film) {
        validator.filmValidator(film);
        validator.invalidId(film.getId());
        filmDbStorageImpl.updateFilm(film);
        log.debug("Фильм успешно изменен");

        return film;
    }

    public Integer readIdDb() {
        return filmDbStorageImpl.readIdDb();
    }
}
