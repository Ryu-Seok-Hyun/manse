package com.keduit.domain;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
public class TodoVO {

	
	private Long tno;
	private String title;
	private String writer;
	private Date dueDate;
	private int finished;
	
}
