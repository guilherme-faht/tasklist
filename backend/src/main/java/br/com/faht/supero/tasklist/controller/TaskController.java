package br.com.faht.supero.tasklist.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.faht.supero.tasklist.model.Task;
import br.com.faht.supero.tasklist.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired 
	private TaskService taskService;
	
	@PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
	public @ResponseBody Task save(@RequestBody Task task) {
	    task = taskService.save(task);
	    return task;
	}
	
	@PostMapping(value = "/save-all", consumes = "application/json", produces = "application/json")
	public void save(@RequestBody List<Task> tasks) {
	    taskService.saveAll(tasks);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable UUID id) {
	    taskService.delete(id);
	}
	
	@GetMapping(value = "/list",  produces = "application/json")
    public ResponseEntity<List<Task>> findAll(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        List<Task> users = taskService.findAllFilter(isDeleted);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
