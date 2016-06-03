package tatuputto.opinnaytetyo.service;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.controllers.LoginController;
import tatuputto.opinnaytetyo.dao.AuthorizedConnectionBasic;
import tatuputto.opinnaytetyo.domain.User;
import tatuputto.opinnaytetyo.json.ParseAuthorizationInfo;


@Service
public class CheckAccessTokenCookie {
	private AuthorizedConnectionBasic connection;
	private ParseAuthorizationInfo parse;
	private String responseContent[];
	
	
	@Autowired
	public CheckAccessTokenCookie(AuthorizedConnectionBasic connection, ParseAuthorizationInfo parse) {
		this.connection = connection;
		this.parse = parse;
	}
	
	
	public User validAccessTokenCookie(Cookie[] cookies) {
		Cookie cookie = null;
	    String accessToken = "";
		
	    //Tarkistetaan löytyykö accesstokenin sisältävää evästettä
	    if(cookies != null) {
	    	for (int i = 0; i < cookies.length; i++) {
	    		cookie = cookies[i];
	    		
	    		if(cookie.getName().equals("accesstoken")) {
	    			accessToken = cookie.getValue();
	    			isValidAccessToken(accessToken);
	    			
	    			//Jos accesstoken ei ole enää voimassa vastauksena saadaan vastauskoodi 404 - not found
		    		if(responseContent[0].equals("200")) {
		    			System.out.println("Access token on voimassa.");
		    			
		    			User user = parse.parseJSON(responseContent[2]);
		    			user.setAccessToken(accessToken);
		    			
		    			return user;
		    		}
		    		else {
		    			System.out.println("Access token ei ole enää voimassa.");
		    	    	return null;
		    		}
	    		}	
	    	}    	    	 
	    }
	    return null;    
	}
	
	
	private void isValidAccessToken(String accessToken) {
		//Liitetään urliin get-parametreiksi sovellukselle rekisteröity id ja secret
		String id = LoginController.clientId;
		String secret = LoginController.clientSecret;
		String url = "https://api.github.com/applications/" + id + "/tokens/" + accessToken + "";
		
		responseContent = connection.formConnection("GET", url, "", id, secret);
	}
}
