package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.OperationsFilmService;
import ru.yandex.practicum.filmorate.service.OperationsUserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.Validator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


//@SpringBootTest
class FilmorateApplicationTests {
    Validator validator = new Validator();
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage(validator);
    OperationsUserService operationsUserService = new OperationsUserService(inMemoryUserStorage, validator);

    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage(validator);
    OperationsFilmService operationsFilmService = new OperationsFilmService(inMemoryFilmStorage, validator);
    UserController userController;
    FilmController filmController;
    User user;
    Film film;


    @BeforeEach
    public void beforeEach() {
        userController = new UserController(inMemoryUserStorage, operationsUserService);
        filmController = new FilmController(inMemoryFilmStorage, operationsFilmService);
    }

    @Test
    void flattererMustBeAdded() {

        user = new User(1,
                "Kapa@yandex.ru",
                "Кошка_капа",
                "Капа",
                LocalDate.of(2018, 5, 30));

        userController.addUser(user);

        assertEquals(user.getEmail(),
                "Kapa@yandex.ru");

    }

    @Test
    void theEmailAddressCannotBeEmpty() {
        user = new User(1,
                "  ",
                "Кошка_капа",
                "Капа",
                LocalDate.of(2018, 5, 30));
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.addUser(user));

        assertEquals("Адрес не может быть пустым", thrown.getMessage());
    }

    @Test
    void theAddressMustContainSymbol() {
        user = new User(1,
                "Kapayandex.ru",
                "Кошка_капа",
                "Капа",
                LocalDate.of(2018, 5, 30));
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.addUser(user));
        assertEquals("Некорректный Email", thrown.getMessage());
    }

    @Test
    void loginMayBeEmpty() {
        user = new User(1,
                "Kapa@yandex.ru",
                "  ",
                "Капа",
                LocalDate.of(2018, 5, 30));
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.addUser(user));

        assertEquals("Логин не может быть пустым или содержать пробелы", thrown.getMessage());

        user = new User(1,
                "Kapa@yandex.ru",
                "Кошка капа",
                "Капа",
                LocalDate.of(2018, 5, 30));
        thrown = assertThrows(ValidationException.class, () -> userController.addUser(user));

        assertEquals("Логин не может быть пустым или содержать пробелы", thrown.getMessage());

    }

    @Test
    void theNameShouldBecomeLogin() {
        user = new User(1,
                "Kapa@yandex.ru",
                "Кошка_капа",
                " ",
                LocalDate.of(2018, 5, 30));
        userController.addUser(user);
        assertEquals(user.getName(), user.getLogin());

    }

    @Test
    void theDateOfBirthCannotBeInTheFuture() {
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.addUser(new User(1,
                "Kapa@yandex.ru",
                "Кошка_капа",
                " ",
                LocalDate.now())));
        assertEquals("Дата рождения не может быть сегодняшней или будущей", thrown.getMessage());

        thrown = assertThrows(ValidationException.class, () -> userController.addUser(new User(1,
                "Kapa@yandex.ru",
                "Кошка_капа",
                " ",
                LocalDate.now().plusWeeks(20))));
        assertEquals("Дата рождения не может быть сегодняшней или будущей", thrown.getMessage());
    }


    @Test
    void theMovieMustBeAdded() {
        film = new Film(1,
                "Киборг убийца",
                "Киборг всех убивает",
                LocalDate.of(1985, 5, 30),
                10);
        filmController.addFilm(film);

        assertEquals(film.getName(), "Киборг убийца");
    }

    @Test
    void theTitleOfTheMovieMayNotBeEmpty() {
        film = new Film(1,
                "",
                "Киборг всех убивает",
                LocalDate.of(1985, 5, 30),
                10);
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Название не может быть пустым", thrown.getMessage());
    }

    @Test
    void theDescriptionCannotBeLongerThan200Characters() {
        film = new Film(1, "Киборг убийца",
                "Если я чешу в затылке - \n" +
                        "Не беда! \n" +
                        "В голове моей опилки, \n" +
                        "Да, да, да. \n" +
                        "Но хотя там и оплики, \n" +
                        "Но кричалки и вопилки, \n" +
                        "Но кричалки и вопилки, \n" +
                        "\n" +
                        "\n" +
                        "А также: \n" +
                        "Шумелки, пыхтелки и сопелки,- \n" +
                        "Сочинию я неплохо иногда. \n" +
                        "Да!!!! \n" +
                        "\n" +
                        "\n" +
                        "Хорошо живет на свете \n" +
                        "Винии-Пух! \n" +
                        "Оттого поет он эти \n" +
                        "Песни вслух! ", LocalDate.of(1985, 5, 30), 10);
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Описание слишком длинное, макс. длина 200 символов", thrown.getMessage());
    }

    @Test
    void TheReleaseDateCannotBeEarlierThan1985() {
        film = new Film(1,
                "Киборг убийца",
                "Киборг всех убивает",
                LocalDate.of(1884, 5, 30),
                10);
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года", thrown.getMessage());
    }


    @Test
    void theDurationOfTheFilmCannotBeNegative() {
        film = new Film(1,
                "Киборг убийца",
                "Киборг всех убивает",
                LocalDate.of(1984, 5, 30),
                -10);
       ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Продолжительность фильма не может быть отрицательной", thrown.getMessage());
    }

}


