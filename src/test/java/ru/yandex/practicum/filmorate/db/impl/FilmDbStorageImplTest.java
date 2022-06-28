package ru.yandex.practicum.filmorate.db.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageImplTest {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Test
    void getAllFilm() {
        Film film = new Film(3,
                "Test",
                "Description",
                LocalDate.now(),
                153,
                4,
                new Mpa(1, "G"),
                new HashSet<>());
        filmStorage.addFilm(film);
        film = new Film(4,
                "Test",
                "Description",
                LocalDate.now(),
                153,
                4,
                new Mpa(1, "G"),
                new HashSet<>());
        filmStorage.addFilm(film);
        assertEquals(filmStorage.getAllFilms().size(), 5);
    }

    @Test
    void addFilmAndGet() {
        Film film = new Film(2,
                "Test",
                "Description",
                LocalDate.now(),
                153,
                4,
                new Mpa(1, "G"),
                new HashSet<>());
        filmStorage.addFilm(film);
        assertEquals(filmService.getFilm(2).getName(), "Test");
    }

    @Test
    void updateFilm() {
        Film film = new Film(1,
                "Test",
                "Description",
                LocalDate.now(),
                153,
                4,
                new Mpa(1, "G"),
                new HashSet<>());
        filmStorage.addFilm(film);
        assertEquals(filmService.getFilm(1).getName(), "Test");
        film = new Film(1,
                "SuperTest",
                "Description",
                LocalDate.now(),
                153,
                4,
                new Mpa(1, "G"),
                new HashSet<>());
        filmStorage.updateFilm(film);
        assertEquals(filmService.getFilm(1).getName(), "SuperTest");
    }

    @Test
    void addLikeAndDelete() {
        Film film = new Film(1,
                "Test",
                "Description",
                LocalDate.now(),
                153,
                4,
                new Mpa(1, "G"),
                new HashSet<>());
        filmStorage.addFilm(film);
        assertEquals(filmService.getFilm(1).getRate(), 4);
        filmService.addLike(1,1);
        assertEquals(filmService.getFilm(1).getRate(), 5);
        filmService.deleteLike(1,2);
        assertEquals(filmService.getFilm(1).getRate(), 4);
    }
    @Test
    void getMpaId() {
        Film film = new Film(1,
                "Test",
                "Description",
                LocalDate.now(),
                153,
                4,
                new Mpa(1, "G"),
                new HashSet<>());
        filmStorage.addFilm(film);

        assertEquals(filmService.getMpaId(1).getName(), "G");
    }
}