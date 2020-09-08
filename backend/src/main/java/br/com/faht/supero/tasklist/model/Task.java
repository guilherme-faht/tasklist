package br.com.faht.supero.tasklist.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "tb_task")
@SQLDelete(sql = "UPDATE tb_task SET deleted = true, deleted_at = UTC_TIMESTAMP() WHERE id=?")
@FilterDef(name = "deletedTaskFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedTaskFilter", condition = "deleted = :isDeleted")
public class Task {

	@Id
	@Getter
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@Getter
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Getter
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Getter
	@Column(name = "concluded_at")
	private LocalDateTime concludedAt;

	@Getter
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Getter
	@Setter
	@Column(name = "title")
	private String title;

	@Getter
	@Setter
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@Getter
	@Setter
	@Column(name = "version")
	private String version;
	
	@Getter
	@Setter
	@Column(name = "task_order")
	private Integer order;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private TaskStatus status;

	@Column(name = "deleted", columnDefinition = "BIT NOT NULL DEFAULT 0")
	private boolean deleted;

	@PrePersist
	@PreUpdate
	private void prePersist() {
		if (TaskStatus.DONE.equals(status)) {
			concludedAt = LocalDateTime.now();
		}
	}
}
