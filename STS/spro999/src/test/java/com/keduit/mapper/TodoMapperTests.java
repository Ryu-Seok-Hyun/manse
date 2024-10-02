package com.keduit.mapper;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.keduit.domain.TodoVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j

public class TodoMapperTests {
	
	@Setter(onMethod_ = {@Autowired})
	private TodoMapper mapper;
	
	@Test
	public void testGetList() {
		mapper.getList().forEach(todo -> log.info(todo));
	}
	
	@Test
	public void testGetOne() {
		log.info(mapper.get(9L));
	}
	
	@Test
	public void testInsert() {
		TodoVO todo = new TodoVO();
		todo.setTitle("mappertest00");
		todo.setDueDate(Date.valueOf(LocalDate.now()));
		todo.setWriter("user01");
		mapper.insert(todo);
		log.info(todo);
		
	}
	
	@Test
	public void testRead() {
		TodoVO todo = mapper.read(1);
		log.info(todo);
	}
	
	@Test
	public void testUpdate() {
		TodoVO todo = new TodoVO();
		todo.setTno(7L);
		todo.setTitle("수정");
		todo.setDueDate(Date.valueOf("2025-01-26"));
		log.info(mapper.update(todo));
	}
	
	@Test
	public void testDelete() {
		mapper.delete(9);
	}


}
