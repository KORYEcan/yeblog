package shop.yeblog.model.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="user_tb")
@Entity     //Table화 시키려면 , User클래스가  MySQL에 테이블이 생성이 됨
public class  User {

  @Id   //PK(Primary Key)
  @GeneratedValue(strategy = GenerationType.IDENTITY)    // 프로젝트에서 연결된 DB의 넘버링 전략을 따라감
  private Long id;   // Oracle ->  시퀀스 (Sequence) / MYSQL-> Auto-increment

  @Column(unique = true, length = 20)
  private String username;     //ID
  @Column(length = 60)      //20자 이하만 받을 예정 -> 해쉬 ( 비밀번호 암호화해서 넘기기 때문에)
  private String password;
  @Column(length = 50)
  private String email;

//  @Enumerated(EnumType.STRING)
  private String role;      //  Enum을 쓰기 / admin,USER(고객)
  private String profile;  //유저 프로필 사진의 경로
  private Boolean status;

  @JsonIgnore
  private LocalDateTime createAt;
  @JsonIgnore
  private LocalDateTime updateAt;

  //회원 수정
 public void update(String password, String email){
   this.password= password;
   this.email= email;
 }

  //프로필 사진 변경
  public void changeProfile(String profile){
   this.profile= profile;
  }

  @PrePersist
  protected void onCreate(){
    this.createAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate(){
    this.updateAt = LocalDateTime.now();
  }




}
