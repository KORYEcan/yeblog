package shop.yeblog.model.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shop.yeblog.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "board_tb")
@Entity
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne (fetch = FetchType.LAZY)   //Board = Many , User= One  한명의 유저는 여러 게시글을 쓸수있음/ OneToOne은 유저는 한개 게시글밖에 못씀
  private User user;   //DB는 object를 저장할수 없음, FK 자바는 오브젝트를 저장할수 있다.



  private String title;
  @Lob  //4GB
  private String content;
  @Lob  //4GB
  private String thumbnail;
  @JsonIgnore
  private LocalDateTime createdAt;
  @JsonIgnore
  private LocalDateTime updatedAt;


  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

}
