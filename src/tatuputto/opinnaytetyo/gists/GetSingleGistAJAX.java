package tatuputto.opinnaytetyo.gists;


import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.GetGistFilesJSON;


public class GetSingleGistAJAX {
	
	protected String getSingleGistJSON(String gistId, String accessToken) {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		GetGistFilesJSON files = new GetGistFilesJSON();
		
		System.out.println(gistId);
		
		if(!gistId.equals(null) || !gistId.equals("")) {
			String url = "https://api.github.com/gists/" + gistId;
			//String accessToken = (String)session.getAttribute("accessToken");
			
			String[] responseContent = connection.formConnection("GET", url, "", accessToken);
			
			
			
			String data = files.GetGistFiles(responseContent[2]).toString();
			
			return data;
			
		}
		else {
			return null;
		}		
	}
	
}
