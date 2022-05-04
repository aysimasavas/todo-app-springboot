package com.aysimasavas.mytodo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aysimasavas.mytodo.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findByUserId(Long userId);

}
