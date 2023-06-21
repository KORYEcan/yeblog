package shop.yeblog.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.yeblog.core.auth.MyUserDetails;
import shop.yeblog.dto.board.BoardRequest;
import shop.yeblog.model.board.Board;
import shop.yeblog.service.BoardService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

  private final BoardService boardService;


  @PostMapping("/s/board/{id}/delete")
  public String delete(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails){
    //소유자 확인?
    boardService.deleteContent(id, myUserDetails.getUser().getId());
    return "redirect:/";
  }

  //RestAPI 주소 설계 규칙에서 자원에는 복수를 붙인다. boards 정석!!
  @GetMapping({"/","/board"})
  public String main(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "") String keyword,
      Model model
  ) {
    Page<Board>boardPG= boardService.showContent(page,keyword);
    model.addAttribute("boardPG",boardPG);
    return "board/main";
  }

  @GetMapping("/s/board/saveForm")  // /s/ 인증되어야지 들어갈수 있도록 만들기
  public String saveForm(){
  return "board/saveForm";
  }

  @PostMapping("/s/board/save")
  public String save(BoardRequest.SaveInDTO saveInDTO, @AuthenticationPrincipal MyUserDetails myUserDetails){
    boardService.write(saveInDTO, myUserDetails.getUser().getId());
    return "redirect:/";
  }

  @GetMapping("/board/{id}")
  public String detail(@PathVariable Long id, Model model){
    Board board= boardService.showDetail(id);
    model.addAttribute("board",board);
    return "board/detail";   //RequestDispatcher => request 덮어쓰기 기술
  }



}
