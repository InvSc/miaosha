package com.invsc.miaosha.exception;

import com.invsc.miaosha.result.CodeMsg;
import com.invsc.miaosha.result.Result;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.apache.naming.factory.ResourceLinkFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
		if (e instanceof GlobalException) {
			GlobalException exception = (GlobalException) e;
			return Result.error(exception.getCodeMsg());
		} else if (e instanceof BindException) {
			BindException exception = (BindException) e;
			List<ObjectError> allErrors = exception.getAllErrors();
			ObjectError error = allErrors.get(0);
			String msg = error.getDefaultMessage();
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		} else {
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
}
