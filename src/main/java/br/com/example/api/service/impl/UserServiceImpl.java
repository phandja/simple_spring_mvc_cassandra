package br.com.example.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.example.api.dao.UserDao;
import br.com.example.api.model.User;
import br.com.example.api.service.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public List<User> getAll() {

		return userDao.getAll();
	}

	@Override
	public User findById(int id) {
		return userDao.findById(id);
	}

	@Override
	public User findByName(String name) {
		return userDao.findByName(name);
	}

	@Override
	public void create(User user) {
		userDao.create(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(int id) {
		userDao.delete(id);
	}

	@Override
	public boolean exists(User user) {
		return userDao.exists(user);
	}
}
