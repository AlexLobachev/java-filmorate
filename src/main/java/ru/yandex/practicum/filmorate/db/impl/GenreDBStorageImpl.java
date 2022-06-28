package ru.yandex.practicum.filmorate.db.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class GenreDBStorageImpl {

    private final JdbcTemplate jdbcTemplate;
//
    public Genre getGenreId(Integer genreId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from genre where genre_id = ?", genreId);
        sqlRowSet.next();
        return new Genre(sqlRowSet.getInt("genre_id"),
                Objects.requireNonNull(sqlRowSet.getString("genre")));
    }

    public List<Genre> getGenreAll() {
        List<Genre> genres = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from genre ");
        while (sqlRowSet.next()) {
            Genre genre = new Genre(sqlRowSet.getInt("genre_id"),
                    Objects.requireNonNull(sqlRowSet.getString("genre")));
            genres.add(genre);
        }
        return genres;
    }

}
