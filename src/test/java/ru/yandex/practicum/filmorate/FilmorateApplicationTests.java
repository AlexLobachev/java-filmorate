package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class FilmorateApplicationTests {

	@Test
	void contextLoads() {
		//User user = new User(1,"Kapa@yandex.ru","Кошка_капа","Капа", LocalDate.of(2018,05,30));
		//user.setEmail("Kapayandex.ru");
		UserController userController = new UserController();
		userController.addUser(new User(1,"Kapa@yandex.ru","Кошка_капа","Капа", LocalDate.of(2018,05,30)));
		userController.getAllUsers();


	}

}
