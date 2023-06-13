package shop.yeblog.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.bcel.BcelAnnotation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeblog.dto.user.UserRequest;
import shop.yeblog.model.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;   //Security Bean 등록함



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
}
