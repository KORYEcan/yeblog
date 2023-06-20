package shop.yeblog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.yeblog.controller.BoardController;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.board.BoardRepository;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class YeblogApplication extends DummyEntity {

  @Profile("dev")
  @Bean    //프로젝트 시작될때 들어가는 더미 데이터: 개발 시간 줄여줄려고!
  CommandLineRunner init(UserRepository userRepository, BoardRepository boardRepository, BCryptPasswordEncoder passwordEncoder){
    return args -> {
      User ssar= newUser("ssar",passwordEncoder);
      User cos= newUser("cos",passwordEncoder);
      userRepository.saveAll(Arrays.asList(ssar,cos));

      List<Board> boardList = new ArrayList<>();
      for (int i= 1; i< 6 ; i++){
        boardList.add(newBoard("제목"+i,ssar));
      }
      for (int i= 7; i< 12 ; i++){
        boardList.add(newBoard("제목"+i,cos));
      }
      boardRepository.saveAll(boardList);
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(YeblogApplication.class, args);
  }

}
