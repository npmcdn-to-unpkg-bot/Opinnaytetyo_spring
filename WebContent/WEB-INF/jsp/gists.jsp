<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="tatuputto.opinnaytetyo.domain.Gist" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- <link href="styles.css " rel="stylesheet" type="text/css" />-->

<link href="http://localhost:8080/Opinnaytetyo_spring/css/Header.css" rel="stylesheet" type="text/css"/>
<link href="http://localhost:8080/Opinnaytetyo_spring/css/Gists.css" rel="stylesheet" type="text/css"/>
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript"></script>
<script src="http://localhost:8080/Opinnaytetyo_spring/js/ShowGistFiles.js" type="text/javascript"></script>


<!-- React ja React router -->
<script src="https://fb.me/react-15.1.0.js"></script>
<script src="https://fb.me/react-dom-15.1.0.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
<script src="https://npmcdn.com/react-router@2.4.0/umd/ReactRouter.min.js"></script>
<!-- /React -->



<title>Gists</title>
</head>
<body>
<div class="container">
	<%@ include file="header.jsp" %>
	<div class="content">
		<!-- 
		<div class="filters">
			<input type="button" id="addFilters" value="Lisää suodattimia"/>
			
			<div class="filteringOptions">
				<input type="button" id="getUsersGists" value="Omat gistit"/>
				<input type="button" id="getAllPublicGists" value="Kaikki gistit"/>
			</div>
		</div>
		-->
		<div>
			<input type="button" id="getUsersGists" value="Omat gistit"/>
			<input type="button" id="getAllPublicGists" value="Kaikki gistit"/>
		</div> 
		
		<!-- Listataan gistit -->
		<div class="contentLeft">
			<div class="listGists">
				<c:forEach items="${model.gistList}" var="gist">
					<div class="singleGist" id="${gist.getId()}">
						<div class="singleGistContainer">
							<p class="gistOwner">${gist.getOwner().getLogin()} / 
									<a href="${gist.getUrl()}">${gist.getFiles().get(0).getFilename()}</a></p>
							<p class="description"><c:out value="${gist.getDescription()}"/></p>
						</div>
		  			</div>
		  		</c:forEach>
		  		
		  		<c:if test="${model.fetchMethod.equals('all')}">
		  				<input type="button" id="loadMore" value="Lataa lisää"/>
		  		</c:if>
		  		
  			</div>
  		</div>
  		
  		
  		<!-- Näytetään valittu gist -->
  		<div class="contentRight">
			<!-- Yksittäisen gistin tiedostot -->
			<div class="singleGistFiles">
				<!-- Gistin tiedot, nimi, kuvaus, tekijä jne. -->
				<div class="gistInfo">
					<img class="ownerAvatar" src=""><a id="toGist" href=""></a> 
				
					<c:choose>
					    <c:when test="${model.fetchMethod.equals('user')}">
					        <input type="button" id="deleteGist" value="Poista"/>
							<input type="button" id="editGist" value="Muokkaa"/> 
					    </c:when>    
					    <c:otherwise>
					        <input type="button" id="forkGist" value="Fork"/> 
					    </c:otherwise>
					</c:choose>
				</div>
				
				<!-- Yksittäinen tiedosto -->
				<div class="gistFirstFile">
					<div class="fileInfo">
						<!-- <input type="text" class="filename" value=""></input>-->
						<a class="filename" href=""/></a>
					</div>
					<!-- div-elementti, joka muutetaan ace-editoriksi -->
					<div id="editor1"></div>
				</div>
			</div>
			<div class="loading"></div>
		</div>
	</div>
</div>
</body>
</html>