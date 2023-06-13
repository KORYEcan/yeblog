package shop.yeblog.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.yeblog.dto.user.UserRequest;
import shop.yeblog.service.UserService;


@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;


  // 인증이 되지 않은 상태에서 인증과 관련된 주소는 앞에 엔티티는 적지않는다.
  // write (post):  /리소스/식별자(pk,uk)/save or delete or update
  // read (get) : /리소스/식별자(pk,uk)
  @PostMapping("/join")
  public String join(UserRequest.JoinInDTO joinInDTO){   // x-www-form-urlencoded   //서비스에게 다 책임울 전가하면 된다.
    userService.signUp(joinInDTO);
    return "redirect:/loginForm";   //status code 301
  }

  @GetMapping("/joinForm")
  public String joinForm(){
    return "user/joinForm";
  }

  @GetMapping("/loginForm")
  public String loginForm(){
    return "user/loginForm";
  }



}
