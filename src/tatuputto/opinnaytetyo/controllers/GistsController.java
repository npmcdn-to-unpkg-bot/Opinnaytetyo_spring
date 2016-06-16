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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tatuputto.opinnaytetyo.domain.Gist;
import tatuputto.opinnaytetyo.service.CreateGist;
import tatuputto.opinnaytetyo.service.DeleteGist;
import tatuputto.opinnaytetyo.service.EditGist;
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
	private CreateGist createGist;
	private GetGistForEditing gistToBeEdited;
	private EditGist editGist;
	private DeleteGist deleteGist;
	
	
	@Autowired
	public GistsController(GetGists gists, GetSingleGist singleGist, 
			GetSingleGistAJAX singleGistAjax, CreateGist createGist, 
			GetGistForEditing gistToBeEdited, EditGist editGist, DeleteGist deleteGist) {
		
		this.gists = gists;
		this.singleGist = singleGist;
		this.singleGistAjax = singleGistAjax;
		this.createGist = createGist;
		this.gistToBeEdited = gistToBeEdited;
		this.editGist = editGist;
		this.deleteGist = deleteGist;
		
	}
	
	
	/**
	 * Asetetaan mallille arvoksi haetut gistit ja palautetaan malli ja näkymä.
	 */
	@RequestMapping("/gists")
    public ModelAndView getMultipleGists(@RequestParam(value = "fetch", required = false) String fetchMethod, 
    		@RequestParam(value = "search", required = false) String searchTarget, 
    		HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		String accessToken = ""; 
		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ModelAndView("403forbidden");
		}
		else {
			ArrayList<Gist> gistsArr = null;
			accessToken = (String)session.getAttribute("accesstoken");
			//String fetchMethod = (String)request.getAttribute("fetch");
			//Haetaan oletusarvona käyttäjän gistejä
			fetchMethod = fetchMethod == null ? "user" : fetchMethod;
			
			if(searchTarget == null) {
		        gistsArr = gists.getGists(fetchMethod, accessToken);
			}
			else {
				gistsArr = gists.searchGistsByUser(fetchMethod, searchTarget, accessToken);
			}
	        
	        
	        //Sijoitetaan gistit, sekä hakutapa hashmapiin, joka puolestaan sijoitetaan mallin arvoksi
	        HashMap<String, Object> modelValues = new HashMap<String, Object>();
	        modelValues.put("fetchMethod", fetchMethod);
	        modelValues.put("gistList", gistsArr);
	      
	        //Palautetaan malli ja näkymä
	        return new ModelAndView("gists", "model", modelValues);
		}

        
    }
	 
	
	@RequestMapping("/singlegist")
    public ModelAndView getSingleGist(@RequestParam("id") String gistId, 
    		HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ModelAndView("403forbidden");
		}
		else {
			String accessToken = (String)session.getAttribute("accesstoken");
			int userId = (int)session.getAttribute("userid");
			
			Gist gist = singleGist.getGist(gistId, userId, accessToken); 
			
	        return new ModelAndView("singlegist", "gist", gist);	
		}	
    }
	
	
	//Yksittäisen gistin hakeminen AJAX-kutsuna
	@RequestMapping(value = "/singlegistajax", method = RequestMethod.GET,  produces = "application/json")
	public @ResponseBody String getSingleGistAJAX(@RequestParam("id") String gistId, 
			HttpServletRequest request) 
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if(session == null) {
			return null;
		}
		else {
			String accessToken = (String)session.getAttribute("accesstoken");
			return singleGistAjax.getGistAsJSON(gistId, accessToken);
		}
	}
		
	
	@RequestMapping("/creategist")
	public ModelAndView showCreationForm() 
			throws ServletException, IOException {
		
		return new ModelAndView("creategist");
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String executeCreate(@RequestBody String data, HttpServletRequest request)
            throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "403 - Forbidden";
		}
		else {
			String accessToken = (String)session.getAttribute("accesstoken");
		
			String[] responseContent = createGist.create(data, accessToken);
			return responseContent[0] + responseContent[1];
		}			
	}	
	
	
	@RequestMapping("/editgist")
    public ModelAndView getGistForEditing(@RequestParam("id") String gistId, HttpServletRequest request)
            throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ModelAndView("403forbidden");
		}
		else {
			String accessToken = (String)session.getAttribute("accesstoken");
			int userId = (int)session.getAttribute("userid");
			
			Gist gist = gistToBeEdited.getGist(gistId, userId, accessToken);
				
		    return new ModelAndView("editgist", "gist", gist);
		}
    }
	 
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String executeEdit(@RequestParam("id") String gistId, @RequestBody String data,
    		HttpServletRequest request) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "403 - Forbidden";
		}
		else {
			String accessToken = (String)session.getAttribute("accesstoken");
			int userId = (int)session.getAttribute("userid");
		
			String[] responseContent = editGist.edit(gistId, data, userId, accessToken);
			return responseContent[0] + responseContent[1];
		}			
	}	
		
	
	@RequestMapping("/deletegist")
    public ModelAndView executeDelete(@RequestParam("id") String gistId, HttpServletRequest request)
            throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ModelAndView("403forbidden");
		}
		else {
			String accessToken = (String)session.getAttribute("accesstoken");
			int userId = (int)session.getAttribute("userid");
			
			deleteGist.deleteGist(gistId, userId, accessToken);
				
		    return new ModelAndView("redirect:/gists");	
		}
    }
}
