<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<html>
<head>
<!-- Material Design fonts -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://code.getmdl.io/1.2.1/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
</head>

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


	function submitForm(url, user) {
		console.log("This is the URL"+url);

		$
				.ajax({
					type : "POST",
					url : url,
					data : $("#createUserForm").serialize(),
					success : function(data) {
					},
					error : function(error) {
						console.log(error);
						errorMsg = JSON.parse(error.responseText);
					},
					statusCode : {
						200 : function() {
							window.location = user;
						},
						400 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();

						},
						500 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();
						},
						404 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();
						},
						409 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();
						}
					},
					complete : function(e) {
						if (e.status == 200) {

						}
						if (e.status == 400) {
							var errorMessage = JSON.parse(e.responseText);
						}
					}
				});
	}
	function librarianLogin(url, user) {
		console.log("This is the URL"+url);
		$
				.ajax({
					type : "POST",
					url : url,
					data : $("#librarianLoginForm").serialize(),
					success : function(data) {
					},
					error : function(error) {
						console.log(error);
						errorMsg = JSON.parse(error.responseText);
					},
					statusCode : {
						200 : function() {
							window.location = user;
						},
						400 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();

						},
						500 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();
						},
						404 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();
						},
						403 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
						},
						401 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();
							var sjsuEmailValue ="SJSU.EDU";
							var emailValue = document.getElementById("loginEmail").value.slice(-8);
							if(emailValue.toUpperCase() == sjsuEmailValue)
								window.location = "/librarian/verifyuser";
							else
								window.location = "/patron/verifyuser";
							

						},
						409 : function() {
							document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();
						}
					},
					complete : function(e) {
						if (e.status == 200) {

						}
					}
				});
	}
	
	function validateMyFields(){
		
		var firstName = document.getElementById("firstName").value;
		var lastName = document.getElementById("lastName").value;
		var email = document.getElementById("signupEmail").value;
		var password = document.getElementById("password").value;
		var sjsuId = document.getElementById("id").value;	
		
		if(firstName == "" || lastName == "" || email == "" || password == "" || sjsuId == ""){
			alert("Enter all fields");
			return 0;
		}
		return 1;
	}
	
	
	function validateLoginFields(){
		
		var email = document.getElementById("loginEmail").value;
		var password = document.getElementById("password").value;
		
		if(email == "" || password == ""){
			alert("Enter all fields");
			return 0;
		}
		return 1;
	}
	

	$(document).ready(function() {
		var sjsuEmailValue ="SJSU.EDU";
		var errorMsg;

		$("#createUserForm").submit(function(e) {
			e.preventDefault();
			
			var result =  parseInt(validateMyFields());			
			if(result == 1){
				
				console.log("aaa");
				var emailValue = document.getElementById("signupEmail").value.slice(-8);
				if(emailValue.toUpperCase() == sjsuEmailValue){
					var url1 = "/librarian/signup";
					//var user1 ="/librarian/home";
					var user1 ="/librarian/verifyuser";
					document.getElementById("signupEmail").name="sjsuEmail";
	
				}else{
				var url1 = "/patron/signup";
					document.getElementById("signupEmail").name="email";
					var user1 ="/patron/verifyuser";
	
				}
				submitForm(url1, user1);
			}
		});

		$("#librarianLoginForm").submit(function(e) {
			e.preventDefault();
			
			
				var emailValue2 = document.getElementById("loginEmail").value.slice(-8);
				if(emailValue2.toUpperCase() == sjsuEmailValue){
					var url = "/librarian/signin";
					var user ="/librarian/home";
					document.getElementById("signupEmail").name="sjsuEmail";
				}else{
				var url = "/patron/signin";
				document.getElementById("signupEmail").name="email";
				var user ="/patron/home";
	
				}
				librarianLogin(url, user);
			
		});

		$("#buttonClick").click(function(e) {
			searchGroups();
		});
	});
</script>
<style>
body {
	
}

#verticle-line {
	margin-left: 650px;
	width: 1px;
	margin-top: -325px;
	min-height: 330px;
	background: black;
}

#or {
	position: absolute;
	margin-left: 630px;
	margin-top: -420px;
}

#secondform {
	margin-left: 830px;
	margin-top: -590px;
}

#firstform {
	margin-left: 80px;
}

.mdl-layout__content {
	padding: 24px;
	flex: none;
}

form {
	margin-bottom: 30px;
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

/* Show the snackbar when clicking on a button (class added with JavaScript) */
#snackbar.show {
	visibility: visible; /* Show the snackbar */
	/* Add animation: Take 0.5 seconds to fade in and out the snackbar. 
However, delay the fade out process for 2.5 seconds */
	-webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
	animation: fadein 0.5s, fadeout 0.5s 2.5s;
}

/* Animations to fade the snackbar in and out */
@
-webkit-keyframes fadein {
	from {bottom: 0;
	opacity: 0;
}

to {
	bottom: 30px;
	opacity: 1;
}

}
@
keyframes fadein {
	from {bottom: 0;
	opacity: 0;
}

to {
	bottom: 30px;
	opacity: 1;
}

}
@
-webkit-keyframes fadeout {
	from {bottom: 30px;
	opacity: 1;
}

to {
	bottom: 0;
	opacity: 0;
}

}
@
keyframes fadeout {
	from {bottom: 30px;
	opacity: 1;
}

to {
	bottom: 0;
	opacity: 0;
}
}
</style>
<body>
	<h1 style="text-align:center;">Library Management System</h1>
	<form id="createUserForm">
		<div class="mdl-layout mdl-js-layout mdl-color--grey-100">

			<div id="firstform">
				<h3 style="margin-left: 30px;">Create an Account</h3>
				<main class="mdl-layout__content">
				<div class="mdl-card mdl-shadow--6dp">
					<div
						class="mdl-card__title mdl-color--primary mdl-color-text--white">
						<h2 class="mdl-card__title-text" >Sign Up</h2>
					</div>


					<div class="mdl-card__supporting-text">
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="firstName"
								name="firstName"> <label class="mdl-textfield__label"
								for="firstname" requird>First Name</label>
						</div>
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="lastName"
								name="lastName"> <label class="mdl-textfield__label"
								for="lastname">Last Name</label>

						</div>
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="email" id="signupEmail"
								name="sjsuEmail"> <label class="mdl-textfield__label"
								for="email">Username</label> <span class="mdl-textfield__error">Not
								a Valid Email</span>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="password" id="password"
								name="password" pattern="((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{6,20})"> <label class="mdl-textfield__label"
								for="password">Password</label><span class="mdl-textfield__error">Password must contain
								Uppercase,lowercase,special character & a Number. Length between
								6 and 20</span>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text"
								pattern="^[0-9]{6}$" id="id" name="sjsuId"> <label
								class="mdl-textfield__label" for="id">SJSU ID</label> <span
								class="mdl-textfield__error">Enter SJSU ID of 6 digits</span>
						</div>

						<button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" style="width:300px;">
							Create</button>
	</form>
	</div>
	</div>
	</main>
	</div>

	<div id="secondform">
		<h3>Login to Access</h3>
		<main class="mdl-layout__content" id="second"
			style="float:right;margin-right:90px; margin-top:30px;">
		<div class="mdl-card mdl-shadow--6dp">
			<div class="mdl-card__title mdl-color--primary mdl-color-text--white">
				<h2 class="mdl-card__title-text">Sign In</h2>
			</div>
			<form id="librarianLoginForm">
				<div class="mdl-card__supporting-text">
					<div
						class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						<input class="mdl-textfield__input" type="email" id="loginEmail"
							name="email"> <label class="mdl-textfield__label"
							for="email">Username</label> <span class="mdl-textfield__error">Not
							a Valid SJSU Email</span>
					</div>

					<div
						class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						<input class="mdl-textfield__input" type="password" id="password"
							name="password" pattern="((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{6,20})" > <label class="mdl-textfield__label"
							for="password">Password</label><span class="mdl-textfield__error">Password must contain
								Uppercase,lowercase,special character & a Number. Length between
								6 and 20</span>
					</div>



					<button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" style="width:300px;">
						Log On</button>
			</form>
		</div>
	</div>
	</main>


	</div>
	<div id="verticle-line"></div>
	<div id="or">
		<h3>Or</h3>
	</div>
	<div id="snackbar">Some text some message..</div>

</body>
</html>