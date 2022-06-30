package ru.yandex.practicum.filmorate.db.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.*;

@Component
@Slf4j
@AllArgsConstructor
public class FilmDbStorageImpl implements FilmDbStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDBStorageImpl genreDBStorage;
    private final MpaDbStorageImpl mpaDbStorage;


    @Override
    public Film getFilm(Integer id) {

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select * " +
                "from films" +
                " where film_id = ?" +
                "", id);
        if (sqlRowSet.next()) {
            return createFilm(sqlRowSet);
        } else {
            return null;
        }
    }

    @Override
    public List<Film> getAllFilm() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select *" +
                "from films ");
        List<Film> films = new ArrayList<>();
        while (sqlRowSet.next()) {
            films.add(createFilm(sqlRowSet));
        }
        return films;
    }

    @Override
    public void addFilm(Film film) {

        String sqlQuery = "insert into films (film_id,name_film, description, releasedate,duration,rate) " +
                "values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate());
        if (film.getGenres() != null) {
            saveGenre(film);
        }
        saveMpa(film);
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "" +
                "update films" +
                " set name_film = ?, description = ?, releasedate = ?,duration = ? " +
                "where film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());
        if (film.getGenres() != null) {
            saveGenre(film);

        }
        saveMpa(film);
        return film;

    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        String sqlQuery = "insert into users_rate (film_id, user_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
        sqlQuery = "" +
                "update films" +
                " set rate = rate + 1 " +
                " where film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        String sqlQuery = "" +
                "delete from" +
                " users_rate " +
                "where film_id = ?" +
                " AND user_id = ?";
        jdbcTemplate.update(sqlQuery, userId, filmId);
        sqlQuery = "update films set rate = rate - 1 " + " where film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    @Override
    public List<Film> getTenPopularMovies(Integer count) {
        List<Film> generalFriends = new ArrayList<>();

        SqlRowSet sqlRowSet =
                jdbcTemplate.queryForRowSet("" +
                        "select * " +
                        "from films " +
                        "order by rate DESC " +
                        "limit " + count);
        while (sqlRowSet.next()) {
            generalFriends.add(getFilm(sqlRowSet.getInt("film_id")));
        }
        return generalFriends;
    }

    private void saveGenre(Film film) {
        jdbcTemplate.update("" +
                "DELETE FROM films_genre" +
                " WHERE film_id = ? ", film.getId());
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update("" +
                    "insert into films_genre (film_id, genre_id)" +
                    " values (?, ?) ", film.getId(), genre.getId());
        }

    }

    private void saveMpa(Film film) {
        jdbcTemplate.update("" +
                "DELETE FROM film_category" +
                " WHERE film_id = ?", film.getId());
        jdbcTemplate.update("" +
                "insert into film_category (film_id, category_id)" +
                " values (?,?) ", film.getId(), film.getMpa().getId());

        //НЕ РАБОТАЕТ
        //"insert into film_category (film_id, category_id)
        // values (?,?)
        // ON CONFLICT (film_id)
        // DO UPDATE SET category_id = EXCLUDED.category_id",
        // film.getId(),film.getMpa().getId());
    }

    private Set<Genre> getFilmsGenre(Integer filmId) {
        Set<Genre> genres = new HashSet<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select * " +
                "from films_genre" +
                " where film_id = ?" +
                " order by genre_id ASC ", filmId);
        while (sqlRowSet.next()) {
            genres.add(genreDBStorage.getGenreId(sqlRowSet.getInt("genre_id")));
        }

        if (genres.size() == 0) {
            return null;
        }
        return genres;
    }

    private Mpa getFilmsMpa(Integer filmId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select * " +
                "from film_category " +
                "where film_id = ?", filmId);
        sqlRowSet.next();
        return mpaDbStorage.getMpaId(sqlRowSet.getInt("category_id"));
    }

    private Film createFilm(SqlRowSet sqlRowSet) {
        return new Film(sqlRowSet.getInt("film_id"),
                sqlRowSet.getString("name_film"),
                sqlRowSet.getString("description"),
                Objects.requireNonNull(sqlRowSet.getDate("releasedate")).toLocalDate(),
                sqlRowSet.getInt("duration"),
                sqlRowSet.getInt("rate"),
                getFilmsMpa(sqlRowSet.getInt("film_id")),
                getFilmsGenre(sqlRowSet.getInt("film_id")));
    }

    public Integer readIdDb() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select film_id" +
                " from films " +
                "order by film_id desc limit 1"
        );
        if (sqlRowSet.next()) {
            return sqlRowSet.getInt("film_id");
        }
        return 0;
    }

}
