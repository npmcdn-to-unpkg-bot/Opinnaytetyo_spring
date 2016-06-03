package tatuputto.opinnaytetyo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tatuputto.opinnaytetyo.domain.Gist;
import tatuputto.opinnaytetyo.service.DeleteGist;
import tatuputto.opinnaytetyo.service.GetGistForEditing;
import tatuputto.opinnaytetyo.service.GetGists;
import tatuputto.opinnaytetyo.service.GetSingleGist;
import tatuputto.opinnaytetyo.service.GetSingleGistAJAX;



/**
 * Kontrolleri gistien CRUD-operaatioille.
 */
@Controller
public class GistsController {
		
	private GetGists gists;
	private GetSingleGist singleGist;
	private GetSingleGistAJAX singleGistAjax;
	private GetGistForEditing edit;
	private DeleteGist delete;
	
	
	@Autowired
	public GistsController(GetGists gists, GetSingleGist singleGist, 
			GetSingleGistAJAX singleGistAjax, GetGistForEditing edit, DeleteGist delete) {
		this.gists = gists;
		this.singleGist = singleGist;
		this.singleGistAjax = singleGistAjax;
		this.edit = edit;
		this.delete = delete;
		
	}
	
	
	/**
	 * Asetetaan mallille arvoksi haetut gistit ja palautetaan malli ja näkymä.
	 */
	@RequestMapping("/gists")
    public ModelAndView getMultipleGists(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		String accessToken = ""; 
		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ModelAndView("403forbidden");
		}
		else {
			accessToken = (String)session.getAttribute("accesstoken");
		}
		
        String fetchMethod = request.getParameter("fetch");
        //Haetaan oletusarvona käyttäjän gistejä
        fetchMethod = fetchMethod == null ? "user" : fetchMethod;
        
        //Haetaan gistit
        ArrayList<Gist> gistsArr = gists.getGists(fetchMethod, accessToken);
        
        //Sijoitetaan gistit, sekä hakutapa hashmapiin, joka puolestaan sijoitetaan mallin arvoksi
        HashMap<String, Object> modelValues = new HashMap<String, Object>();
        modelValues.put("fetchMethod", fetchMethod);
        modelValues.put("gistList", gistsArr);
      
        //Palautetaan malli ja näkymä
        return new ModelAndView("gists", "model", modelValues);
    }
	 
	
	@RequestMapping("/singlegist")
    public ModelAndView getSingleGist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		String accessToken = null;
		int userId = 0;
		String gistId = request.getParameter("id");
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ModelAndView("403forbidden");
		}
		else {
			accessToken = (String)session.getAttribute("accesstoken");
			userId = (int)session.getAttribute("userid");
		}
		
		
		Gist gist = singleGist.getSingleGist(gistId, userId, accessToken); 
		
        return new ModelAndView("singlegist", "gist", gist);	
    }
	
	
	//Yksittäisen gistin hakeminen AJAX-kutsuna
		@RequestMapping(value = "/singlegistajax", method = RequestMethod.GET,  produces = "application/json")
		public @ResponseBody String getSingleGistAJAX(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			
			String accessToken = ""; 
			HttpSession session = request.getSession(false);
			if(session == null) {
				return null;
			}
			else {
				accessToken = (String)session.getAttribute("accesstoken");
			}
			
			String gistId = request.getParameter("id");
			return singleGistAjax.getSingleGistAsJSON(gistId, accessToken);
		}
		
	
	@RequestMapping("/creategist")
	public ModelAndView showCreationForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		return new ModelAndView("creategist");
	}
	
	
	@RequestMapping("/editgist")
    public ModelAndView getGistForEditing(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		String accessToken = null;
		int userId = 0;
		String gistId = request.getParameter("id");
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ModelAndView("403forbidden");
		}
		else {
			accessToken = (String)session.getAttribute("accesstoken");
			userId = (int)session.getAttribute("userid");
		}
		
        Gist gist = edit.getGistForEditing(gistId, userId, accessToken);
		
        return new ModelAndView("editgist", "gist", gist);
    }
	 
	
	@RequestMapping("/deletegist")
    public ModelAndView deleteGist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		String accessToken = null;
		int userId = 0;
		String gistId = request.getParameter("id");
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ModelAndView("403forbidden");
		}
		else {
			accessToken = (String)session.getAttribute("accesstoken");
			userId = (int)session.getAttribute("userid");
		}
		
        delete.deleteGist(gistId, userId, accessToken);
		
        return new ModelAndView("redirect:/gists");	
    }
}
