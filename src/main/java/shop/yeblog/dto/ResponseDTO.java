package shop.yeblog.dto;

import org.springframework.http.HttpStatus;

public class ResponseDTO<T> {
  private Integer status;
  private String msg;
  private T data;

  public ResponseDTO() {
    this.status= HttpStatus.OK.value();
    this.msg="성공";
    this.data=null;
  }

  public ResponseDTO<?> data(T data){
    this.data= data;
    return this;
  }

  public ResponseDTO<?> fail(HttpStatus httpStatus, String msg, T data){
    this.status = httpStatus.value();
    this.msg=msg;
    this.data=data;
    return this;
  }
}
