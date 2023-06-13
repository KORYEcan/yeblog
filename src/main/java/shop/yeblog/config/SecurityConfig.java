package shop.yeblog.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@Configuration
public class SecurityConfig {


  @Bean
  BCryptPasswordEncoder passwordEncoder(){    //Ioc에서 관리하는 식으로 사용하기
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    //1. CSRF 해제  --> postman을 사용못하기 떄문에 마지막에 필요하면 이코드를 지우면 됨
   http.csrf().disable();


   //2. frame option 해제
    http.headers().frameOptions().disable();


   //3.Form 로그인 설정
    http.formLogin()
        .loginPage("/loginForm")
        .loginProcessingUrl("/login")   //MyUserDetailsService가 호출  , Post , x-www-form-urlencode
        .successHandler((request, response, authentication) -> {
         log.debug("디버그 : 로그인 성공 ");
         response.sendRedirect("/");
        })
        .failureHandler(((request, response, exception) -> {
          log.debug("디버그 : 로그인 실패 :  "+exception.getMessage());
          response.sendRedirect("/loginForm");
        }));
    //3. 인증, 권한 필터 설정
   http.authorizeRequests(authorize -> authorize.antMatchers("/s/**").authenticated() ///주소가  s로 시작하면 로그인안될시 접속불가
       .anyRequest().permitAll()
   );



    return http.build();

  }

}
