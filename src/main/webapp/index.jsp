
<html>
<body>
<%@include file="layout/header.jsp"%>
	<h1>Upload File</h1>
 
	<form action="rest/file/upload" method="post" enctype="multipart/form-data">
 
	   <p>
		Select a file : <input type="file" name="file" size="45" />
	   </p>
 
	   <input type="submit" value="Upload It" />
	</form>
 <%@include file="layout/footer.jsp"%>
</body>
</html>