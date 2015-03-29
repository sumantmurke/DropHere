
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>User Page</title>




<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">



<link href="bootstrap/bootstrap.min.css" rel="stylesheet" media="screen"
	type="text/css">
<link href="bootstrap/bootstrap.css" rel="stylesheet" media="screen"
	type="text/css">
<link href="bootstrap/bootstrap-responsive.css" rel="stylesheet"
	media="screen" type="text/css">
<link href="bootstrap/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen" type="text/css">
<script type="text/javascript" src="https://www.dropbox.com/static/api/1/dropins.js" id="dropboxjs" data-app-key="omk40ywjmprdifx"></script>
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



<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) {
	/* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}
</style>

<script type="text/javascript">
function modal() {

	$("#myModal").modal('show');

}

function Files(file){
	
	//alert("i am inside download"+file);
	var uri = "rest/file/download/"+file;
	$.ajax({
		url : uri,
	    type: "GET",
	    datatype : "json",
	   
	    success:function(data, textStatus, jqXHR){
	    	alert('success');
	    	//alert(data);
	    	var link = data;
	    	window.open(link);
	    	window.location.href="userPage.jsp";
	    	
	    },
	    error: function(jqXHR, textStatus, errorThrown){
	    	alert('Could not process request.. ' + errorThrown);
	    }
	});
	
}

var testfile;
function shareFiles(file){
	//alert('name of the file is '+file);
	testfile = file;
	
	$("#shareModal").modal('show');
}
function submitsharedata(){
	//alert("name of the test file is "+testfile);
	var filetobeshared = testfile;
	var shareemail=  $('#shareemail').val();
	
	//alert("file shared with "+shareemail+" is "+filetobeshared);
	
	$.ajax({
		url : "rest/file/sharelink",
	    type: "POST",
	    data : "shareemail=" + shareemail + "&filename=" + filetobeshared,
	   
	    success:function(data, textStatus, jqXHR){
	    	alert('success');
	    	window.location.href="userPage.jsp";
	    },
	    error: function(jqXHR, textStatus, errorThrown){
	    	alert('Could not process request.. ' + errorThrown);
	    }
	});
	
	
}


function EmailVerify() {
    var email = document.getElementById('email').value;
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var message = document.getElementById('emailMessage');
    var badColor = "red";
    message.style.color = badColor;

    if (!filter.test(email)) {   
       $('#emailMessage').html("Please Enter valid Email");
       return false;
    }else{
       $('#emailMessage').html("");
       $.get('/check_email?email=' + email, function(data){
            if(data == "true")
                $('#emailMessage').html("Email already exists");
       });
    }
    return true;
}

function Refresh(){
	var uri = "rest/file/refresh";
	$.ajax({
		url : uri,
	    type: "GET",
	    datatype : "json",	     
	    success:function(data, textStatus, jqXHR){
		window.location.href="userPage.jsp";
	},
	error: function(jqXHR, textStatus, errorThrown){
		alert('Could not process request.. ' + errorThrown);
	}
	});
	}


function logout(){
	
	//alert("i am inside logut");

	$.ajax({
		url : "rest/file/logout",
	    type: "GET",
	    datatype : "text",
	   
	    success:function(data, textStatus, jqXHR){
	    	alert('successfully logout');
	    	window.location.href="HomePage.html";
	    },
	    error: function(jqXHR, textStatus, errorThrown){
	    	alert('Could not process request.. ' + errorThrown);
	    }
	});
	
}


function DeleteFiles(file){
	
	confirm('Are you sure you want to delete the file?');
	var uri = "rest/file/delete/"+file;
	$.ajax({
		url : uri,
	    type: "DELETE",
	    datatype : "json",
	   
	    success:function(data, textStatus, jqXHR){
	    	var uri = "rest/file/refresh";
	    	$.ajax({
	    		url : uri,
	    	    type: "GET",
	    	    datatype : "json",	     
	    	    success:function(data, textStatus, jqXHR){
	    		window.location.href="userPage.jsp";
	    	    }
	    	});
	    	    
	    },
	    
	    error: function(jqXHR, textStatus, errorThrown){
	    	//alert('Could not process request.. ' + errorThrown);
	    }
	});
	
}


</script>

<!-- Dropbox script -->
<script type="text/javascript">
  
  $(document).ready(function() {
	  var  dbChooser = $("#db-chooser");
	  
 	 dbChooser.on("DbxChooserSuccess", function(e) { 
		 //alert("Hello");
	 	 //alert("Here's the chosen file: " + e.originalEvent.files[0].link);
	 	$(dblink).append(" " + e.originalEvent.files[0].link);
	 	$(dblink).attr("href", e.originalEvent.files[0].link);
 	
	 		$("#myModal3").modal('show');
	 		
	 	

	});
  
  });
</script>

</head>

<body>




	<%@include file="layout/header.jsp"%>



	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3">
				<div class="well sidebar-nav">

					<h4>Space Used</h4>
					<div class="progress progress-info progress-striped">
						<div class="bar" style="width: ${filePercentage}%"></div>
					</div>
					<button class="btn btn-primary" type="button" onclick ="logout();" >Logout</button>
					<button class="btn btn-primary" type="button" onclick="window.location.href='profile.jsp'" >Profile &raquo;</button>
					
				</div>
				<div class="span3">
				<div class="well sidebar-nav" style="width:266px; margin-left:-9px">
					<input type="dropbox-chooser" name="selected-file" id="db-chooser" data-link-type="direct" />
				<!-- 	
					<a href="" id="dblink"></a>
				 -->
				</div>
			</div>
				<!--/.well -->
			</div>
			
			<!--/span-->
			<div class="span9">
				<div class="well well-large">
					<!--  class="hero-unit"-->

					<div class="row">

						<!-- <form class="navbar-search pull-left">
							<input type="text" class="search-query" placeholder="Search"
								style="margin-left: 20px"></form>
							<!-- change done on May 6 1:36pm -->
						 
						
						

						<button class="btn btn-primary" type="button"
							style="margin-left: 447px" onclick="modal()">Upload File</button>

						<input type="image" id="myimage"
							src="images/Basic-Upload-2-icon.png" />

					</div>
					<div></div>
				</div>

				<table id="example" class="table table-hover">
					<thead>
						<tr>
							<th>File Name</th>
							<th>File Size</th>
							<th>Date Modified</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="item" items="${fileDetails}">
						<tr>
							<td>${item.getFileName()}</td>
							
							<td>${item.getSize()} KB</td>
							
							<td>${item.getDateCreated()}</td>
						
							<td onclick="Files('${item.getFileName()}');"><button
									class="btn btn-primary" type="button">Download</button></td>
							<td onclick="shareFiles('${item.getFileName()}');"><button
									class="btn btn-success" type="button">Share</button></td>
							<td onclick="DeleteFiles('${item.getFileName()}');"><button
									class="btn btn-danger" type="button">Delete</button></td>
						</tr>
						</c:forEach>
					</tbody>
					 
				</table>
				<button class="btn btn-primary" type="button" onClick="Refresh();">Refresh</button> 


			</div>
			<!--/span-->
		</div>
		<!--/row-->

			
	</div>
	<!-- Modal for upload-->

	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Upload File</h4>
				</div>
				<div class="modal-body">

					<form action="rest/file/upload" method="post"
						enctype="multipart/form-data">

						<p>
							Select a file : <input type="file" name="file" size="45" />
						</p>

						<input type="submit" value="Upload It" />
					</form>
				</div>
				<div class="modal-footer"></div>
			</div>


		</div>

	</div>


	<!-- Modal for share file -->

	<div id="shareModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Share your file with</h4>
				</div>
				<div class="modal-body">

					<form action="rest/file/upload" method="post"
						enctype="multipart/form-data">
						<input type="email" class="form-control" id="shareemail"
							placeholder="Your registered email" onChange="EmailVerify();">


						
					</form>
				</div>
				<div class="modal-footer">
				
				<input type="submit" value="Submit" onclick="submitsharedata()" />
				</div>
			</div>


		</div>

	</div>

<!-- Modal for dropbox -->


	<div id="myModal3" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Download your DropBox file here ..! </h4>
				</div>
				<div class="modal-body">

						<a href="" id="dblink"></a>
				</div>
				<div class="modal-footer"></div>
			</div>


		</div>

	</div>




	<%@include file="layout/footer.jsp"%>
</body>
</html>