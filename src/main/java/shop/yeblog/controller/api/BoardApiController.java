package shop.yeblog.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.yeblog.core.auth.MyUserDetails;
import shop.yeblog.dto.ResponseDTO;
import shop.yeblog.model.reply.Reply;
import shop.yeblog.service.BoardService;
import shop.yeblog.service.ReplyService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BoardApiController {


}
