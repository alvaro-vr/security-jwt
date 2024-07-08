package com.spring.security;

import com.spring.security.enums.Role;
import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.persistence.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository repository, PasswordEncoder encoder){
		return args -> {
			List<UserEntity> users = List.of(
					UserEntity.builder()
							.username("admin")
							.password(encoder.encode("1234"))
							.roles(Set.of(Role.ROLE_ADMIN))
							.build(),
					UserEntity.builder()
							.username("alvaro")
							.password(encoder.encode("1234"))
							.roles(Set.of(Role.ROLE_USER))
							.build()
			);
			repository.saveAll(users);
			System.out.println(repository.findByUsername("alvaro").get().toString());
		};
	}

}
