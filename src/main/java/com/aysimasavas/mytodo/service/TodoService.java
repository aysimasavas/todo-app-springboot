package com.aysimasavas.mytodo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aysimasavas.mytodo.entities.Todo;
import com.aysimasavas.mytodo.repo.TodoRepository;

@Service
public class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public List<Todo> findAllByUserId(Long userId) {
		return todoRepository.findByUserId(userId);
	}

	public Todo setCompleted(Long id, Boolean completed) {
		Todo todo = todoRepository.getById(id);
		todo.setCompleted(completed);
		todoRepository.save(todo);
		return todo;
	}

	public Todo save(Todo todo) {
		return todoRepository.save(todo);
	}

	public Todo getById(Long id) {
		return todoRepository.getById(id);
	}

	public void delete(Long id) {
		todoRepository.deleteById(id);
	}

}
