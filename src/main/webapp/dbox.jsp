
<html>
<head>
<script type="text/javascript" src="https://www.dropbox.com/static/api/1/dropins.js" id="dropboxjs" data-app-key="omk40ywjmprdifx"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<!-- Replace data-app-key with yours -->   
   
<script type="text/javascript">
  
  $(document).ready(function() {
	  var  dbChooser = $("#db-chooser");
	  
 	 dbChooser.on("DbxChooserSuccess", function(e) { 
		 alert("Hello");
	 	 alert("Here's the chosen file: " + e.originalEvent.files[0].link);
	 	$(textarea).append(" " + e.originalEvent.files[0].link);
	 	$(dblink).append(" " + e.originalEvent.files[0].link);
	 	$(dblink).attr("href", e.originalEvent.files[0].link);

	});
  
  });
</script>

</head>

<body>

	<input type="dropbox-chooser" name="selected-file" id="db-chooser" data-link-type="direct" />
	<textarea name="textarea" id="textarea"></textarea>
	
	<a href="" id="dblink"></a>
</body>
</html>