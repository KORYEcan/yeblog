package shop.yeblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.yeblog.core.exception.csr.ExceptionApi400;
import shop.yeblog.core.exception.ssr.Exception400;
import shop.yeblog.core.exception.ssr.Exception500;
import shop.yeblog.core.util.MyFileUtil;
import shop.yeblog.dto.user.UserRequest;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;

import java.util.Optional;

@Transactional(readOnly = true)
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
    //1.유저 중복확인
    Optional<User> userOP= userRepository.findByUsername(joinInDTO.getUsername());
    if (userOP.isPresent()){
      //로그(비정상적인 접근)
      throw  new Exception400("username","유저네임이 중복되었어요");   //Exception400을 하지않는 이유? -> JSON을 응답해줘야 되긴때문에
    }
    try{
      // 2. 패스워드 암호화
      joinInDTO.setPassword(passwordEncoder.encode(joinInDTO.getPassword()));
      // 3. DB 저장 (고립성)
      userRepository.save(joinInDTO.toEntity());
    }catch (Exception e){
      throw new Exception500("회원 가입 실패: "+e.getMessage());
    }
  }  //더티체킹 , DB 세션 종료(OSIV=false)

  public User showProfile(Long id) {    //회원보기
   User userPS= userRepository.findById(id)
       .orElseThrow(()-> new Exception400("id","해당 유저가 존재하지 않습니다."));
   return userPS;
  }

  @Transactional
  public User updateProfile(MultipartFile profile, Long id) {   //회원수정
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

  public void checkUsername(String username) {    //회원아이디 중복확인
    Optional<User> userOP= userRepository.findByUsername(username);
    if (userOP.isPresent()){
      throw  new ExceptionApi400("username","유저네임이 중복되었어요");   //Exception400을 하지않는 이유? -> JSON을 응답해줘야 되긴때문에
    }
  }

  public User showUserInfo(Long id) {
 User userPS=userRepository.findById(id)
     .orElseThrow(() -> new Exception400("id","해당 유저가 존재하지 않습니다."));
 return userPS;
  }


  @Transactional
  public User updateUser(UserRequest.JoinInDTO joinInDTO, Long id, String password, String email) {
    //수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고 ,영속화된 User 를 수정
    //select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서!!
    //영속화된 오브젝트를 변경하면 자동으로 DB에 Update문을 날려줌
    User userPS= userRepository.findById(id)
        .orElseThrow(()-> new Exception400("id","해당 유저가 존재하지 않습니다."));
    try{
      //1. 수정된 패스워드 암호화
      joinInDTO.setPassword(passwordEncoder.encode(password));
      joinInDTO.setEmail(email);
      //2. DB에 수정된 비번과 이메일 저장
      User updateUser= joinInDTO.toEntity();
      userPS.update(updateUser.getPassword(),updateUser.getEmail());
      userRepository.saveAndFlush(updateUser);
      return userPS;
    }catch (Exception e){
      throw new Exception500("회원정보 변경 실패:"+ e.getMessage());
    }
  }
}
