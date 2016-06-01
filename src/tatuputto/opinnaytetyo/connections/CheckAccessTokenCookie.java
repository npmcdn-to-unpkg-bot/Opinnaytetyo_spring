package tatuputto.opinnaytetyo.connections;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.gists.User;
import tatuputto.opinnaytetyo.json.ParseAuthorizationInfo;

public class CheckAccessTokenCookie {
	private String responseContent[];
			
	protected User validAccessTokenCookie(Cookie[] cookies) {
		Cookie cookie = null;
		//Cookie[] cookies = null;
	    //cookies = request.getCookies();
	    String accessToken = "";
	    boolean tokenCookieFound = false;
		
	    //Tarkistetaan löytyykö accesstokenin sisältävää evästettä
	    if(cookies != null) {
	    	for (int i = 0; i < cookies.length; i++) {
	    		cookie = cookies[i];
	    		
	    		if(cookie.getName().equals("accesstoken")) {
	    			tokenCookieFound = true;
	    			accessToken = cookie.getValue();
	    			isValidAccessToken(accessToken);
	    			
	    			//Jos accesstoken ei ole enää voimassa vastauksena saadaan vastauskoodi 404 - not found
		    		if(responseContent[0].equals("200")) {
		    			System.out.println("Access token on voimassa.");
		    			User user = new ParseAuthorizationInfo().parseJSON(responseContent[2]);
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
		
		responseContent = new AuthorizedConnectionBasic().formConnection("GET", url, "", id, secret);
	}
}
