package shop.yeblog.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.yeblog.core.auth.MyUserDetails;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

  //RestAPI 주소 설계 규칙에서 자원에는 복수를 붙인다. boards 정석!!
  @GetMapping({"/","/board"})
  public String main() {
    return "board/main";
  }

  @GetMapping("/s/board/saveForm")  // /s/ 인증되어야지 들어갈수 있도록 만들기
  public String saveForm(){
  return "board/saveForm";
  }


}
