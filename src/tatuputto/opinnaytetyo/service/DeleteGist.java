package tatuputto.opinnaytetyo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;

/**
 * Gistin poistaminen.
 */
@Service
public class DeleteGist {
       
	private AuthorizedConnectionOauth connection;
	private CheckGistOwnership checkOwner;
	
	@Autowired
	public DeleteGist(AuthorizedConnectionOauth connection, CheckGistOwnership checkOwner) {
		this.connection = connection;
		this.checkOwner = checkOwner;
	}
	
	
	/**
	 * Lähetetään poistamispyyntö valitusta gististä.
	 */ 
	public void deleteGist(String gistId, int userId, String accessToken) {
		//Lähetään gistin poistamispyyntö jos kirjautunut käyttäjä on gistin omistaja
		if(checkOwner.isOwner(gistId, userId, accessToken)) {
			String deleteUrl = "https://api.github.com/gists/" + gistId;
			connection.formConnection("DELETE", deleteUrl, "", accessToken);			
		}	
	}

}
