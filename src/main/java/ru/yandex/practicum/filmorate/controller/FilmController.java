package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.OperationsFilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@Valid
public class FilmController {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    private final OperationsFilmService operationsFilmService;
    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, OperationsFilmService operationsFilmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.operationsFilmService = operationsFilmService;
    }

    @GetMapping("/films")
    public Collection<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @GetMapping(value = "/films/{id}")
    public Film getFilm(@PathVariable int id) {
        return operationsFilmService.getFilm(id);
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
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        return operationsFilmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
        return operationsFilmService.deleteLike(id, userId);
    }

    @GetMapping(value = "/films/popular")
    public List<Film> getTenPopularMovies(@RequestParam(defaultValue = "10") int count) {
        return operationsFilmService.getTenPopularMovies(count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> errorException(final ValidationException e) {
        return Map.of("ERROR", "ОШИБКА ВВОДА",
                "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> errorException(final IllegalArgumentException e) {
        return Map.of("ERROR", "ОШИБКА ВВОДА",
                "errorMessage", e.getMessage());

    }

}
