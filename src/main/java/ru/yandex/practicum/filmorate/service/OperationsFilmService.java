package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.db.impl.FilmDbStorageImpl;
import ru.yandex.practicum.filmorate.db.impl.GenreDBStorageImpl;
import ru.yandex.practicum.filmorate.db.impl.MpaDbStorageImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.Validator;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OperationsFilmService implements FilmService {
    private final Validator validator;
    private final FilmDbStorageImpl filmDbStorageImpl;
    private final GenreDBStorageImpl genreDBStorage;
    private final MpaDbStorageImpl mpaDbStorage;


    public Film getFilm(Integer filmId) {
        validator.invalidId(filmId);
        log.debug("Фильм добавлен!");
        return filmDbStorageImpl.getFilm(filmId);
    }

    public void addLike(Integer filmId, Integer userId) {
        filmDbStorageImpl.addLike(filmId, userId);
        log.debug("Лайк добавлен!");
    }

    public void deleteLike(Integer filmId, Integer userId) {
        validator.invalidId(userId);
        filmDbStorageImpl.deleteLike(filmId, userId);
        log.debug("Лайк у фильма удален!");
    }

    public List<Film> getTenPopularMovies(Integer count) {
        return filmDbStorageImpl.getTenPopularMovies(count);
    }

    public Genre getGenreId(Integer genreId) {
        validator.invalidId(genreId);
        log.debug("Жанр получен!");
        return genreDBStorage.getGenreId(genreId);
    }

    public List<Genre> getGenreAll() {
        return genreDBStorage.getGenreAll();
    }

    public Mpa getMpaId(Integer categoryId) {
        validator.invalidId(categoryId);
        log.debug("Категория получена!");
        return mpaDbStorage.getMpaId(categoryId);
    }

    public List<Mpa> getMpaAll() {
        return mpaDbStorage.getMpaAll();
    }

}

