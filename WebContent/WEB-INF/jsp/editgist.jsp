<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.ArrayList" %>
<%@ page import="tatuputto.opinnaytetyo.domain.Gist" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript"></script>
<script src="http://localhost:8080/Opinnaytetyo_spring/js/AssistGistEdit.js" type="text/javascript"></script>

<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>
<link href="http://localhost:8080/Opinnaytetyo_spring/css/Header.css" rel="stylesheet" type="text/css"/>
<link href="http://localhost:8080/Opinnaytetyo_spring/css/EditGist.css" rel="stylesheet" type="text/css"/>

<title>Edit gist</title>
</head>
<body>
<div class="container">
	<%@ include file="header.jsp" %>
	<!-- Listataan gistin sisältämät tiedostot -->
	<div class="content">
		<div class="files">
			<c:choose>
			    <c:when test="${gist != null}">
			     	<input type="hidden" class="gistId" value="${gist.getId()}"/>
					<input type="text" class="description" value="${gist.getDescription()}"></input><br><br>
					
					<c:forEach items="${gist.getFiles()}" var="file" varStatus="fileIndex">
						<c:set var="fileNum" value="${'gistFile'}${fileIndex.index}"/>
						<c:set var="editorNum" value="${'editor'}${fileIndex.index}"/>
						
						<div class="${fileNum}">
							<div class="fileInfo">
								<input type="text" class="filename" value="${file.getFilename()}"></input>
								<input type="button" class="removeFile" value="Poista"/>
							</div>
							<div id="${editorNum}">
								<p id="content">${file.getContent()}</p>	
							</div>
						</div>
					</c:forEach>
				</c:when>
				
				<c:otherwise>
					<c:out value="Gistiä ei löytynyt"></c:out>
				</c:otherwise>
			</c:choose>
			
		</div>
		<div class="buttons">
			<input type="button" class="addFile" value="Lisää tiedosto"/>
			<input type="button" class="updateGist" value="Päivitä" style="float: right;"/>
		</div>
			
	</div>
</div>
</body>
</html>