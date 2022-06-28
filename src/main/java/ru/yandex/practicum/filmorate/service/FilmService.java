package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface FilmService {
    Film getFilm(Integer filmId);//

    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);

    List<Film> getTenPopularMovies(Integer count);

    Genre getGenreId(Integer genreId);

    List<Genre> getGenreAll();

    Mpa getMpaId(Integer categoryId);

    List<Mpa> getMpaAll();

}
