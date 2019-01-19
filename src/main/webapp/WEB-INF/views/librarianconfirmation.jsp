<html>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
<head>
  <!-- Material Design fonts -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
</head>
<style>
#snackbar.show {
	visibility: visible; /* Show the snackbar */
	/* Add animation: Take 0.5 seconds to fade in and out the snackbar.
However, delay the fade out process for 2.5 seconds */
	-webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
	animation: fadein 0.5s, fadeout 0.5s 2.5s;
}
#snackbar {
	visibility: hidden; /* Hidden by default. Visible on click */
	min-width: 250px; /* Set a default minimum width */
	margin-left: -125px; /* Divide value of min-width by 2 */
	background-color: #333; /* Black background color */
	color: #fff; /* White text color */
	text-align: center; /* Centered text */
	border-radius: 2px; /* Rounded borders */
	padding: 16px; /* Padding */
	position: fixed; /* Sit on top of the screen */
	z-index: 1; /* Add a z-index if needed */
	left: 50%; /* Center the snackbar */
	bottom: 30px; /* 30px from the bottom */
}
</style>
<script>
function showSnackBar() {
	// Get the snackbar DIV
	var x = document.getElementById("snackbar");

	// Add the "show" class to DIV
	x.className = "show";

	// After 3 seconds, remove the show class from DIV
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);
}

 window.showError=function(errorMessage){
	document.getElementById('snackbar').innerHTML = errorMessage;
	showSnackBar();
}
function submitForm(url, user) {	
	$.ajax({
		type : "POST",
		url : "/librarian/verifyuser",	
		data: $("#userVerificationForm").serialize(),
		success : function(data) {
		},
		error:function(error){
			window.errorMsg = JSON.parse(error.responseText);

			showError(errorMsg.errorMessage);
			console.log(error);
		},
		statusCode : {
			200 : function() {
				
			},
			400 : function() {
				
			},
			500 : function() {
				
			},
			404 : function() {
				
			},
			409 : function() {
			}
		},
		complete : function(e) {
			if (e.status == 200) {
				//alert("User Verified. Please SignIn");
				window.location = '/signup';				
			}
			if (e.status == 400) {
				var errorMessage = JSON.parse(e.responseText);
				//alert(errorMessage.errorMessage);				
			}
		}
	});
}


$(document).ready(function() {
	
	$("#userVerificationForm").submit(function(e) {
		e.preventDefault();
		submitForm();
	});
	
	$("#buttonClick").click(function(e) {
		searchGroups();
	});
});
</script>
<body>

<form   id="userVerificationForm" style="margin-left:450px;margin-top:200px;">

<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
<input class="mdl-textfield__input" type="text" name ="code" pattern="^[0-9]{5}$" id="code">
<label class="mdl-textfield__label" for="id">Confirmation Code</label>
  <span class="mdl-textfield__error">Enter Valid Confirmation Code</span>
</div>

<button class="mdl-button mdl-js-button mdl-js-ripple-effect" type="submit">
Verify
</button>


</form>
	<div id="snackbar">Some text some message..</div>

</body>
</html>
