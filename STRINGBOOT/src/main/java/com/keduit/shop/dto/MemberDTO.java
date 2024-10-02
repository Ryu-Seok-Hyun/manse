package com.keduit.shop.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

// 로그인과 관련.
// (데이터 전송을 위한 객체) 회원 관련 데이터 전송 객체로, 회원가입 시 사용자로부터 받은 데이터를 저장하고 전달하는 역할.

//이거 만들고 멤버DTO로 이동.
@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
@Setter // 모든 필드에 대한 setter 메서드를 자동 생성
@ToString // 객체의 문자열 표현을 자동으로 생성
public class MemberDTO {

  @NotBlank(message = "이름은 필수 입력입니다") // 이름은 null, 길이가 0이거나 공백일 수 없음
  private String name;

  @NotEmpty(message = "이메일은 필수 입력입니다") // 이메일은 null이거나 길이가 0일 수 없음
  @Email(message = "이메일 형식으로 입력해주세요") // 이메일 형식이 맞는지 체크
  private String email;

  @NotEmpty(message = "비밀번호는 필수입력입니다") // 비밀번호는 null이거나 길이가 0일 수 없음
  @Length(min = 4, max = 16, message = "비밀번호는 4~16 자리로 입력해주세요") // 비밀번호 길이 4~16 자리로 제한
  private String password;

  @NotEmpty(message = "주소는 필수 입력입니다") // 주소는 null이거나 길이가 0일 수 없음
  private String address;
}
