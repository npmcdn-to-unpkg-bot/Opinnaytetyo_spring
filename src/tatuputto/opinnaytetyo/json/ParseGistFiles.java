package tatuputto.opinnaytetyo.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ParseGistFiles {
	public JSONObject parseJSON(String JSONresponse) {
		try {
			JSONObject jObject = new JSONObject(JSONresponse);
			
			JSONObject files = jObject.getJSONObject("files");
			JSONObject owner = jObject.getJSONObject("owner");
			String login = owner.getString("login");
			String avatarUrl = owner.getString("avatar_url");	
		
			
			JSONObject obj = new JSONObject();
			obj.put("files", files);
			obj.put("login", login);
			obj.put("avatarUrl", avatarUrl);
			
			return obj;
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
