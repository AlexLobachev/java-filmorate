package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.OperationsFilmService;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class MpaController {
    private final OperationsFilmService operationsFilmService;
//
    @GetMapping(value = "/mpa/{id}")
    public Mpa getMpaId(@PathVariable("id") Integer categoryId) {
        return operationsFilmService.getMpaId(categoryId);
    }


    @GetMapping(value = "/mpa")
    public List<Mpa> getMpaAll() {
        return operationsFilmService.getMpaAll();
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






