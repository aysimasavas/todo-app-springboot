package com.aysimasavas.mytodo.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aysimasavas.mytodo.entities.Todo;
import com.aysimasavas.mytodo.security.JwtTokenProvider;
import com.aysimasavas.mytodo.service.TodoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "todoapi")
public class TodoController {
	private final JwtTokenProvider jwtTokenProvider;
	private final TodoService todoService;

	public TodoController(JwtTokenProvider jwtTokenProvider, TodoService todoService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.todoService = todoService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Todo>> getTodos(@RequestHeader String authorization) {
		String token = authorization.split(" ")[1];
		Long userId = jwtTokenProvider.getUserIdFromJwt(token);
		List<Todo> todos = todoService.findAllByUserId(userId);
		if (todos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(todos);
	}

	@PostMapping("/add")
	public ResponseEntity<Todo> addTodo(@RequestBody Todo todo, @RequestHeader String authorization) {
		String token = authorization.split(" ")[1];
		Long userId = jwtTokenProvider.getUserIdFromJwt(token);
		todo.setUserId(userId);
		if (todo.getContent().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		todoService.save(todo);
		return ResponseEntity.ok(todo);
	}

	@PutMapping("/update")
	public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo, @RequestHeader String authorization) {
		String token = authorization.split(" ")[1];
		Long userId = jwtTokenProvider.getUserIdFromJwt(token);
		todo.setUserId(userId);
		if (todo.getContent().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		todoService.save(todo);
		return ResponseEntity.ok(todo);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
		Todo todo = todoService.getById(id);
		if (todo == null) {
			return ResponseEntity.notFound().build();
		}
		todoService.delete(todo.getId());
		return ResponseEntity.ok("Todo deleted");
	}

	@PostMapping("/complete/{id}")
	public ResponseEntity<String> completeTodo(@PathVariable Long id, @RequestHeader String authorization) {
		String token = authorization.split(" ")[1];
		Long userId = jwtTokenProvider.getUserIdFromJwt(token);
		Todo todo = todoService.getById(id);
		if (todo == null) {
			return ResponseEntity.notFound().build();
		}
		if (!todo.getUserId().equals(userId)) {
			return ResponseEntity.badRequest().build();
		}
		todo.setCompleted(true);
		todoService.save(todo);
		return ResponseEntity.ok("Todo completed");
	}

	@PostMapping("/uncomplete/{id}")
	public ResponseEntity<String> uncompleteTodo(@PathVariable Long id, @RequestHeader String authorization) {

		Todo todo = todoService.getById(id);

		todo.setCompleted(false);
		todoService.save(todo);
		return ResponseEntity.ok("Todo uncompleted");
	}

}
