//package com.airdrop.controller;
//
//import javax.imageio.ImageIO;
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.airdrop.util.VerifyUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.airdrop.config.code.CodeEnum;
//import com.airdrop.dto.UpdateDto;
//import com.airdrop.dto._ResultDto;
//import com.airdrop.entity.User;
//import com.airdrop.service.UserService;
//
//import io.swagger.annotations.ApiOperation;
//
//import java.awt.image.BufferedImage;
//import java.io.OutputStream;
//
//@RestController
//public class ResgisterPhoneController {
//
//
//
//	@Autowired
//	private UserService userService;
//
//	@ApiOperation("生成验证码")
//	@GetMapping("/getcode1")
//	public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception {
//
//		// 利用图片工具生成图片
//		// 第一个参数是生成的验证码，第二个参数是生成的图片
//		Object[] objs = VerifyUtil.createImage();
//		// 将验证码存入Session
//
//		Object obj = objs[0];
//
//
//		ServletContext app = request.getServletContext();
//		app.setAttribute("imageCode2",obj);
//		// 将图片输出给浏览器
//		BufferedImage image = (BufferedImage) objs[1];
//		response.setContentType("image/png");
//		OutputStream os = response.getOutputStream();
//
//		ImageIO.write(image, "png", os);
//	}
//
//	@ApiOperation("手机注册")
//	@PostMapping("/registerPhone")
//	public int registerPhone(@RequestParam String phone, @RequestParam String password2,@RequestParam String passwordConfirm2,
//									@RequestParam String captcha2, HttpSession session) {
//			//生成验证码 放进session之中
//		String captchaCode = (String) session.getAttribute("imageCode2");
//
//		////验证码一致 两次输入的密码一致
//		if(password2.equals(passwordConfirm2) && captcha2.equals(captchaCode)){
//		User user = new User();
//		user.setPhone(phone);
//		user.setPassword(password2);
//		UpdateDto registerPhone = userService.registerPhone(user);
//
//		return 200;
//
//		}
//		return 400;
//
//
//	}}
//
//
