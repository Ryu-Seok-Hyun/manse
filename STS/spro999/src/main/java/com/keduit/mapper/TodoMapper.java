package com.keduit.mapper;

import java.util.List;

import com.keduit.domain.TodoVO;

public interface TodoMapper {
	
	public List<TodoVO> getList();
	
	public TodoVO get(Long tno);
	
	public Long insert(TodoVO todo);
	
	public TodoVO read(int tno);
	
	public boolean modify(TodoVO todo);

	public int update(TodoVO todo);
	
	public int delete(int tno);

	

}
