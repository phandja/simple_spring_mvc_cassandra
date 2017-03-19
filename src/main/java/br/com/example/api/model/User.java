package br.com.example.api.model;

import java.io.Serializable;

import com.datastax.driver.core.Row;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;

	public User(int id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public User(Row row) {
		setId(row.getInt("id"));
		setUsername(row.getString("user_name"));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		User user = (User) obj;

		if (id != user.id)
			return false;
		if (username != null ? !username.equals(user.username) : user.username != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username='" + username + '\'' + '}';
	}
}
