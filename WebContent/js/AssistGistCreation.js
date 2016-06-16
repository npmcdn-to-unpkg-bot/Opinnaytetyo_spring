var editors = [];
var editorNum = 1;

//Alustetaan ensimmäinen editori, kun sivusto on ladattu
$(document).ready(function() {
	editor = ace.edit("editor0");
	editor.getSession().setMode("ace/mode/java");
	editor.setOptions({minLines: 20});
	editor.setOptions({maxLines: 60});
	editors.push(editor);
	
	//Aloita julkisen gistin luominen
	$("#createPublic").click(function() {
		initiateGistCreation("true");
	});

	//Aloita salaisen gistin luominen
	$("#createSecret").click(function() {
		initiateGistCreation("false");
	});
	
	//Lisätään kenttä
	$("#addFile").click(function() {
		addFile();
	});

	//Poistetaan kenttä
	$(".files").on("click", ".removeFile", function() {
		$(this).closest("[class^=gistFile]").remove();
	});
});




//Kerätään Gistin luomiseen tarvittava data dokumentista ja lähetetään se eteenpäin.
function initiateGistCreation(isPublic) {
	var data = {};
	var files = {};
	var sources = [];
	var filenames = [];
	var description = $(".description").val();
	var filenameFields = $(".filename");

	
	//Lisätään tiedostonimet ja lähdekoodit taulukoihin.
	for(var j = 0; j < filenameFields.length; j++) {
		if($(filenameFields[j]).val() != "") {
			var getValueFrom = $(filenameFields[j]).closest("[class^=gistFile]").attr("class");
			getValueFrom = getValueFrom.substring(getValueFrom.length - 1);
	
			var filename = $(filenameFields[j]).val();
			var source = editors[getValueFrom].getValue();
			var file = {filename: filename, content: source};
			files[filename] = file;
		}
		else {
			alert("Tiedostonimi ei voi olla tyhjä.");
			return;
		}
	}

	
	data["description"] = description;
	data["ispublic"] = isPublic;
	data["files"] = files;

	$.ajax({
		headers: { 
			'Accept': 'application/json',
	        'Content-Type': 'application/json' 
		},
		type: "POST",
	    url: "http://localhost:8080/Opinnaytetyo_spring/create",
	    data: JSON.stringify(data),
	    dataType: "json"
	});
}


//Lisätään kenttä uudelle tiedostolle.
function addFile() {
	$(".files").append("<div class=\"gistFile" + editorNum +  "\">" +
			"<div class=\"fileInfo\">" +
			"<input type=\"text\" class=\"filename\" placeholder=\"Tiedostonimi, esim. File.java\"/>" + 
			"<input type=\"button\" class=\"removeFile\" value=\"Poista\" style=\"margin-left: 4px;\"/>" +
			"</div>" +
			"<div id=\"editor" + editorNum +  "\"</div>" 
	);
	
	//Tehdään luodusta <div> elementistä uusi ACE-editor ja lisätään editori taulukkoon.
	var makeEditorOf = "editor" + editorNum;
	editors.push(ace.edit(makeEditorOf));
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setOptions({minLines: 20});
	editors[editors.length - 1].setOptions({maxLines: 60});
	
	editorNum++;
}

