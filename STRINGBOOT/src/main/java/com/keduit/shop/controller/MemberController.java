package com.keduit.shop.controller;

import com.keduit.shop.dto.MemberDTO;
import com.keduit.shop.entity.Member;
import com.keduit.shop.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 로그인과 관련.
// (로그인 및 회원가입 요청을 처리하는 클래스) 회원과 관련된 요청을 처리하는 컨트롤러로, 회원가입 및 로그인 요청을 받을 가능성이 높음.

@Controller
@RequestMapping("/members") // "/members" 경로에 대한 요청을 처리하는 컨트롤러야
@RequiredArgsConstructor // 필요한 의존성을 자동으로 주입해주는 생성자 생성
public class MemberController {

  private final MemberService memberService; // 회원 서비스 의존성
  private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 서비스 의존성

  // 회원 가입 폼을 보여주는 메서드
  @GetMapping("/new")
  public String memberForm(Model model) {
    // 새로운 MemberDTO 객체를 모델에 추가해서 폼에 전달해
    model.addAttribute("memberDTO", new MemberDTO());
    return "member/memberForm"; // 회원 가입 폼 뷰를 반환
  }

  // 회원 가입 처리 메서드
  @PostMapping("/new")
  public String memberForm(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
    // MemberDTO의 유효성 체크 결과를 확인
    if (bindingResult.hasErrors()) {
      // 에러가 있으면 폼을 다시 보여줘
      return "/member/memberForm";
    }

    //  @PostMapping("/new")
//  public String memberForm(MemberDTO memberDTO) {
//    Member member = Member.createMember(memberDTO, passwordEncoder);
//    memberService.saveMember(member);
//    return "redirect:/";
//  }

    // 회원 가입 시 이메일 중복 처리
    try {
      // 회원 객체 생성 및 암호화
      Member member = Member.createMember(memberDTO, passwordEncoder);
      // 회원 정보 저장
      memberService.saveMember(member);
    } catch (IllegalStateException e) {
      // 중복 이메일 발생 시 에러 메시지 모델에 추가
      model.addAttribute("errorMessage", e.getMessage());
      return "/member/memberForm"; // 에러가 발생하면 폼을 다시 보여줘
    }
    return "redirect:/"; // 가입 성공 시 메인 페이지로 리다이렉트
  }

  // 로그인 페이지를 보여주는 메서드
  @GetMapping("/login")
  public String loginMember() {
    return "/member/memberLoginForm"; // 로그인 폼 뷰를 반환
  }

  // 로그인 에러를 처리하는 메서드
  @GetMapping("/login/error")
  public String loginError(Model model) {
    // 로그인 에러 메시지를 모델에 추가
    model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해 주세요.");
    return "/member/memberLoginForm"; // 로그인 폼을 다시 보여줘
  }
}
