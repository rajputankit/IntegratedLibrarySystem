"<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<meta charset="utf-8" />
<!-- Material Design fonts -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://code.getmdl.io/1.2.1/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.js"></script>
</head>
<style>
#updateform {
	margin-left: 20px;
}

#addkeyword {
	position: absolute;
	margin-top: 620px;
	margin-left: 270px;
}
</style>
<body>
<script type="text/javascript">

$(document).ready(function() {
	
	$("#updateBookForm").submit(function(e) {
		e.preventDefault();
		submitUpdateForm();
	});
});


function submitUpdateForm(){
	
	$.ajax({
		type : "POST",
		url : "/book/update",
		data : $("#updateBookForm").serialize(),
		success : function(data) {
		},
		error : function(error) {
			console.log(error);
			errorMsg = JSON.parse(error.responseText);
		},
		statusCode : {
			200 : function() {
				//window.location = """;
			}
		},
		complete : function(e) {
			console.log(e.status);	
			//open(location, '_self').close();
		}
	});
}




</script>


	<main class="mdl-layout__content">
	<section class="mdl-layout__tab-panel is-active" id="scroll-tab-1">
		<div class="page-content">
			<!--  TAB 1 ADD ............................ -->
			<main class="mdl-layout__content" id="updateform">
			<div class="mdl-card mdl-shadow--6dp">
				<form id="updateBookForm">
					<div class="mdl-card__supporting-text">

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="isbn"
								value="${book.ISBN}" name="ISBN"> <label
								class="mdl-textfield__label" for="isbn">ISBN</label>
								<input class="mdl-textfield__input" type="text" id="bookId"
								value="${book.bookId}" name="bookId" hidden>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="author"
								value="${book.author}" name="author"> <label
								class="mdl-textfield__label" for="email">Author</label>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="title"
								value="${book.title}" name="title"> <label
								class="mdl-textfield__label" for="password">Title</label>
						</div>


						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="callnumber"
								value="${book.callNumber}" name="callNumber"> <label
								class="mdl-textfield__label" for="id">Call Number</label>

						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="publisher"
								value="${book.publisher}" name="publisher"> <label
								class="mdl-textfield__label" for="email">Publisher</label>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="year"
								pattern="^[0-9]{4}$" value="${book.yearOfPublication}" name="yearOfPublication">
							<label class="mdl-textfield__label" for="year">Year of
								Publication</label> <span class="mdl-textfield__error">Enter a
								valid Year</span>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="Location"
								value="${book.location}" name="location"> <label
								class="mdl-textfield__label" for="email">Location</label>
						</div>

						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="copies"
								value="${book.numberOfCopies}" name="numberOfCopies"> <label
								class="mdl-textfield__label" for="email">Number of
								Copies</label>
						</div>
						<%-- <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
    <input class="mdl-textfield__input" type="text" id="keyword" name="keywordarray[]" value="${book.keywordArray}">
    <label class="mdl-textfield__label" for="email">Keyword</label>
  </div> --%>
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<c:forEach items="${book.keywordArray}" var="keyword" varStatus="loop">
								<input class="mdl-textfield__input" type="text" id="keyword"
									name="keywordArray[${loop.index}]" value="${keyword}">
							</c:forEach>
							<label class="mdl-textfield__label" for="email">Keyword</label>
						</div>

					</div>

					<button class="mdl-button mdl-js-button mdl-js-ripple-effect"
						type="submit" style="width: 330px;" id="submitupdate">
						Update!</button>


				</form>
				<button
					class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored"
					id="addkeyword">
					<i class="material-icons">add</i>

				</button>
				<script>

$('#submitupdate').click(function(){
  console.log("trying to close");
  

})
  </script>
			</div>
			</main>
</body>
</html>
