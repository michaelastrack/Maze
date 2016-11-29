package org.launchcode.maze.models.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.maze.models.Maze;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface MazeDao extends CrudRepository<Maze, Integer>{

	List<Maze> findAll();
	
	Maze findByUid (int uid);
	
	Maze findByName (String name);
}
