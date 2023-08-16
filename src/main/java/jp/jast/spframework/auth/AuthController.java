package jp.jast.spframework.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
	@RequestMapping("/authError")
	public String error() {
		return "/system/error";
	}
}
