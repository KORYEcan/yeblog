package shop.yeblog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;

@SpringBootApplication
public class YeblogApplication {

  @Profile("dev")
  @Bean    //프로젝트 시작될때 들어가는 더미 데이터: 개발 시간 줄여줄려고!
  CommandLineRunner init(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder){
    return args -> {
      User ssar= User.builder()
          .username("ssar")
          .password(passwordEncoder.encode("1234"))
          .email("ssar@nate.com")
          .role("USER")
          .profile("person.png")
          .status(true)
          .build();

      userRepository.save(ssar);
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(YeblogApplication.class, args);
  }

}
