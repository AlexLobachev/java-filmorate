package ru.yandex.practicum.filmorate.db;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDbStorage {
    Film getFilm(Integer id);

    List<Film> getAllFilm();

    void addFilm(Film film);

    Film updateFilm(Film film);

    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);

    List<Film> getTenPopularMovies(Integer count);

}





