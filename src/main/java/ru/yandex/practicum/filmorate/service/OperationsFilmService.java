package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.Validator;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OperationsFilmService implements FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final Validator validator;

    @Autowired
    public OperationsFilmService(InMemoryFilmStorage inMemoryFilmStorage, Validator validator) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.validator = validator;
    }

    public Film getFilm(int id) {
        validator.invalidId(id);
        log.debug("Всего фильмов = {}", inMemoryFilmStorage.getFilms().size());
        return inMemoryFilmStorage.getFilms().get(id);
    }

    public Film addLike(int id, int userId) {
        Film film = inMemoryFilmStorage.getFilms().get(id);
        if (film.getUserId().add(userId)) {
            film.setRate(film.getRate() + 1);
        }
        log.debug("Лайк к фильму = " + film.getName() + " добавлен!" + " Всего лайков " + film.getRate());
        return film;
    }

    public Film deleteLike(int id, int userId) {
        validator.invalidId(userId);
        Film film = inMemoryFilmStorage.getFilms().get(id);
        if (film.getUserId().remove(userId)) {
            film.setRate(film.getRate() - 1);
        }
        log.debug("Лайк у фильма = " + film.getName() + " удален!" + " Всего лайков " + film.getRate());
        return film;
    }

    public List<Film> getTenPopularMovies(int count) {
        List<Film> popularFilm = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        popularFilm.sort((o1, o2) -> o2.getRate() - o1.getRate());
        if (popularFilm.size() < count) {
            return popularFilm;
        }

        return popularFilm.subList(0, count);
    }

}

