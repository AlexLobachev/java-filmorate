package ru.yandex.practicum.filmorate.db.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class MpaDbStorageImpl {

    JdbcTemplate jdbcTemplate;
//
    public Mpa getMpaId(Integer mpaId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select * " +
                "from category " +
                "where category_id = ?" +
                "", mpaId);
        sqlRowSet.next();
        return new Mpa(sqlRowSet.getInt("category_id"),
                Objects.requireNonNull(sqlRowSet.getString("category")));
    }

    public List<Mpa> getMpaAll() {
        List<Mpa> mpaAll = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select *" +
                " from category ");
        while (sqlRowSet.next()) {
            Mpa mpa = new Mpa(sqlRowSet.getInt("category_id"),
                    Objects.requireNonNull(sqlRowSet.getString("category")));
            mpaAll.add(mpa);
        }
        return mpaAll;
    }

}
