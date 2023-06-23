package shop.yeblog.dto.user;

import lombok.Getter;
import lombok.Setter;
import shop.yeblog.model.user.User;

import javax.validation.constraints.NotEmpty;

public class UserRequest {

  @Getter @Setter
  public static class JoinInDTO{
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;


    public User toEntity(){
      return User.builder()
          .username(this.username)
          .password(password)
          .email(email)
          .role("USER")   //enum 사용해도 됨
          .status(true)
          .profile("person.png")  //profile 사진 관련
          .build();
    }
  }
}
