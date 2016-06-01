package tatuputto.opinnaytetyo.gists;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.ParseSingleGistJSON;



public class GetGistForEditing  {
	
	protected Gist getGistForEditing(String gistId, int userId, String accessToken) {
		ParseSingleGistJSON parse = new ParseSingleGistJSON();
		AuthorizedConnectionOauth AuthConnection = new AuthorizedConnectionOauth();
		
		String url = "https://api.github.com/gists/" + gistId;
		String[] responseContent = AuthConnection.formConnection("GET", url, "", accessToken);
		Gist gist = parse.parseJSON(responseContent[2]);
		
		//Tarkistetaan onko kirjautunut käyttäjä gistin omistaja
		//if(gist.getOwner().getId() == userId) {
			return gist;
		//}
		//Jos ei ole lähetetään vastauskoodi 403 - forbidden
		//else {
		//	return null;
		//}

	}
	
	
	
}
