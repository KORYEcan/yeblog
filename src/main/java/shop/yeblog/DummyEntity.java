package shop.yeblog;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.yeblog.model.board.Board;
import shop.yeblog.model.reply.Reply;
import shop.yeblog.model.user.User;

import java.util.List;

public class DummyEntity {


  protected User newUser(String username, BCryptPasswordEncoder passwordEncoder){
    return User.builder()
        .username(username)
        .password(passwordEncoder.encode("1234"))
        .email(username+"@nate.com")
        .role("USER")
        .profile("profile.png")
        .build();
  }


  protected Board newBoard(String title, User user){
    return Board.builder()
        .title(title)
        .content(title+"에 대한 내용입니다.")
        .user(user)
        .thumbnail("/image/think.png")
        .build();
  }

}
