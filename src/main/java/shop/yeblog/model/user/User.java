package shop.yeblog.model.user;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="user_tb")
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, length = 20)
  private String username;
  @Column(length = 60)      //20자 이하만 받을 예정
  private String password;
  @Column(length = 50)
  private String email;
  private String role;      //USER(고객)
  private String profile;  //유저 프로필 사진의 경로
  private Boolean status;
  private LocalDateTime createAt;
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
