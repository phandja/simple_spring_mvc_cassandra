package br.com.example.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.example.api.model.User;
import br.com.example.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	@Qualifier("userService")
	private UserService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAll() {
		try {
			List<User> users = service.getAll();
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		try {
			User user = service.findById(id);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		try {
			if (service.exists(user)) {
				LOG.info("a user with name " + user.getUsername() + " already exists");
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}

			service.create(user);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		try {
			User currentUser = service.findById(user.getId());

			if (currentUser == null) {
				LOG.info("User with id {} not found", user.getId());
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}

			currentUser.setId(user.getId());
			currentUser.setUsername(user.getUsername());

			service.update(user);
			return new ResponseEntity<User>(currentUser, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		try {
			LOG.info("deleting user with id: {}", id);
			User user = service.findById(id);

			if (user == null) {
				LOG.info("Unable to delete. User with id {} not found", id);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}

			service.delete(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
