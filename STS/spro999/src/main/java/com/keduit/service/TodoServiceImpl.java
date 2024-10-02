package com.keduit.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.keduit.domain.TodoVO;
import com.keduit.mapper.TodoMapper;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
@ToString


public class TodoServiceImpl implements TodoService {

	private final TodoMapper mapper;
	
	@Override
	public List<TodoVO> getList() {
		log.info("-------- getList-----");
		return mapper.getList();
	}

	@Override
	public TodoVO get(Long tno) {
		return mapper.get(tno);
	}
	
	@Override
	public boolean register(TodoVO todo) {
		return mapper.insert(todo) == 1;
	}
	
	@Override
	public TodoVO read(int tno) {
		return mapper.read(tno);
	}
	
	@Override
	public boolean remove(int tno) {
		int result = mapper.delete(tno);
		return result == 1;
	}

	@Override
	public boolean modify(TodoVO todo) {
		return mapper.update(todo) == 1;
	}
	
	
	
	

}
