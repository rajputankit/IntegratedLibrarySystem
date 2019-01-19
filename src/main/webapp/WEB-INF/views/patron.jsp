<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<!-- Material Design fonts -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://code.getmdl.io/1.2.1/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
</head>
<style>
body {
	opacity: 0.8;
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
	margin-top: -460px;
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
@-webkit-keyframes fadein {
    from {bottom: 0; opacity: 0;} 
    to {bottom: 30px; opacity: 1;}
}

@keyframes fadein {
    from {bottom: 0; opacity: 0;}
    to {bottom: 30px; opacity: 1;}
}

@-webkit-keyframes fadeout {
    from {bottom: 30px; opacity: 1;} 
    to {bottom: 0; opacity: 0;}
}

@keyframes fadeout {
    from {bottom: 30px; opacity: 1;}
    to {bottom: 0; opacity: 0;}
}
</style>
<script>


function showSnackBar() {
    // Get the snackbar DIV
    var x = document.getElementById("snackbar")

    // Add the "show" class to DIV
    x.className = "show";

    // After 3 seconds, remove the show class from DIV
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

	function submitForm() {
		$
				.ajax({
					type : "POST",
					url : "/patron/signup",
					data : $("#createUserForm").serialize(),
					success : function(data) {
					},
					error : function(error) {
						console.log(error);
						errorMsg=JSON.parse(error.responseText);
					},
					statusCode : {
						200 : function() {
							window.location = 'http://localhost:8080/patron/verifyuser';
						},
						400 : function() {
						    document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
							showSnackBar();
							
						},
						401 : function() {
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
						if(e.status == 400){
							var errorMessage = JSON.parse(e.responseText);
						}
					}
				});
	}
	function userLogin() {
		$.ajax({
			type : "POST",
			url : "/patron/signin",
			data : $("#userLoginForm").serialize(),
			success : function(data) {},
			error : function(error) {
				console.log(error);
				errorMsg=JSON.parse(error.responseText);
			},
			statusCode : {
				200 : function() {
					window.location = 'http://localhost:8080/patron/home';
				},
				400 : function() {
				    document.getElementById('snackbar').innerHTML = errorMsg.errorMessage;
					showSnackBar();
					
				},
				401 : function() {
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
			}
		});
	}

	$(document).ready(function() {

		$("#createUserForm").submit(function(e) {
			e.preventDefault();
			submitForm();
		});

		$("#userLoginForm").submit(function(e) {
			e.preventDefault();
			userLogin();
		});

		$("#buttonClick").click(function(e) {
			searchGroups();
		});
	});
</script>
<body>
	<h1>Customer Creation Form</h1>

	<div class="mdl-layout mdl-js-layout mdl-color--grey-100">

		<div id="firstform">
			<h3 style="margin-left: 30px;">Create an Account</h3>
			<main class="mdl-layout__content">
			<div class="mdl-card mdl-shadow--6dp">
				<div
					class="mdl-card__title mdl-color--primary mdl-color-text--white">
					<h2 class="mdl-card__title-text">Patron</h2>
				</div>
				<div class="mdl-card__supporting-text">
					<!--   confirmation code controller -->
					<form id="createUserForm">
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="firstName"
								name="firstName"> <label class="mdl-textfield__label"
								for="firstname">First Name</label>
						</div>
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="lastName"
								name="lastName"> <label class="mdl-textfield__label"
								for="lastname">Last Name</label>

						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" name="email"
								id="email" pattern="^[a-zA-Z0-9._%+-]+@sjsu.edu$"> <label
								class="mdl-textfield__label" for="email">Username</label> <span
								class="mdl-textfield__error">Not a Valid SJSU Email</span>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="password"
								name="password" id="password"
								pattern="((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{6,20})">
							<label class="mdl-textfield__label" for="password">Password</label>
							<span class="mdl-textfield__error">Password must contain
								Uppercase,lowercase,special character & a Number. Length between
								6 and 20</span>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text"
								pattern="^[0-9]{6}$" name="sjsuId" id="sjsuId"> <label
								class="mdl-textfield__label" for="id">Student ID</label> <span
								class="mdl-textfield__error">Enter Librarian ID of 6
								digits</span>
						</div>

						<button class="mdl-button mdl-js-button mdl-js-ripple-effect"
							type="submit">Create</button>


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
				<div
					class="mdl-card__title mdl-color--primary mdl-color-text--white">
					<h2 class="mdl-card__title-text">Patron</h2>
				</div>
				<div class="mdl-card__supporting-text">
					<form id="userLoginForm">
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" name="email"
								id="email"> <label class="mdl-textfield__label"
								for="email">Username</label> <span class="mdl-textfield__error">Not
								a Valid Librarian Email</span>
						</div>
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="password"
								name="password" id="password"> <label
								class="mdl-textfield__label" for="password">Password</label>
						</div>
						<button class="mdl-button mdl-js-button mdl-js-ripple-effect">
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
