package tatuputto.opinnaytetyo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.controllers.LoginController;
import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.dao.UnauthorizedConnection;
import tatuputto.opinnaytetyo.json.ParseMultipleGists;

@Service
public class GetAccessToken {
	private UnauthorizedConnection connection;
	
	
	@Autowired
	public GetAccessToken(UnauthorizedConnection connection) {
		this.connection = connection;
	}
	
	
	public String getToken(String code) {
		String url = "https://github.com/login/oauth/access_token?client_id=" + 
				LoginController.clientId + "&client_secret=" + LoginController.clientSecret + "&code=" + code;
 
   		//Erotellaan access token saadusta vastauksesta
   		String[] responseContent = connection.formConnection("GET", url, "");
        String[] token = responseContent[2].split("&");
        token = token[0].split("=");
        
        return token[1];
	}
}
