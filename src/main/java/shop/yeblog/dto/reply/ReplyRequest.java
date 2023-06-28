package shop.yeblog.dto.reply;

import lombok.*;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.reply.Reply;
import shop.yeblog.model.user.User;

@Data
@Builder
@AllArgsConstructor
public class ReplyRequest {
  private int userId;
  private int boardId;
  private String content;

}
