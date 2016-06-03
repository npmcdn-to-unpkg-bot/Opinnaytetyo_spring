package tatuputto.opinnaytetyo.domain;

import java.util.ArrayList;

public class Gist {
	private String id;
	private String description;
	private String url;
	private User owner;
	private boolean isOwnedByCurrentUser;
	private ArrayList<GistFile> files;
	//TODO hae tarkemmat tiedot: omistaja, luonti/p�ivitys aika, lyhennetty yms.
	
	
	public Gist(String id, String description, String url, ArrayList<GistFile> files) {
		this.id = id;
		this.description = description;
		this.url = url;
		this.files = files;
	}
	
	public Gist(String id, String description, String url, User owner, ArrayList<GistFile> files) {
		this.id = id;
		this.description = description;
		this.url = url;
		this.owner = owner;
		this.files = files;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isOwnedByCurrentUser() {
		return isOwnedByCurrentUser;
	}
	public void setOwnedByCurrentUser(boolean isOwnedByCurrentUser) {
		this.isOwnedByCurrentUser = isOwnedByCurrentUser;
	}

	public ArrayList<GistFile> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<GistFile> files) {
		this.files = files;
	}


	public String toString() {
		return "ID: " + this.id + "\nKuvaus: " + this.description + "\nTiedostot: " + this.files;  
	}
	
}
