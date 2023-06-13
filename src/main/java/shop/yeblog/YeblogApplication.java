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

import java.util.Arrays;

@SpringBootApplication
public class YeblogApplication {

  @Profile("dev")
  @Bean    //프로젝트 시작될때 들어가는 더미 데이터: 개발 시간 줄여줄려고!
  CommandLineRunner init(UserRepository userRepository, BoardRepository boardRepository, BCryptPasswordEncoder passwordEncoder){
    return args -> {
      User ssar= User.builder()
          .username("ssar")
          .password(passwordEncoder.encode("1234"))
          .email("ssar@nate.com")
          .role("USER")
          .profile("person.png")
          .status(true)
          .build();

      User cos = User.builder()
          .username("cos")
          .password(passwordEncoder.encode("7890"))
          .email("cos@nate.com")
          .role("USER")
          .profile("person.png")
          .status(true)
          .build();
     userRepository.saveAll(Arrays.asList(ssar,cos));

     Board b1 = Board.builder()
         .title("제목1")
         .content("내용1")
         .user(ssar)
         .thumbnail("/upload/person.png")
                .build();
     Board b2 = Board.builder()
         .title("제목2")
         .content("내용2")
         .user(ssar)
         .thumbnail("/upload/person.png")
                .build();
boardRepository.saveAll(Arrays.asList(b1,b2));

    };
  }

  public static void main(String[] args) {
    SpringApplication.run(YeblogApplication.class, args);
  }

}
