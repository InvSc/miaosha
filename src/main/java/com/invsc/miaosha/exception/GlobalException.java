package com.invsc.miaosha.exception;

import com.invsc.miaosha.result.CodeMsg;

public class GlobalException extends RuntimeException{
	private static final long serialVersionUID = 2312855085679456456L;
	private CodeMsg codeMsg;
	public GlobalException(CodeMsg codeMsg) {
		super(codeMsg.toString());
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}
}
