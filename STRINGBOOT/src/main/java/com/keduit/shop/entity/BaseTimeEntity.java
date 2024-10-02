package com.keduit.shop.entity;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


// Auditing을 적용하기 위해 @EntityListeners 애노테이션 추가
@EntityListeners(value = {AuditingEntityListener.class})
// 공통 매핑 정보가 필요할 때 사용하는 애노테이션, 부모 클래스를 상속받는 자식 클래스에 매핑 정보를 제공
@MappedSuperclass
@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
@Setter // 모든 필드에 대한 setter 메서드를 자동 생성
public abstract class BaseTimeEntity {

  @CreatedDate // 엔티티 생성 시 자동으로 등록 시간을 기록
  @Column(updatable = false) // 등록 시간은 업데이트 시 변경되지 않도록 설정
  private LocalDateTime regTime;

  @LastModifiedDate // 마지막 수정 시 자동으로 수정 시간을 기록
  private LocalDateTime updateTime; // 수정 시간
}
