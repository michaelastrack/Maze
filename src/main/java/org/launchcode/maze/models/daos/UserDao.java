package org.launchcode.maze.models.daos;

import javax.transaction.Transactional;

import org.launchcode.maze.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserDao extends CrudRepository <User, Integer> {

		User findByUid(int id);
		
		User findByUsername(String name);
}
