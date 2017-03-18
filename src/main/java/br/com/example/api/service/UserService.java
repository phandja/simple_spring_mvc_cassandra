package br.com.example.api.service;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.example.api.model.User;

@Component
public interface UserService {

	List<User> getAll();

	User findById(int id);

	User findByName(String name);

	void create(User user);

	void update(User user);

	void delete(int id);

	boolean exists(User user);
}
