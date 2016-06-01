package tatuputto.opinnaytetyo.gists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



/**
 * Kontrolleri gistien CRUD-operaatioille.
 */
@Controller
public class GistsController {
	
	/**
	 * Asetetaan mallille arvoksi haetut gistit ja palautetaan malli ja näkymä.
	 */
	@RequestMapping("/gists")
    public ModelAndView getMultipleGists(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fetchMethod = request.getParameter("fetch");
        //Haetaan oletusarvona käyttäjän gistejä
        fetchMethod = fetchMethod == null ? "user" : fetchMethod;
        
        //Haetaan gistit
        ArrayList<Gist> gists = new GetGists().getGists(fetchMethod);
        
        //Sijoitetaan gistit, sekä hakutapa hashmapiin, joka puolestaan sijoitetaan mallin arvoksi
        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("fetchMethod", fetchMethod);
        myModel.put("gistList", gists);
      
        //Palautetaan malli ja näkymä
        return new ModelAndView("gists", "model", myModel);
    }
	 
	
	@RequestMapping("/editgist")
    public ModelAndView getGistForEditing(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		String gistId = request.getParameter("id");
       
        GetGistForEditing edit = new GetGistForEditing();
        Gist gist = edit.getGistForEditing(gistId, 123);
		
        return new ModelAndView("editgist", "gist", gist);
    }
	 
	
	
	
	//Yksittäisen gistin hakeminen AJAX-kutsuna
	@RequestMapping(value = "/getsinglegistajax", method = RequestMethod.GET,  produces="application/json")
	public @ResponseBody String getSingleGistAJAX(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String gistId = request.getParameter("id");
	
		return new GetSingleGistAJAX().
				getSingleGistJSON(gistId, "a0d5658b99b77cffe31de0b2b0d54a8aa5635617");
		

	}
	
	

}
