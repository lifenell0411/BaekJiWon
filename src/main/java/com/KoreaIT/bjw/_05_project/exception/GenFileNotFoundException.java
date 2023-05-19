package com.KoreaIT.bjw._05_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// 예외가 발생하면 예외 객체를 생성하여 해당 예외를 알리고 예외를 잡아서 처리
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "genFile not found")
public class GenFileNotFoundException extends RuntimeException {
}