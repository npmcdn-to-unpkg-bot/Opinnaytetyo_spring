<%@ page language="java" contentType="text/html;UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.ArrayList" %>
<%@ page import="tatuputto.opinnaytetyo.domain.Gist" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript"></script>
<script src="http://localhost:8080/Opinnaytetyo_spring/js/ShowSingleGist.js" type="text/javascript"></script>

<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>
<link href="http://localhost:8080/Opinnaytetyo_spring/css/Header.css" rel="stylesheet" type="text/css"/>
<link href="http://localhost:8080/Opinnaytetyo_spring/css/SingleGist.css" rel="stylesheet" type="text/css"/>

<title>Gist</title>
</head>
<body>
<div class="container">
	<%@ include file="header.jsp" %>
	<div class="content">
		<div class="files">
		
			<div class="gistOptions">
				<div class="OptionsContainer">
					<input type="hidden" class="gistId" value="${gist.getId()}"/>
					<img src="${gist.getOwner().getAvatarUrl()}"/>
					<p class="gistName">${gist.getOwner().getLogin()} / 
							${gist.getFiles().get(0).getFilename()}</p>
						
					<c:choose>
						<c:when test="${gist.isOwnedByCurrentUser()}">
							<input type="button" id="deleteGist" value="Poista"/>
							<input type="button" id="editGist" value="Muokkaa"/>
						</c:when>
						<c:otherwise>
							<input type="button" id="forkGist" value="Fork"/>
						</c:otherwise>
					</c:choose>
					<br><p>${gist.getDescription()}</p>
				</div>		
			</div><br>
			
			<c:forEach items="${gist.getFiles()}" var="file" varStatus="fileIndex">
				<c:set var="fileNum" value="${'gistFile'}${fileIndex.index}"/>
				<c:set var="editorNum" value="${'editor'}${fileIndex.index}"/>
				
				<div class="${fileNum}">
					<div class="fileInfo">
						<p>${file.getFilename()}</p> 
					</div>
					
					<div id="${editorNum}">
						<p id="content">${file.getContent()}</p>	
					</div>
				</div>
				
			
			</c:forEach>
		</div>
		
	</div>
</div>



</body>
</html>