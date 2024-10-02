package com.keduit.service;

import java.util.List;

import com.keduit.domain.TodoVO;

public interface TodoService {

		public List<TodoVO> getList();
		
		public TodoVO get(Long tno);
		
		public TodoVO read(int tno);
		
		public boolean register(TodoVO todo);
		
		public boolean modify(TodoVO todo);
		
		public boolean remove(int tno);

}
