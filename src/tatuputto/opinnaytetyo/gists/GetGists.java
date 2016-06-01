package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.ParseMultipleGistsJSON;
import tatuputto.opinnaytetyo.gists.Gist;

import java.util.ArrayList;

public class GetGists {

	protected ArrayList<Gist> getGists(String fetchMethod, String accessToken) {
		String[] responseContent = null;
		String url = "";
		//Haetaan käyttäjän gistit
		if(fetchMethod.equals("user")) {
			url = "https://api.github.com/gists";
		}
		//Haetaan kaikkien käyttäjien julkisia gistejä uusimmista vanhimpiin
		else if(fetchMethod.equals("all")) {
			url = "https://api.github.com/gists/public?page=1&per_page=100";
		}
	
		responseContent = new AuthorizedConnectionOauth().formConnection("GET", url, "", accessToken);
		return new ParseMultipleGistsJSON().parseJSON(responseContent[2]);
	
	}
	
}
