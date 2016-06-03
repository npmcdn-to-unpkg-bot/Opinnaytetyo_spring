package tatuputto.opinnaytetyo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.domain.Gist;
import tatuputto.opinnaytetyo.json.ParseSingleGist;


@Service
public class GetGistForEditing  {
	
	private AuthorizedConnectionOauth connection;
	private ParseSingleGist parse;
	
	
	@Autowired
	public GetGistForEditing(AuthorizedConnectionOauth connection, ParseSingleGist parse) {
		this.connection = connection;
		this.parse = parse;
	}
	
	
	public Gist getGistForEditing(String gistId, int userId, String accessToken) {
		String url = "https://api.github.com/gists/" + gistId;
		String[] responseContent = connection.formConnection("GET", url, "", accessToken);
		Gist gist = parse.parseJSON(responseContent[2]);
		
		//Tarkistetaan onko kirjautunut käyttäjä gistin omistaja
		if(gist.getOwner().getId() == userId) {
			return gist;			
		}
		//Jos ei ole lähetetään vastauskoodi 403 - forbidden
		else {
			return null;
		}

	}
	
	
	
}
