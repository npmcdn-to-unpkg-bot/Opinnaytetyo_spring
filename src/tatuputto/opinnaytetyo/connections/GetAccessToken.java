package tatuputto.opinnaytetyo.connections;


public class GetAccessToken {
	
	protected String getToken(String code) {
		String url = "https://github.com/login/oauth/access_token?client_id=" + 
				LoginController.clientId + "&client_secret=" + LoginController.clientSecret + "&code=" + code;
 
   		//Erotellaan access token saadusta vastauksesta
   		String[] responseContent = new UnauthorizedConnection().formConnection("GET", url, "");
        String[] token = responseContent[2].split("&");
        token = token[0].split("=");
        
        return token[1];
	}
}
