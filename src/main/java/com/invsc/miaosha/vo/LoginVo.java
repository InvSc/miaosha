package com.invsc.miaosha.vo;

import com.invsc.miaosha.validator.IsMobile;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ToString
@Setter
@Getter
public class LoginVo {
	@NotNull
	@IsMobile
	private String mobile;
	@NotNull
	@Length(min = 32)
	private String password;
}
