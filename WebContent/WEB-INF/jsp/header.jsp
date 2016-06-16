<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="header">
	<div class="headerContent">
		<!--<div class="logo"><img src="../logo.png"/></div>-->
		
		<c:choose>
		    <c:when test="${sessionScope.loggedin != null}">
		     	<div class="userInfo">
			        <img src="${sessionScope.avatar}"/>
					<p>${sessionScope.login}</p>
				</div>
				
				<div class="search">
					<form action="gists" method="get">
						<input type="text" id="searchField" name="search"/>
						<input type="submit" id="doSearch" value="Hae">
					</form>
				</div>
				
				<ul class="navmenu">
					<li><a href="gists">Listaa gistit</a></li>
					<li><a href="creategist">Luo uusi gist</a></li>
					<li><a href="logout">Logout</a></li>
				</ul>
		    </c:when>    
		    <c:otherwise>
		    	<ul class="navmenu">
					<li><a href="gists">Listaa gistit</a></li>
					<li><a href="creategist">Luo uusi Gist</a></li>
					<li><a href="login">Login</a></li>
				</ul>	
		    </c:otherwise>
		</c:choose>
	</div>

</div>
