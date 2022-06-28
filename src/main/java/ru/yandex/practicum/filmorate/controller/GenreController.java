package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.OperationsFilmService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class GenreController {
//
    private final OperationsFilmService operationsFilmService;

    @GetMapping(value = "/genres/{id}")
    public Genre getGenreId(@PathVariable("id") Integer genreId) {
        return operationsFilmService.getGenreId(genreId);
    }

    @GetMapping(value = "/genres")
    public List<Genre> getGenreAll() {
        return operationsFilmService.getGenreAll();
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
