package tatuputto.opinnaytetyo.connections;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tatuputto.opinnaytetyo.gists.User;


@Controller
public class LoginController {
	//Sovellukselle rekisteröidyt avaimet
	protected static final String clientId = "566fea61a0cebae27268";
	protected static final String clientSecret = "87454f258250d9170e31a8f13b51e6a612bd6545";
	
	
	@RequestMapping("/login")
	public ModelAndView showLoginPage(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		User user = new CheckAccessTokenCookie().validAccessTokenCookie(cookies);
		
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
		String token = new GetAccessToken().getToken(codeToExchange);
		
		//Luodaan uusi eväste, jonka arvoksi asetetaan access tokenin arvo
   		Cookie tokenCookie = new Cookie("accesstoken", token);
   		tokenCookie.setMaxAge(60*60*24*7); 
   		response.addCookie(tokenCookie);
		
		return new ModelAndView("redirect:/gists");
		
	}
}
