package shop.yeblog.model.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shop.yeblog.model.reply.Reply;
import shop.yeblog.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

  @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)  //mappedBy 연관관계의 주인이 아니다 (FK가 아님) DB에 칼럼을 만들지 않기
  private List<Reply> reply;  //댓글

  private String title;  //제목
  @Lob  //4GB
  private String content;
  @Lob  //4GB
  private String thumbnail;
  @JsonIgnore
  private LocalDateTime createdAt;
  @JsonIgnore
  private LocalDateTime updatedAt;


  public void update(String title, String content, String thumbnail){
    this.title=title;
    this.content=content;
    this.thumbnail=thumbnail;
  }

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

}
