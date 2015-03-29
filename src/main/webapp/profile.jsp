<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script  src="Chart.js" /></script>
<style>
			canvas{
			}
		</style>


<title>User Profile</title>
 <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }

      @media (max-width: 980px) {
        /* Enable use of floated navbar text */
        .navbar-text.pull-right {
          float: none;
          padding-left: 5px;
          padding-right: 5px;
        }
      }
    </style>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<link href="bootstrap/bootstrap.min.css" rel="stylesheet" media="screen"
	type="text/css">
<link href="bootstrap/bootstrap.css" rel="stylesheet" media="screen"
	type="text/css">
<link href="bootstrap/bootstrap-responsive.css" rel="stylesheet"
	media="screen" type="text/css">
<link href="bootstrap/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen" type="text/css">
	
	
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

	<%@include file="layout/header.jsp"%>


   
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
           
           <table>
           <tbody>
           
           <tr>
           <td>First Name </td>
           <td>: ${usersfirstname}</td>
           </tr>
           <tr>
           <td>Last Name </td>
           <td>: ${userslastname}</td>
           </tr>
           <tr>
           <td>Email Id </td>
           <td>: ${username}</td>
           </tr>
           
           </tbody>
           
           
           </table>
           
              <canvas id="canvas" height="145" width="145"></canvas>
           
          </div><!--/.well -->
        </div><!--/span-->
        <div class="span9">
         
         
          <div class="row-fluid">
         
            <table id="example" class="table table-hover">
					<thead>
						<tr>
							<th>Shared With</th>
							<th>File Name</th>
							<th>Date Modified</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="item" items="${history}">
						<tr>
							<td>${item.getUsername()}</td>							
							<td>${item.getFileName()}</td>
						
							<td>${item.getDateCreated()}</td>
					
						</tr>
						</c:forEach>
					</tbody>
					 
				</table>
				
				
				<table id="ex" class="table table-hover">
				<thead>
				
				<tr>
				<th>Shared Name</th>
				<th>Shared Percentage</th>
				</tr>
				
				</thead>
				<tbody>
				<c:forEach var="share" items="${shareperc}">
				<tr id="sharerow">
				<td id="shareholdername">${share.getUsername()}</td>
				<td id="sharepercentage">${share.getSize()}%</td>
				</tr>
				</c:forEach>
				</tbody>
				</table>
							
					
					
					
				
            <button class="btn btn-primary" type="button"
							style="margin-left: 380px" onclick="window.location.href='userPage.jsp'">&laquo; Back to User page </button>
				</div><!--/row-->
        </div><!--/span-->
      </div><!--/row-->

 </div>

    

<script>

		var pieData = [
				{
					value: 14,
					color:"#F38630"
				},
				{
					value : 71,
					color : "#E0E4CC"
				},
				{
					value : 14,
					color : "#69D2E7"
				},
				{
					value : 0,
					color : "#63DE98"
				},
				{
					value : 0,
					color : "#9193A6"
				},
				{
					value : 0,
					color : "#424DAE"
				}
			
			];
/*
	var myPie = new Chart(document.getElementById("canvas").getContext("2d")).Pie(pieData);
*/
	</script>

	<%@include file="layout/footer.jsp"%>

</body>
</html>

















 