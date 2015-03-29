<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">



<link href="bootstrap/bootstrap.min.css" rel="stylesheet" media="screen"
	type="text/css">
<link href="bootstrap/bootstrap.css" rel="stylesheet" media="screen"
	type="text/css">
<link href="bootstrap/bootstrap-responsive.css" rel="stylesheet"
	media="screen" type="text/css">
<link href="bootstrap/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen" type="text/css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src="jsbootstrap/bootstrap.js" /></script>
<script type="text/javascript" src="jsbootstrap/bootstrap.min.js" /></script>
<script type="text/javascript" src="jsbootstrap/bootstrap-dropdown.js" /></script>
<script type="text/javascript" src="jsbootstrap/bootstrap-alert.js" /></script>
<script type="text/javascript" src="jsbootstrap/bootstrap-button.js" /></script>
<script type="text/javascript" src="jsbootstrap/bootstrap-carousel.js" /></script>
<script type="text/javascript" src="jsbootstrap/bootstrap-scrollspy.js" /></script>
<script type="text/javascript" src="jsbootstrap/bootstrap-modal.js" /></script>





</head>
<body>




	
<div id="header">
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div id="headerNav" class="navbar-inner">
			<a class="brand" href="HomePage.html"><span style="color: white; margin: 0px 10px 0px 10px;">AirBox</span></a>
			<% if(session.getAttribute("username") != null) { %>
			<ul class="nav">
				<li><a href="#" style="color: white;"></a></li>
			</ul>
			<% } %>
			<% if(session.getAttribute("username") == null) { %>
			<ul id="loginMenu" class="nav" style=" float:right;">
				<li><a href="login.jsp" style="color: white;">Login</a></li>
				<li><a href="signup.jsp" style="color: white;">Sign Up</a></li>
			</ul>
			<% } else { %>
		<!-- 
			<ul id="loggedInMenu" class="nav" style=" float:right; padding-right: 35px;">
			
				<li class="dropdown" id="loggedInDown">
				 -->	<%--
					<c:choose>
					    <c:when test="${user.isTester}">
					    	<a class="dropdown-toggle" data-toggle="dropdown" href="#loggedInDown" style="color: white;">Hello ${user.firstName} <span class="caret"></span></a>
					    </c:when>
	 					<c:otherwise>--%>
	 					
	 					
	 						<a class="dropdown-toggle" data-toggle="dropdown" href="#loggedInDown" style="color: white;float:right; margin-top:11px" onclick="window.location.href='profile.jsp'">Hello ${usersfirstname} ! <span class="caret"></span></a>
	 			
	 			
	 			
	 			
	 				<%--	</c:otherwise>
 					</c:choose>
					--%>
					<!-- 
					<ul class="dropdown-menu">
				      <li><a href="/project-vars/showProfile.htm">Profile</a></li>
				      <li><a href="/project-vars/logout.htm">Logout</a></li>
				    </ul>

			    </li>
			</ul>
							     -->
			<% } %>
		</div>
	</div>
</div>

</body>
</html>