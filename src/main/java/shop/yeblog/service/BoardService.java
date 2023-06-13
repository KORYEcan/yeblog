package shop.yeblog.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeblog.dto.board.BoardRequest;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.board.BoardQueryRepository;
import shop.yeblog.model.board.BoardRepository;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardQueryRepository boardQueryRepository;


  @Transactional
  public void write(BoardRequest.SaveInDTO saveInDTO, Long userId){

    try{

      //1. 유저 존재 확인
      User userPS= userRepository.findById(userId).orElseThrow(
          ()-> new RuntimeException("유저를 찾을 수 없습니다.")
      );
      //2. 게시글 쓰기
      boardRepository.save(saveInDTO.toEntity(userPS));
    }catch (Exception e){

      throw new RuntimeException("글쓰기 실패 :"+ e.getMessage());
    }

  }

  @Transactional(readOnly = true)  //변경감지 X , 고립성(repeatable read)
  public Page<Board> showContent(Pageable pageable) {  //CSR은 DTO로 변경해서 돌려줘야 함.
    // 1. 모든 전략은 Lazy : 이유는 필요할때만 가져오려고
    // 2. 필요할때 는 직접 fetch join을  사용해라
    return boardQueryRepository.findAll(pageable);
  }
}
