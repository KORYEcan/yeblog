package shop.yeblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeblog.core.exception.ssr.Exception400;
import shop.yeblog.core.exception.ssr.Exception500;
import shop.yeblog.dto.reply.ReplyRequest;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.board.BoardRepository;
import shop.yeblog.model.reply.Reply;
import shop.yeblog.model.reply.ReplyRepository;
import shop.yeblog.model.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {

  private final ReplyRepository replyRepository;
  private final BoardRepository boardRepository;


  @Transactional
  public void writeReply(User user, Long boardId, ReplyRequest requestReply) {
    Board board = Board.checkBoardId(boardRepository,boardId);
    try {
      Reply reply = replyRepository.save(Reply.builder()
          .board(board)
          .content(requestReply.getContent())
          .user(user)
          .build());
      reply.syncBoard(board);
    } catch (Exception e) {
      throw new Exception500("댓글 작성 실패 : " + e.getMessage());
    }
  }





}