package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
//
@RestControllerAdvice("ru.yandex.practicum.filmorate")
public class ExceptionController {

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
