package shop.yeblog.dto.board;

import lombok.Getter;
import lombok.Setter;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.user.User;

public class BoardRequest {

  @Getter @Setter
  public static class SaveInDTO{
    private String title;
    private String content;


    public Board toEntity(User user,String thumbnail){
       return Board.builder()
           .user(user)
           .title(title)
           .content(content)
           .thumbnail(thumbnail)
           .build();
    }
  }
}
