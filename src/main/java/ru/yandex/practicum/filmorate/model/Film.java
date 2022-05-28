package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Film {
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private LocalDate releaseDate;
    @NonNull
    private Integer duration;
    @NonNull
    private Integer rate = 0;
    private TreeSet<Integer> userId = new TreeSet<>();


}
