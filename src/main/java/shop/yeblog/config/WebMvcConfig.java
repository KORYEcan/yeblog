package shop.yeblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  //CORS 설정
  //interceptor 설정 가능


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    WebMvcConfigurer.super.addResourceHandlers(registry);
    //1.절대 경로 file:///c:/upload/
    //2. 상대 경로 file:./upload/

    registry
        .addResourceHandler("/upload/**")
        .addResourceLocations("file:"+"./upload/")
        .setCachePeriod(60*60)  //초 단위 -> 한시간
        .resourceChain(true)
        .addResolver(new PathResourceResolver());
  }
}
