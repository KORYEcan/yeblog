package shop.yeblog.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReplyResponse {
  private Long id;
  private String comment;
  private String username;
}
