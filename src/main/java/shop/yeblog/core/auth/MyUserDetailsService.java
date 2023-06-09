package shop.yeblog.core.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.yeblog.model.user.User;
import shop.yeblog.model.user.UserRepository;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {


  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   User userPs= userRepository.findByUsername(username).orElseThrow(
       () ->  new UsernameNotFoundException("Bad Credential")   //failureHandler가 처리함

   );
    return new MyUserDetails(userPs);
  }
}
