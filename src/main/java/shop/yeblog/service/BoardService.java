package shop.yeblog.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeblog.dto.board.BoardRequest;
import shop.yeblog.model.board.BoardRepository;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;


@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

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
}
