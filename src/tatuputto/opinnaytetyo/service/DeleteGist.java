package tatuputto.opinnaytetyo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.domain.Gist;
import tatuputto.opinnaytetyo.json.ParseSingleGist;

/**
 * Gistin poistaminen.
 */
@Service
public class DeleteGist {
       
	private AuthorizedConnectionOauth connection;
	private ParseSingleGist parse;
	
	@Autowired
	public DeleteGist(AuthorizedConnectionOauth connection, ParseSingleGist parse) {
		this.connection = connection;
		this.parse = parse;
	}
	
	
	/**
	 * Lähetetään poistamispyyntö valitusta gististä.
	 */ 
	public void deleteGist(String gistId, int userId, String accessToken) {
		String url = "https://api.github.com/gists/" + gistId;
		String[] responseContent = connection.formConnection("GET", url, "", accessToken);
		Gist gist = parse.parseJSON(responseContent[2]);
		
		//Lähetään gistin poistamispyyntö
		//String[] responseContent = new AuthorizedConnectionOauth().formConnection("DELETE", url, "", accessToken);
		if(gist.getOwner().getId() == userId) {
			String deleteUrl = "https://api.github.com/gists/" + gistId;
			connection.formConnection("DELETE", deleteUrl, "", accessToken);			
		}	
	}

}
