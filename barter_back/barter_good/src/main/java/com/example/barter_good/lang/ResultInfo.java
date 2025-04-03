package com.example.barter_good.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* 公共返回对象
* @author walnut
* @date 2021/11/7 2:36 下午
* @version 1.0
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultInfo<T> implements Serializable {

   private Integer code;

   private String message;

//    private String path;

   private T data;

   public Integer getCode() {
       return code;
   }

   public void setCode(Integer code) {
       this.code = code;
   }

   public String getMessage() {
       return message;
   }

   public void setMessage(String message) {
       this.message = message;
   }

//    public String getPath() {
//        return path;
//    }

//    public void setPath(String path) {
//        this.path = path;
//    }

   public T getData() {
       return data;
   }

   public void setData(T data) {
       this.data = data;
   }
}