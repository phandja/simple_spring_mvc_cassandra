package br.com.example.api;

import br.com.example.api.model.User;
import br.com.example.api.service.UserService;
import br.com.example.api.service.impl.UserServiceImpl;

public class Application {
	
	public static void main(String [] args){
		User u = new User(5, "Fatih");
		
		UserService s = new UserServiceImpl();
		
		s.create(u);
		
	}

}
