package shop.yeblog.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeblog.core.exception.ssr.Exception400;
import shop.yeblog.core.exception.ssr.Exception403;
import shop.yeblog.core.exception.ssr.Exception500;
import shop.yeblog.core.util.MyParseUtil;
import shop.yeblog.dto.board.BoardRequest;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.board.BoardQueryRepository;
import shop.yeblog.model.board.BoardRepository;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;


@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardQueryRepository boardQueryRepository;


  @Transactional
  public void write(BoardRequest.SaveInDTO saveInDTO, Long userId) {

    try {

      //1. 유저 존재 확인
      User userPS = userRepository.findById(userId).orElseThrow(
              () -> new RuntimeException("유저를 찾을 수 없습니다.")
      );

      //2. 썸네일 만들기
      String thumbnail = MyParseUtil.getThumbnail(saveInDTO.getContent());

      //3. 게시글 쓰기
      boardRepository.save(saveInDTO.toEntity(userPS, thumbnail));
    } catch (Exception e) {

      throw new RuntimeException("글쓰기 실패 :" + e.getMessage());
    }

  }

  @Transactional(readOnly = true)  //변경감지 X , 고립성(repeatable read)
  public Page<Board> showContent(int page, String keyword) {  //CSR은 DTO로 변경해서 돌려줘야 함.
    // 1. 모든 전략은 Lazy : 이유는 필요할때만 가져오려고
    // 2. 필요할때 는 직접 fetch join을  사용해라
   /*
    isEmpty() -> 문자열의 길이가 0인걸 검사
    isBlank()-> 문자열이 비어있거나 ,공백 문자열(whitespace-only string)인지 검사
   * */
    if (keyword.isBlank()) {
      return boardQueryRepository.findAll(page);
    } else {
      Page<Board> boardPGPS = boardQueryRepository.findAllByKeyword(page, keyword);
      return boardPGPS;
    }
  }   //openinview= false(리턴되고 나면 PS를 뺴도됨)


  public Board showDetail(Long id) {
    Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
            () -> new Exception400("id", "게시글 아이디를 찾을 수 없습니다.")
            //1. lazy loading 하는 것보다 join fetch하는 것이 좋다
            //2 . 왜 lazy를 쓰냐면, 쓸데 없는 조인 쿼리를 줄이기 위해서이다.
            //3. 사실 @ManytoOne은 Eager 전략을 쓰는 것이 좋다.
            //board.PS.getUser().getUsername();
    );
    return boardPS;
  }

  @Transactional
  public void deleteContent(Long id, Long userId) {
    Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
            () -> new Exception400("id", "게시글 아이디를 찾을 수 없습니다.")
    );
    if (boardPS.getUser().getId() != userId) {
      throw new Exception403("권한이 없습니다.");
    }
    try {
      boardRepository.delete(boardPS);
    } catch (Exception e) {
      throw new Exception500("게시글 삭제 실패:" + e.getMessage());
    }
  }


  @Transactional
  public void updateContent(Long boardId, BoardRequest.UpdateInDTO updateInDTO, Long userId) {
    //1. 유저 존재 확인
    Board boardPS = boardRepository.findByIdFetchUser(boardId).orElseThrow(
            () -> new Exception400("id", "게시글 아이디를 찾을 수 없습니다.")
    );
    if (boardPS.getUser().getId() != userId) {
      throw new Exception403("권한이 없습니다.");
    }
    try {
      //2. 썸네일 만들기
      String thumbnail = MyParseUtil.getThumbnail(updateInDTO.getContent());
      //3. 게시글 수정하기
      boardPS.update(updateInDTO.getTitle(), updateInDTO.getContent(), thumbnail);
    } catch (Exception e) {
      throw new RuntimeException("글쓰기 수정 실패 :" + e.getMessage());
    }
  }
}