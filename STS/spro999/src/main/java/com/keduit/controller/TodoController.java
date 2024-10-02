package com.keduit.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.keduit.domain.TodoVO;
import com.keduit.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/todo/*")
@Log4j
@RequiredArgsConstructor
public class TodoController {
    
    private final TodoService Service;

    @GetMapping("/list")
    public String list(Model model) {
        log.info("----list-----");
        List<TodoVO> todos = Service.getList();
        model.addAttribute("list", todos);
        return "todo/list";
    }
    
    @GetMapping("/get")
    public void get(@RequestParam("tno") Long tno, Model model) {
        log.info("/get");
        model.addAttribute("todo", Service.get(tno));
    }
    
    @GetMapping("/register")
    public void register() {
        
    }
    
    @PostMapping("/register")
    public String register(TodoVO todo, RedirectAttributes rttr) {
        if (Service.register(todo)) { 
            rttr.addFlashAttribute("result", "success"); 
        }
        return "redirect:/todo/list";
    }
    
    @GetMapping({"/read", "/modify"})
    public void read(@RequestParam("tno") int tno, Model model) { 
        model.addAttribute("todo", Service.read(tno)); 
    }
    
    @PostMapping("/modify")
    public String modify(TodoVO todo, RedirectAttributes rttr) {
        if (Service.modify(todo)) {
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/todo/list";
    }
    
    @PostMapping("/remove")
    public String remove(@RequestParam("tno") int tno, RedirectAttributes rttr) {
        log.info("----------- remove : " + tno);
        boolean isRemoved = Service.remove(tno);
        if (isRemoved) {
            log.info("삭제 성공");
            rttr.addFlashAttribute("result", "success");
        } else {
            log.info("삭제 실패");
            rttr.addFlashAttribute("result", "failure");
        }
        return "redirect:/todo/list"; 
    }

}