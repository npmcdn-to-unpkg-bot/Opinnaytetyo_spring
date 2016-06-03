package tatuputto.opinnaytetyo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatuputto.opinnaytetyo.dao.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.domain.Gist;
import tatuputto.opinnaytetyo.json.ParseSingleGist;

/**
 * Servlet implementation class GetSingleGist
 */

@Service
public class GetSingleGist {

	private AuthorizedConnectionOauth connection;
	private ParseSingleGist parse;
	
	@Autowired
	public GetSingleGist(AuthorizedConnectionOauth connection, ParseSingleGist parse) {
		this.connection = connection;
		this.parse = parse;
	}
      
    /*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		String gistId = request.getParameter("id");
		String url = "https://api.github.com/gists/" + gistId;
		String accessToken = (String)session.getAttribute("accessToken");
		int userId = (int)session.getAttribute("userId");
		
		String[] responseContent;
		Gist gist = null;
		
		responseContent = new AuthorizedConnectionOauth().formConnection("GET", url, "", accessToken);
		gist = new ParseSingleGistJSON().parseJSON(responseContent[2]);
		
		if(gist.getOwner().getId() == userId) {
			request.setAttribute("gistOwner", true);
		}
		else {
			request.setAttribute("gistOwner", false);
		}
		
		request.setAttribute("gist", gist);
		request.setAttribute("id", gistId);
			
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsps/SingleGist.jsp");
		rd.forward(request, response);
	}*/
	
	public Gist getSingleGist(String gistId, int userId, String accessToken) {
		String url = "https://api.github.com/gists/" + gistId;
		String[] responseContent = connection.formConnection("GET", url, "", accessToken);
		Gist gist = parse.parseJSON(responseContent[2]);
		
		if(gist.getOwner().getId() == userId) {
			gist.setOwnedByCurrentUser(true);
		}
		else {
			gist.setOwnedByCurrentUser(false);
		}
		
		return gist;
	}
	
}
