<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="header">
	<!--<div class="logo"><img src="../logo.png"/></div>-->
	
	<c:choose>
	    <c:when test="${sessionScope.loggedin != null}">
	     	<div id="userInfo">
		        <img src="${sessionScope.avatar}"/>
				<p>${sessionScope.login}</p>
			</div>
			<ul id="navmenu">
				<li><a href="http://localhost:8080/Opinnaytetyo_spring/gists">Listaa gistit</a></li>
				<li><a href="http://localhost:8080/Opinnaytetyo_spring/creategist">Luo uusi gist</a></li>
				<li><a href="http://localhost:8080/Opinnaytetyo_spring/logout">Logout</a></li>
			</ul>
	    </c:when>    
	    <c:otherwise>
	    	<ul id="navmenu">
				<li><a href="http://localhost:8080/Opinnaytetyo_spring/gists">Listaa gistit</a></li>
				<li><a href="http://localhost:8080/Opinnaytetyo_spring/creategist">Luo uusi Gist</a></li>
				<li><a href="http://localhost:8080/Opinnaytetyo_spring/login">Login</a></li>
			</ul>	
	    </c:otherwise>
	</c:choose>

</div>
