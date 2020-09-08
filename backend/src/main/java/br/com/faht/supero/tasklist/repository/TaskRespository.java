package br.com.faht.supero.tasklist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faht.supero.tasklist.model.Task;

public interface TaskRespository extends JpaRepository<Task, UUID> {

}
