package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.OperationsFilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Valid
public class FilmController {
//
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final OperationsFilmService operationsFilmService;

    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, OperationsFilmService operationsFilmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.operationsFilmService = operationsFilmService;

    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @GetMapping(value = "/films/{id}")
    public Film getFilm(@PathVariable("id") Integer filmId) {
        return operationsFilmService.getFilm(filmId);
    }

    @PostMapping(value = "/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        operationsFilmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer filmId, @PathVariable Integer userId) {
        operationsFilmService.deleteLike(filmId, userId);
    }

    @GetMapping(value = "/films/popular")
    public List<Film> getTenPopularMovies(@RequestParam(defaultValue = "10") Integer count) {
        return operationsFilmService.getTenPopularMovies(count);
    }


}
