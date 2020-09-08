--
-- SCRIPT DE CRIAÇÃO DOS OBJETOS DO BANCO DE DADOS
--
create table tb_task (
	id BINARY(16) not null,
	created_at datetime(6),
	updated_at datetime(6),
	concluded_at datetime(6),
	deleted_at datetime(6),
	deleted BIT NOT NULL DEFAULT 0,
	description TEXT,
	task_order integer,
	status varchar(255),
	title varchar(255),
	version varchar(255),
	primary key (id)
) engine=InnoDB;