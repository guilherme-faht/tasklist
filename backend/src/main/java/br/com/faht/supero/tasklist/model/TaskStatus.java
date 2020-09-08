package br.com.faht.supero.tasklist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TaskStatus {
	TO_DO("Para Fazer"),
	IN_PROGRESS("Em Andamento"),
	DONE("Concluído");
	
	@Getter
	private String description;
}
