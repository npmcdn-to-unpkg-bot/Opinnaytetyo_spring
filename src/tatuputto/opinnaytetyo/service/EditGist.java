package tatuputto.opinnaytetyo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;

/**
 * Tämä luokka hoitaa gistin poistamispyynnön lähettämisen.
 * @author Tatu
 *
 */
@Service
public class EditGist {

	private AuthorizedConnectionOauth connection;
	private CheckGistOwnership checkOwner; 
	
	@Autowired
	public EditGist(AuthorizedConnectionOauth connection, CheckGistOwnership checkOwner) {
		this.connection = connection;
		this.checkOwner = checkOwner;
	}
	
	
	/**
	 * Suoritetaan gistin muokkaus.
	 * @param gistId Gistin id.
	 * @param data Gistiin tehtävät muutokset JSON-muodossa.
	 * @param userId Käyttäjän id.
	 * @param accessToken Käyttäjän access token.
	 * @return String[], palautetaan vastauskoodit.
	 */
	public String[] edit(String gistId, String data, int userId, String accessToken) {
		//Lähetään gistin poistamispyyntö jos kirjautunut käyttäjä on gistin omistaja
		if(checkOwner.isOwner(gistId, userId, accessToken)) {
			String url = "https://api.github.com/gists/" + gistId;
			return connection.formConnection("PATCH", url, data, accessToken);
		}
		else {
			String[] response = {"403", "Forbidden"};
			return response;
		}
	}	
	
}
