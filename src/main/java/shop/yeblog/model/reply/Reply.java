package shop.yeblog.model.reply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ Table(name = "reply_tb")
@Entity
public class Reply {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private int  id;

  @Column(nullable = false,length = 200)
  private String content;


  @ManyToOne  //여러개의 답변이 하나의 게시물 가능
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

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
