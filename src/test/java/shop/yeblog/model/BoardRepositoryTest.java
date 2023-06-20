package shop.yeblog.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import shop.yeblog.DummyEntity;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.board.BoardRepository;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@DataJpaTest
public class BoardRepositoryTest extends DummyEntity {
  @Autowired
  private BoardRepository boardRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private EntityManager em;
  public void join_good(){
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    User ssar = newUser("ssar", passwordEncoder);
    User cos = newUser("cos", passwordEncoder);
    userRepository.saveAll(Arrays.asList(ssar, cos));
    List<Board> boardList = new ArrayList<>();
    for (int i = 1; i < 11; i++) {
      boardList.add(newBoard("제목" + i, ssar));
    }
    for (int i = 11; i < 21; i++) {
      boardList.add(newBoard("제목" + i, cos));
    }
    boardRepository.saveAll(boardList);
    em.clear();
  }
  private void in_query_good(){
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    List<User> userList = new ArrayList<>();
    for (int i= 1; i< 4; i++){
      userList.add(newUser("user"+i, passwordEncoder));
    }
    userRepository.saveAll(userList);
    List<Board> boardList = new ArrayList<>();
    for (int i = 1; i< 5; i++){
      boardList.add(newBoard("제목"+i ,userList.get(0)));
    }
    for (int i=5; i< 9; i++){
      boardList.add(newBoard("제목"+i,userList.get(1)));
    }
    for (int i=9; i< 11; i++){
      boardList.add(newBoard("제목"+i,userList.get(2)));
    }
    boardRepository.saveAll(boardList);
    em.clear();
  }
  @BeforeEach
  public void setUp(){
    in_query_good();
  }
  @Test
  public void findByUserIds_test(){
    List<Long> userIds=  boardRepository.findUserIdDistinct();
    List<User> userList= userRepository.findByUserIds(userIds);  // user1 ,user2 user3
    List<Board> boardList=boardRepository.findByUserIds(userIds);
  }

  @Test
  public void findAllFetchUser(){
    boardRepository.findAllFetchUser();
  }
  @Test
  public void findAll_test(){
    boardRepository.findAll();
  }
  @Test
  public void findAllV2_test(){
    //Lazy 전략  -> Board만 조회
    List<Board> boardList= boardRepository.findAll();
    //Lazy Loading 하기 (20바퀴)
    boardList.stream().forEach( board -> {
      //Loop 1번에 select (ssar)
      //loop 2~10번 1차 캐싱 (PC)
      //Loop 11번에 select (cos)
      //loop 12~20번 1차 캐싱 (PC)
      System.out.println(board.getUser().getUsername());  // select  2번
    });
  }
  @Test
  public void findAllV3_test() throws JsonProcessingException {
    //Lazy전략 -> Board만 조회
    PageRequest pageRequest = PageRequest.of(0,8, Sort.by("id").descending());
    Page<Board> boardPG= boardRepository.findAll(pageRequest);
    boardPG.getContent().stream().forEach(board -> {
      System.out.println(board.getUser().getUsername()); //select 2번
    });
    String responseBody= new ObjectMapper().writeValueAsString(boardPG);
    System.out.println("디버그: "+ responseBody);
  }
  @Test
  public void findByUserId_test(){
    boardRepository.findByUserId(1L);
  }
}
