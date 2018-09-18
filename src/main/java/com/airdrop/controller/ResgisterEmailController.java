package com.airdrop.controller;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.dto.UpdateDto;
import com.airdrop.dto._ResultDto;
import com.airdrop.entity.User;
import com.airdrop.service.UserService;
import com.airdrop.util.VerifyUtil;

import io.swagger.annotations.ApiOperation;

@RestController
public class ResgisterEmailController {

	@Autowired
	private UserService userService;

	// 新建一个JavaMailSender 用来发送邮件
	JavaMailSender jms = new JavaMailSender() {
		@Override
		public MimeMessage createMimeMessage() {
			return null;
		}

		@Override
		public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
			return null;
		}

		@Override
		public void send(MimeMessage mimeMessage) throws MailException {

		}

		@Override
		public void send(MimeMessage... mimeMessages) throws MailException {

		}

		@Override
		public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

		}

		@Override
		public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

		}

		@Override
		public void send(SimpleMailMessage simpleMailMessage) throws MailException {

		}

		@Override
		public void send(SimpleMailMessage... simpleMailMessages) throws MailException {

		}
	};

	@ApiOperation("生成验证码")
	@GetMapping("/getcode")
	public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception {

		// 利用图片工具生成图片
		// 第一个参数是生成的验证码，第二个参数是生成的图片
		Object[] objs = VerifyUtil.createImage();
		// 将验证码存入Session

		Object obj = objs[0];


		ServletContext app = request.getServletContext();
		app.setAttribute("imageCode",obj);
		// 将图片输出给浏览器
		BufferedImage image = (BufferedImage) objs[1];
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();

		ImageIO.write(image, "png", os);
	}
	@ApiOperation("邮箱注册")
	@PostMapping("/registerEmail")
	public int registerEmail(@RequestParam String email, @RequestParam String password,@RequestParam String passwordConfirm,
									@RequestParam String captcha, HttpServletRequest request) {
		ServletContext app = request.getServletContext();
		// 验证码通过
		String captchaCode = (String) app.getAttribute("imageCode");

		//如果验证码一致且两次输入的密码一致  如果验证码一致
		if (password.equals(passwordConfirm) && captcha.equalsIgnoreCase(captchaCode)) {
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);


			userService.registerEmail(user);
			return 200;

		}else{
			return 400;
		}


	}


	@ApiOperation("手机注册")
	@PostMapping("/registerPhone")
	public int registerPhone(@RequestParam String phone, @RequestParam String password2,@RequestParam String passwordConfirm2,
							 @RequestParam String captcha2, HttpServletRequest request) {
		ServletContext app = request.getServletContext();
		String captchaCode=(String)app.getAttribute("imageCode");



			////验证码一致 两次输入的密码一致
			if (password2.equals(passwordConfirm2) && captcha2.equalsIgnoreCase(captchaCode)) {
				User user = new User();
				//将手机号存入
				user.setPhone(phone);
				//将密码存入
				user.setPassword(password2);
				//调用service层方法进行注册
				userService.registerPhone(user);
				//返回状态码200
				return 200;
			}else{
				//注册失败,返回状态码400
				return 400;
			}


	}



	@ApiOperation("邮箱的验证码一致")
	@PostMapping("/tologin")
	public _ResultDto tologin(@RequestParam String yzm,HttpSession session) {

		String codeyzm = (String) session.getAttribute("yzm");
		//激活码一致
		if (codeyzm.equals(yzm)) {
			//去登录界面


		}else {
			//否则 还在这个界面


		}

		return new _ResultDto(CodeEnum.CODE_401.getCode(), "账户未登录");
	}



	@ApiOperation("查看手机号是否已经存在")
	@PostMapping("/checkPhoneExist")
	public Integer checkPhoneExist(@RequestParam String phone
							  ) {


		return userService.checkPhoneExist(phone);

	}


	@ApiOperation("查看邮箱地址是否已经存在")
	@PostMapping("/checkEmailExist")
	public Integer checkEmailExist(@RequestParam String email
	) {


		return userService.checkEmailExist(email);

	}


}
