package shop.yeblog.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.bcel.BcelAnnotation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.yeblog.core.exception.ssr.Exception400;
import shop.yeblog.core.exception.ssr.Exception500;
import shop.yeblog.core.util.MyFileUtil;
import shop.yeblog.dto.user.UserRequest;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;   //Security Bean 등록함

   @Value("${file.path}")
   private String uploadFolder;

  // insert, update, delete -> try catch  처리
  @Transactional
  public void signUp(UserRequest.JoinInDTO joinInDTO){    //회원가입

    try{
      // 1. 패스워드 암호화
      joinInDTO.setPassword(passwordEncoder.encode(joinInDTO.getPassword()));

      // 2. DB 저장
      userRepository.save(joinInDTO.toEntity());
    }catch (Exception e){
      throw new RuntimeException("회원 가입 오류: "+e.getMessage());
    }
  }  //더티체킹 , DB 세션 종료(OSIV=false)

  public User showProfile(Long id) {
   User userPS= userRepository.findById(id)
       .orElseThrow(()-> new Exception400("id","해당 유저가 존재하지 않습니다."));
   return userPS;
  }

  @Transactional
  public User updateProfile(MultipartFile profile, Long id) {
    try{
       String uuidImageName= MyFileUtil.write(uploadFolder,profile);

       User userPS= userRepository.findById(id)
           .orElseThrow(()-> new Exception500("로그인 된 유저가 DB에 존재하지 않음"));
       userPS.changeProfile(uuidImageName);
       return userPS;
    }catch (Exception e){
      throw new Exception500("프로필 사진 등록 실패:"+ e.getMessage());
    }
  } //더티체킹 (업데이트)
}
