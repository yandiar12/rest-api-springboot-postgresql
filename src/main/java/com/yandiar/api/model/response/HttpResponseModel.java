/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.model.response;

import java.io.Serializable;
import java.util.Optional;

/**
 *
 * @author USER
 */
public class HttpResponseModel<T> implements Serializable {
  public static final int OK_CODE = 200;
  
  public static final int OK_CREATED_CODE = 201;
  
  public static final int ERROR_CODE_REQUEST = 400;
  
  public static final int ERROR_CODE_SERVER = 500;
  
  public static final String SUCCESS = "success";
  
  public static final String ERROR = "error";
  
  private int status;
  
  private T data;
  
  private String message;
  
  public HttpResponseModel() {}
  
//  public HttpResponseModel(@Nullable T data) {
//    this(200, "success", data);
//  }
//  
//  public HttpResponseModel(int status, String message, T data) {
//    this.status = status;
//    this.message = message;
//    this.data = data;
//  }
//  
//  public static <T> HttpResponseModel<T> ok(@Nullable T data) {
//    return new HttpResponseModel<>(200, "Success", data);
//  }
//  
//  public static <T> HttpResponseModel<T> ok(@Nullable T data, String message) {
//    return new HttpResponseModel<>(200, message, data);
//  }
//  
//  public static <T> HttpResponseModel<T> error(@Nonnull String message) {
//    return error(Integer.valueOf(500), message, null);
//  }
//  
//  public static <T> HttpResponseModel<T> error(@Nonnull Integer status, @Nonnull String message) {
//    return error(status, message, null);
//  }
//  
//  public static <T> HttpResponseModel<T> error(@Nonnull Integer status, @Nonnull String message, @Nullable T data) {
//    Check.hasText(message, "message required !");
//    return new HttpResponseModel<>(status.intValue(), message, data);
//  }
  
  public int getStatus() {
    return this.status;
  }
  
  public HttpResponseModel<T> setStatus(int status) {
    this.status = status;
    return this;
  }
  
  public T getData() {
    return this.data;
  }
  
  public HttpResponseModel<T> setData(T data) {
    this.data = data;
    return this;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public HttpResponseModel<T> setMessage(String message) {
    this.message = message;
    return this;
  }
  
  public Optional<T> optionalData() {
    return Optional.ofNullable(this.data);
  }
  
  public boolean isSuccess() {
    return (this.status == 200 || this.status == 201);
  }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("HttpResponseModel [");
    builder.append("status=");
    builder.append(this.status);
    builder.append(", data=");
    builder.append(this.data);
    builder.append(", message=");
    builder.append(this.message);
    builder.append("]");
    return builder.toString();
  }

}