package shop.yeblog.model.reply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "reply_tb")
@Entity
public class Reply {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private Long id;
  private String content;
  @ManyToOne (fetch = FetchType.LAZY) //여러개의 답변이 하나의 게시물 가능
  private Board board;
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
  @JsonIgnore
  private LocalDateTime createdAt;
  @JsonIgnore
  private LocalDateTime updatedAt;

  public void syncBoard(Board board){
    if (this.board != null) {
      this.board.getReplyList().remove(this);
    }
    this.board=board;
    board.getReplyList().add(this);
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
