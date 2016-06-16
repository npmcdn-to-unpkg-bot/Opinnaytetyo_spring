package tatuputto.opinnaytetyo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;


/**
 * Gistin luominen.
 */
@Service
public class CreateGist {
    
	private AuthorizedConnectionOauth connection;
	
	@Autowired
	public CreateGist(AuthorizedConnectionOauth connection) {
		this.connection = connection;
	}
	
	
	public String[] create(String data, String accessToken) {
		//Lähetetään gistille asetut tiedot muunnettavaksi JSON-muotoon
		String url = "https://api.github.com/gists";
		return connection.formConnection("POST", url, data, accessToken);
	}
	
	
	
}
