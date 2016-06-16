package tatuputto.opinnaytetyo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.ParseGistFiles;

@Service
public class GetSingleGistAJAX {
	
	private AuthorizedConnectionOauth connection;
	private ParseGistFiles parse;
	
	@Autowired
	public GetSingleGistAJAX(AuthorizedConnectionOauth connection, ParseGistFiles parse) {
		this.connection = connection;
		this.parse = parse;
	}
	
	
	public String getGistAsJSON(String gistId, String accessToken) {
		if(!gistId.equals(null) || !gistId.equals("")) {
			String url = "https://api.github.com/gists/" + gistId;
			String[] responseContent = connection.formConnection("GET", url, "", accessToken);
			
			String data = parse.parseJSON(responseContent[2]).toString();
			
			return data;			
		}
		else {
			return null;
		}		
	}
	
}
