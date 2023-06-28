package shop.yeblog.model.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;
import shop.yeblog.core.exception.ssr.Exception400;
import shop.yeblog.model.reply.Reply;
import shop.yeblog.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

  @OneToMany(mappedBy = "board", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, orphanRemoval = true)  //mappedBy 연관관계의 주인이 아니다 (FK가 아님) DB에 칼럼을 만들지 않기
  @JsonIgnoreProperties({"board"})
  @OrderBy("id desc")
  private List<Reply> replyList= new ArrayList<>();  //댓글

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

  public static Board checkBoardId(BoardRepository boardRepository, Long id){
    return boardRepository.findByIdFetchUser(id).orElseThrow(
        ()-> new Exception400("id", "게시글 아이디를 찾을수 없습니다.")
    );
  }

}
