package tatuputto.opinnaytetyo.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tatuputto.opinnaytetyo.domain.User;
import tatuputto.opinnaytetyo.service.CheckAccessTokenCookie;
import tatuputto.opinnaytetyo.service.GetAccessToken;


@Controller
public class LoginController {
	//Sovellukselle rekisteröidyt avaimet
	public static final String clientId = "566fea61a0cebae27268";
	public static final String clientSecret = "87454f258250d9170e31a8f13b51e6a612bd6545";
	
	private CheckAccessTokenCookie checkCookie;
	private GetAccessToken getAccessToken;
	
	@Autowired
	public LoginController(CheckAccessTokenCookie checkCookie, GetAccessToken getAccessToken) {
		this.checkCookie = checkCookie;
		this.getAccessToken = getAccessToken;
	}
	
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		User user = checkCookie.validAccessTokenCookie(cookies);
		
		if(user != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("loggedin", true);
			session.setAttribute("userid", user.getId());
			session.setAttribute("login", user.getLogin());
			session.setAttribute("avatar", user.getAvatarUrl());
			session.setAttribute("accesstoken", user.getAccessToken());
	
			return new ModelAndView("redirect:/gists");
		}
		else {
			Cookie tokenCookie = new Cookie("accesstoken", "");
	   		tokenCookie.setMaxAge(0); 
	   		response.addCookie(tokenCookie);
			
			return new ModelAndView("login");
		}
	}
	

	@RequestMapping("/authorize")
	public void sendUserToAuthorizationService(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		response.sendRedirect("https://github.com/login/oauth/authorize?client_id=566fea61a0cebae27268&scope=gist");	
	}
	
	
	@RequestMapping("/getaccesstoken")
	public ModelAndView getAccessToken(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		
		String codeToExchange = request.getParameter("code");
		String token = getAccessToken.getToken(codeToExchange);
		
		//Luodaan uusi eväste, jonka arvoksi asetetaan access tokenin arvo
   		Cookie tokenCookie = new Cookie("accesstoken", token);
   		tokenCookie.setMaxAge(60*60*24*7); 
   		response.addCookie(tokenCookie);
		
		return new ModelAndView("redirect:/login");	
	}
	
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		//Tuhotaan sessio
		HttpSession session = request.getSession(false);
		session.invalidate();
		
		//Poistetaan access tokenin sisältävä eväste
		Cookie tokenCookie = new Cookie("accesstoken", "");
		tokenCookie.setMaxAge(0);
		response.addCookie(tokenCookie);
		
		return new ModelAndView("redirect:/login");	
	}
	
	
}
