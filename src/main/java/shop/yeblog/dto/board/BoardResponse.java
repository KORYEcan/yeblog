package shop.yeblog.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.yeblog.dto.reply.ReplyResponse;
import shop.yeblog.model.user.User;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardResponse {
  private Long id;
  private String title;
  private String content;
  private User user;
  private List<ReplyResponse> replyList;

}
