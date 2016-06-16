package tatuputto.opinnaytetyo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.domain.Gist;
import tatuputto.opinnaytetyo.json.ParseSingleGist;


@Service
public class CheckGistOwnership {
	
	private AuthorizedConnectionOauth connection;
	private ParseSingleGist parse;
	
	@Autowired
	public CheckGistOwnership(AuthorizedConnectionOauth connection, ParseSingleGist parse) {
		this.connection = connection;
		this.parse = parse;
	}
	
	
	public boolean isOwner(String gistId, int userId, String accessToken) {
		String url = "https://api.github.com/gists/" + gistId;
		String[] responseContent = connection.formConnection("GET", url, "", accessToken);
		Gist gist = parse.parseJSON(responseContent[2]);
		
		
		if(gist.getOwner().getId() == userId) {
			return true;
		}	
		else {
			return false;
		}
	}

}
