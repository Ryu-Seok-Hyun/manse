package com.keduit.shop.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@EntityListeners(value = {AuditingEntityListener.class})
// 이 클래스의 엔티티 리스너로 AuditingEntityListener를 사용해
@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
@MappedSuperclass // 이 클래스는 다른 엔티티에서 상속 가능, 테이블로 매핑되지 않음
public abstract class BaseEntity extends BaseTimeEntity {

  @CreatedBy // 엔티티 생성 시 자동으로 생성자를 기록
  @Column(updatable = false) // 생성자는 업데이트 시 변경되지 않도록 설정
  private String createdBy;

  @LastModifiedBy // 마지막 수정자를 자동으로 기록
  private String modifiedBy; // 수정자
}
