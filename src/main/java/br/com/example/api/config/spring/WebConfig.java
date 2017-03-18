package br.com.example.api.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.example.api.dao.UserDao;
import br.com.example.api.dao.impl.UserDaoImpl;
import br.com.example.api.service.UserService;
import br.com.example.api.service.impl.UserServiceImpl;

@EnableWebMvc
@Configuration
@ComponentScan("br.com.example")
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Bean
	public UserService userService(){
		return new UserServiceImpl();
	}

	@Bean
	public UserDao userDao(){
		return new UserDaoImpl();
	}
}
