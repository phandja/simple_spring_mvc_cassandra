package br.com.example.api.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import br.com.example.api.config.cassandra.ConnectionFactory;
import br.com.example.api.dao.UserDao;
import br.com.example.api.model.User;

public class UserDaoImpl implements UserDao {

	private Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
	private static String tableName = "users";

	@Override
	public List<User> getAll() {
		Session session = null;
		try {
			session = ConnectionFactory.openSession();
			Statement select = QueryBuilder.select().from(tableName);

			ResultSet rset = session.execute(select);

			List<User> users = rset.all().parallelStream()
					.map(row -> new User(row.getInt("id"), row.getString("user_name"))).collect(Collectors.toList());

			return users;

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public User findById(int id) {
		Session session = null;
		try {
			session = ConnectionFactory.openSession();

			Statement select = QueryBuilder.select().from(tableName).where(QueryBuilder.eq("id", id));

			ResultSet result = session.execute(select);
			Row row = result.one();
			
			if(row==null){
				return null;
			}
			User user = new User(row);
			
			return user;
			
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public User findByName(String name) {
		Session session = null;
		try {
			session = ConnectionFactory.openSession();

			Statement select = QueryBuilder.select().from(tableName).where(QueryBuilder.eq("user_name", name));

			ResultSet result = session.execute(select);
			Row row = result.one();
			User user = new User(row);

			return user;
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void create(User user) {
		Session session = null;
		
		try {
			session = ConnectionFactory.openSession();

			Statement insert = QueryBuilder.insertInto(tableName).value("id", user.getId()).value("user_name",
					user.getUsername());
			session.execute(insert);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public void update(User user) {
		Session session = null;

		try {
			session = ConnectionFactory.openSession();

			Statement update = QueryBuilder.update(tableName).with(QueryBuilder.set("user_name", user.getUsername()))
					.where(QueryBuilder.eq("id", user.getId()));

			session.execute(update);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}

	}

	@Override
	public void delete(int id) {
		Session session = null;

		try {
			session = ConnectionFactory.openSession();

			Statement delete = QueryBuilder.delete().from(tableName).where(QueryBuilder.eq("id", id));
			session.execute(delete);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public boolean exists(User currentUser) {
		try {
			User user = findById(currentUser.getId());

			return user != null;

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return false;
		}
	}

}
