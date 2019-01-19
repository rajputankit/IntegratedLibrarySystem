<html>
<head>
    <meta charset="utf-8"/>
  <!-- Material Design fonts -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.indigo-pink.min.css">


<script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>

<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
         rel = "stylesheet">
      
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.css" />
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/moment-with-locales.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
      <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css" />
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css" />
      
      
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/js/bootstrap-datetimepicker.min.js"></script>
      
      
      
      
      
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.css"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.js"></script>
 
   
   
</head>
<style>
.mdl-layout__content{
	padding: 24px;
	flex: none;
}
#snackbar.show {
	visibility: visible; /* Show the snackbar */
	/* Add animation: Take 0.5 seconds to fade in and out the snackbar.
However, delay the fade out process for 2.5 seconds */
	-webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
	animation: fadein 0.5s, fadeout 0.5s 2.5s;
}
#addkeyword{
  position: absolute;
  margin-top: 10px;
  margin-left: 270px;
}
#library1{
  height:700px;
  margin-top: 30px;
}
#buttonsclass{
margin-top:20px;
margin-left:0px;
}
#searchform{
  margin-left: 500px;
  margin-bottom: 30px;
}
#logout{
position:absolute;
margin-left:1000px;
width:100px;
color:white;
}
#searchbutton{
  margin-left: 230px;

}
#mycart{
  margin-left: 450px;
  margin-bottom: 40px;
}
#mainform{
  margin-left: 390px;
}
#updatebook{
      margin-left: 390px;
}
#updatebutton{
  margin-top: 20px;
}
#chooselibrarian{
  height:300px;
  margin-left: 300px;
}
#library1{
  height:300px;
  margin-left: 300px;
}
#hi{
  margin-left: 90px;
}
#datepicker3{
  position: fixed;
  margin-left: 860px;
  border-radius: 5px;
  border-style: solid;
  margin-top: 10px;
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
<body>
 
  <script>
  window.keywordList=[];
  </script>
  <script>
 
  </script>
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
 function addBook() {	
		$.ajax({
			type : "POST",
			url : "/book/add",	
			data: $("#addBookForm").serialize(),
			success : function(data) {
			},
			error: function(error){
				window.errorMsg = JSON.parse(error.responseText);

    			showError(errorMsg.errorMessage);
				console.log(error);
			},
			statusCode : {
				200 : function() {
					window.location = '/librarian/home';
					
				},
				400 : function() {
					  $("#addbutton").attr("disabled", false);

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
															
				}
			}
		});
	}

 function searchForISBN(isbnNumber) {
		$
				.ajax({
					type : "GET",
					url : "/book/isbn?ISBN=" + isbnNumber,
					success : function(data) {
						$('#imgHolder').empty();
						if (data.totalItems == 1) {
							console.log(data.items[0].volumeInfo.title);
							document.getElementById("author").value = data.items[0].volumeInfo.authors[0];
							document.getElementById("title").value = data.items[0].volumeInfo.title;
							document.getElementById("publisher").value = data.items[0].volumeInfo.publisher;
							document.getElementById("year").value = data.items[0].volumeInfo.publishedDate.slice(0, 4);
							;
							console
									.log(data.items[0].volumeInfo.imageLinks.smallThumbnail);
							if (data.items[0].volumeInfo.imageLinks.smallThumbnail) {
								var img = $(
										'<img />',
										{
											src : data.items[0].volumeInfo.imageLinks.smallThumbnail
										});
								img.appendTo(document
										.getElementById('imgHolder'));
							}
						} else {
							console.log("No book found");
							document.getElementById("author").value = "";
							document.getElementById("title").value = "";
							document.getElementById("publisher").value = "";
							document.getElementById("year").value = "";
						}
					},
					error : function(error) {
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

						}
					}
				});
	}
	$(document).ready(function() {
		var systemdate;
		$.ajax({
			url:"/getdate",
			type:"GET",
			async:false,
			success:function(data){
				systemdate=new Date(parseInt(data.systemDate));
				console.log(systemdate);
				//console.log(returndate);
				//console.log(Date.UTC(systemdate.getFullYear(), systemdate.getMonth(), systemdate.getDate()));
				//a=returndate;
				newsystemdate=Date.UTC(systemdate.getFullYear(), systemdate.getMonth(), systemdate.getDate());
				console.log(newsystemdate);
				//console.log(((a-newsystemdate)/(1000 * 60 * 60 * 24))-1);
			},
			error:function(error){
				window.errorMsg = JSON.parse(error.responseText);

    			showError(errorMsg.errorMessage);
			}
		});
		$(function () {
            $('#datetimepicker4').datetimepicker({
            	 defaultDate:systemdate
            });
        });
		$("#addBookForm").submit(function(e) {
			e.preventDefault();
			//$("#addbutton").click(function(){
				// $(this).prop("disabled",true);
			//});
			//  $("#addbutton").attr("disabled", true);
			 // isbn=document.getElementById('isbn').value;
			  //console.log(isbn);
			  if(document.getElementById('isbn').value==''|| document.getElementById('author').value=='' || document.getElementById('title').value=='' || document.getElementById('callnumber').value=='' || document.getElementById('publisher').value=='' || document.getElementById('year').value=='' || document.getElementById('Location').value=='' || document.getElementById('copies').value=='' || document.getElementById('keyword').value==''){
				  alert("Please Enter all the fields in the form");
			  }else{
			 // alert(isbn);
			addBook();
			  }
		});
        $('#logout').click(function(e){
        	console.log("clicked button");
        	logOutLibrarian();	
        });
        function logOutLibrarian() {	
        	$.ajax({
        		type : "GET",
        		url : "/logout",	
        		success : function(data) {
        		},
        		error: function(error){
        			window.errorMsg = JSON.parse(error.responseText);

        			showError(errorMsg.errorMessage);
        			console.log(error);
        		},
        		statusCode : {
        			200 : function() {
        				window.location = '/signup';
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
        														
        			}
        		}
        	});
        }
		
	});

</script>
  <!-- Simple header with scrollable tabs. -->
       
  
  <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header">
      <div class="mdl-layout__header-row">
        <!-- Title -->
        
         <button id="logout"class="mdl-button mdl-js-button mdl-js-ripple-effect" onclick="logOutLibrarian();" style="width:200px;">
      Log Out
    </button>
        <span class="mdl-layout-title">SJSU Library</span>
      </div>
      <!-- Tabs -->
      <div class="mdl-layout__tab-bar mdl-js-ripple-effect">
        <a href="#scroll-tab-1" class="mdl-layout__tab is-active">ADD</a>

        <a href="#scroll-tab-2" class="mdl-layout__tab">Search</a>
        <a href="#scroll-tab-3" class="mdl-layout__tab">Search By Librarian</a>
        

      </div>
      
    </header>

    <main class="mdl-layout__content" style="height:800px;">
                    
    
      <section class="mdl-layout__tab-panel is-active" id="scroll-tab-1">
        <div class="page-content">
      

          <!--  TAB 1 ADD ............................ -->
             
          <!--<p>Enter Date: <input type = "text" id = "datepicker"></p> -->
          <div class="container">
    <div class="row">
         <button style="float:right;" id="datebutton"onclick="setDate();return false;"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Update Date</button> 
    
        <div class='col-sm-3' style="float:right;">
            <input type='text' class="form-control" id='datetimepicker4' />
            
        </div>
        
        <script type="text/javascript">
            
           
        </script>
    </div>
</div>
    
          <main class="mdl-layout__content" id="mainform">
    		<div class="mdl-card mdl-shadow--6dp">
    		<div id="imgHolder"></div>
   <form id="addBookForm">
						<div class="mdl-card__supporting-text">

							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="isbn"
									name="ISBN"> <label class="mdl-textfield__label"
									for="isbn">ISBN</label>
							</div>

							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="author"
									name="author" placeholder=""> <label
									class="mdl-textfield__label" for="email" id="authorLabel">Author</label>
							</div>

							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="title"
									name="title" placeholder=""> <label
									class="mdl-textfield__label" for="password">Title</label>
							</div>


							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="callnumber"
									name="callNumber" placeholder=""> <label
									class="mdl-textfield__label" for="id">Call Number</label>

							</div>

							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="publisher"
									name="publisher" placeholder=""> <label
									class="mdl-textfield__label" for="email">Publisher</label>
							</div>

							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="year"
									pattern="^[0-9]{4}$" maxlength="4" name="yearOfPublication"
									placeholder=""> <label class="mdl-textfield__label"
									for="year">Year of Publication</label> <span
									class="mdl-textfield__error">Enter a valid Year</span>
							</div>

							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="Location"
									name="location" placeholder=""> <label
									class="mdl-textfield__label" for="email">Location</label>
							</div>

							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="copies" pattern="^[0-9]*$"
									name="numberOfCopies" placeholder=""> <label
									class="mdl-textfield__label" for="email">Number of
									Copies</label><span
									class="mdl-textfield__error">Should be Integer value</span>
							</div>

							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="keyword"
									name="keywordArray" placeholder=""> <label
									class="mdl-textfield__label" for="email">Keywords
									(Comma separated)</label>
							</div>

							<input type="file" id="inputFile2" multiple="">Book Image</input>

						</div>

						<button class="mdl-button mdl-js-button mdl-js-ripple-effect" id="addbutton"
							type="submit"  style="width: 330px;">Create</button>


					</form>
      <button class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored" id="addkeyword">
        <i class="material-icons">search</i>

    </button>
					<script>
					$('#addkeyword').click(
							function() {
								var isbnNumber = document
										.getElementById("isbn").value;
								if (isbnNumber) {
									console.log(isbnNumber);
									searchForISBN(isbnNumber);
								}
							});
					$("#datetimepicker4").on("dp.change",function (e) {
				        console.log(e.date.format('H'));
				        console.log(e.date.toDate());
						window.latestdate=e.date.toDate();
						console.log(latestdate);
						//setDate(latestdate);
				        });
					window.setDate=function(){
						console.log(latestdate);
						  $.ajax({
							    url: '/setdate',
							    type: 'POST',
							    data: {systemDate: latestdate},
							    success: function(result) {
							        console.log("success");
							        
							    },
							    error:function (error) {
							    	window.errorMsg = JSON.parse(error.responseText);

				        			showError(errorMsg.errorMessage);
							      console.log(error);

							    }
							});
					}
					</script>
					
				</div>
      </main>
    </div>



          <!-- Your content goes here -->




      </section>
   
      
      <section class="mdl-layout__tab-panel" id="scroll-tab-2">
      <!-- Your content goes here -->
      <div class="page-content" >
	               
	
	<div style="margin-left:470px;margin-bottom:40px;">Search By Author,Keywords,Title, Publisher, Year Or ISBN</div>
          <form id="searchform">
  <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
    <label class="mdl-button mdl-js-button mdl-button--icon" for="sample6">
      <i class="material-icons">search</i>
    </label>
    <div class="mdl-textfield__expandable-holder">
      <input class="mdl-textfield__input" type="text" id="sample6">
      <label class="mdl-textfield__label" for="sample-expandable">Expandable Input</label>
    </div>
    <button id="searchbutton"onclick="searchresults();return false;"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
    Search
    </button>
    </form>

 <script>
 function searchresults(){
		 console.log("Searching");
	   //alert("inside searchresults");
	   //this is the text to search by
	   var keysearch=document.getElementById('sample6').value;
	   var urlstring="/book/search?search="+keysearch;
	   console.log(urlstring);
	   console.log(keysearch);
	   $.ajax({
			type : "GET",
			url : urlstring,
			success : function(data) {
				console.log(data);
					
				  main='<table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp" id="table4">'+
				  ' <thead><tr>  <th class="mdl-data-table__cell--non-numeric">Book Name</th><th>Book ID</th><th>ISBN</th><th>Current No Of Copies</th><th>Author</th><th>Publisher</th><th>Year of Publication</th><th>Locationr</th>'+' <th>Selected</th>   </tr>  </thead> <tbody>';
				  for(i=0;i<data.length;i++){
					  if(data[i].bookId != null )
				    main+='<tr><td class="mdl-data-table__cell--non-numeric">'+data[i].title+'</td><td>'+data[i].bookId+'</td><td>'+data[i].isbn+'</td><td>'+data[i].currentNumberOfCopies+'</td><td>'+data[i].author+'</td><td>'+data[i].publisher+'</td><td>'+data[i].yearOfPublication+'</td><td>'+data[i].location+'</td>'+
				    '<td>  <label class="mdl-radio mdl-js-radio" for="'+  data[i].bookId +'">'+
				    '<input type="radio" value ="'+data[i].title+'" name="bookselection"id="'+data[i].bookId+'" class="mdl-radio__button"  >'+
				    '<span class="mdl-radio__label">   </span> </label></td></tr>';

				  }
				  //console.log(main);
				  bookscart=[];
				  main+='</tbody></table>';
				  main+='<div id="buttonsclass"><button style="margin-right:20px;"id="searchbutton2" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Update</button>';
				  main+='<button id="searchbutton3" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Delete</button></div>';

				  document.getElementById('hi').innerHTML=main;
				  $('#searchbutton3').click(function(){
					  var chx = document.getElementsByName('bookselection');
						 for (var i=0; i<chx.length; i++) {
						   // If you have more than one radio group, also check the name attribute
						   // for the one you want as in && chx[i].name == 'choose'
						   // Return true from the function on first match of a checked item
						   if (chx[i].checked ) {
						     console.log(chx[i].id);
						     deletingbook(chx[i].id);
						     //window.idtobedeleted1=chx[i].id;
						   //  updatingbook(chx[i].id);
						   }
						 }
				  });
				  $('#searchbutton2').click(function(){
					  var chx = document.getElementsByName('bookselection');
						 for (var i=0; i<chx.length; i++) {
						   // If you have more than one radio group, also check the name attribute
						   // for the one you want as in && chx[i].name == 'choose'
						   // Return true from the function on first match of a checked item
						   if (chx[i].checked ) {
						     console.log(chx[i].id);
						   //  deletingbook(chx[i].id);
						  //   window.idtobedeleted1=chx[i].id;
						   //  updatingbook(chx[i].id);
						     updatingbook(chx[i].id);
						   }
						 }
				  });
				  $('input[name=bookselection]').on('change', function() {
						 var chx = document.getElementsByName('bookselection');
						 for (var i=0; i<chx.length; i++) {
						   // If you have more than one radio group, also check the name attribute
						   // for the one you want as in && chx[i].name == 'choose'
						   // Return true from the function on first match of a checked item
						   if (chx[i].checked ) {
						     console.log(chx[i].id);
						   }
						 }
						 });
				  
			},
			error:function(error){
				window.errorMsg = JSON.parse(error.responseText);

    			showError(errorMsg.errorMessage);
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
															
				}
			}
		});
	   
	 
	 $('input[name=bookselection]').on('change', function() {
	 var chx = document.getElementsByName('bookselection');
	 for (var i=0; i<chx.length; i++) {
	   // If you have more than one radio group, also check the name attribute
	   // for the one you want as in && chx[i].name == 'choose'
	   // Return true from the function on first match of a checked item
	   if (chx[i].checked ) {
	     console.log(chx[i].id);
	   }
	 }
	 });


	}
function deletingbook(bookId){
  console.log("yes im here to delete a book");
  $.ajax({
    url: '/book/delete',
    type: 'POST',
    data: {bookId: bookId},
    success: function(result) {
        console.log("Deklete book success");
        searchresults();
    },
    error:function (error) {
    	window.errorMsg = JSON.parse(error.responseText);

		showError(errorMsg.errorMessage);
      console.log(error);

    }
});


    //window.location.reload()
//$(".mdl-layout__tab:eq(5) span").click ();
}
function updatingbook(idtobedeleted1){
  console.log("inside updating book");
  //myWindow = window.open("update.html", "myWindow", "width=500,height=500");
  window.open('/book/update/'+ idtobedeleted1);
}

 </script>
  </div>
  <center>
<div id="hi"></div>
</center>


        </div>
      </section>
      
      
      
      <section class="mdl-layout__tab-panel" id="scroll-tab-3">
      <center><p>Please select a Librarian From the Dropdown List</p>
        <div class="page-content" id="library1" style="margin-left:20%">        
        
        </div>
        </center>
        <!-- Your content goes here -->

<script>
var a;
reload();
function reload(){


    //  alert("hi");
    $.ajax({
    	url:"/librarian/alllibrarians",
    	type:"GET",
    	success:function(data){
    		console.log(data[0].sjsuEmail);
    		a='<select class="ui search dropdown" id="chooselibrarian2">';
			a+='<option value=""Librarian</option>';
    		for(i=0;i<data.length;i++){
    			
    			a+='<option value="'+data[i].sjsuEmail+'">'+data[i].firstName+' '+data[i].lastName+'</option>';
    	    //  var a='<select class="ui search dropdown" id="chooselibrarian2">
    //<option value="">Librarian</option><option value="AL">Alabama</option><option value="AK">Alaska</option> <option value="IL">Illinois</option></select>';
    		}
    		a+='</select>';
    		console.log(a);
    		document.getElementById("library1").innerHTML = a;
    	    // console.log(document.getElementById('chooselibrarian2'));
    	     $('.ui.dropdown')
    	       .dropdown();
    	     
    	     $('.dropdown')
    	       .dropdown({
    	         action: function(text, value) {
    	           console.log(value);
					$.ajax({
						url:'/librarian/books',
						type:"POST",
						data:{sjsuEmail: value},
						success:function(data){
							//maincontent comes here
							console.log(data);
				   	           maincontent='<table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp" id="table5">'+
				    	          ' <thead><tr>  <th class="mdl-data-table__cell--non-numeric">Book Name</th><th>Selected</th>'+
				    	             '  <th>Author</th>  </tr>  </thead> <tbody>';
				    	          for(i=0;i<data.length;i++){
				    	            maincontent+='<tr><td class="mdl-data-table__cell--non-numeric">'+data[i].title+'</td>'+
				    	            '<td>  <label class="mdl-radio mdl-js-radio" for="'+  data[i].bookId  +'">'+
				    	            '<input type="radio" name="bookselections"id="'+data[i].bookId+'" class="mdl-radio__input"  >'+
				    	            '<span class="mdl-radio__label">   </span> </label></td><td>'+data[i].author+'</td></tr>';

				    	          }
				    	          maincontent+='</tbody></table> <button onclick="reload();"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">  Back  </button>'
				    	          +'<button onclick="updatethisbook();"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">  Update  </button>'
				    	          +'<button onclick="deletethisbook()"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">  Delete  </button>';
				    	         // console.log(maincontent);
				    	        //  calculatecart();

				    	          document.getElementById('library1').innerHTML=maincontent;
				    	          $('input[name=bookselections]').on('change', function() {
				        	          var chx = document.getElementsByName('bookselections');
				        	          for (var i=0; i<chx.length; i++) {
				        	            // If you have more than one radio group, also check the name attribute
				        	            // for the one you want as in && chx[i].name == 'choose'
				        	            // Return true from the function on first match of a checked item
				        	            if (chx[i].checked ) {
				        	              console.log(chx[i].id);
				        	              window.idtobedeleted=chx[i].id;
				        	            }
				        	          }
				        	          });
							
						},
						error:function(error){
							window.errorMsg = JSON.parse(error.responseText);

		        			showError(errorMsg.errorMessage);
							console.log(error);
						}
					});
 
    	        
    	          window.updatethisbook=function(){
    	        	  
    	        	  //window.location = '/book/update/'+ idtobedeleted;
    	        	  window.open('/book/update/'+ idtobedeleted);
    	        	  
    	        	  console.log("updating this book");
    	        	  console.log(idtobedeleted);
    	        	  /* var url='/book/update';
    	        	  $.ajax({
  	        		    url: url,
  	        		    type: 'GET',  	        		    
  	        		    success: function(result) {
  	        		        console.log("Update book success");
  	        		        alert("here");
  	        		        //window.location='/book/update';
  	        		       // reload();
  	        		    },
  	        		    error:function (data) {
  	        		      console.log(data);

  	        		    }
  	        		}); */
    	        	  
    	        	  
    	          }//updatethisbook function closes
					window.deletethisbook=function(){
    	        	  console.log("delething this book");
    	        	  console.log(idtobedeleted);
    	        	  $.ajax({
    	        		    url: '/book/delete',
    	        		    type: 'POST',
    	        		    data: {bookId: idtobedeleted},
    	        		    success: function(result) {
    	        		        console.log("Deklete book success");
    	        		        reload();
    	        		    },
    	        		    error:function (error) {
    	        		    	window.errorMsg = JSON.parse(error.responseText);

			        			showError(errorMsg.errorMessage);
    	        		      console.log(error);

    	        		    }
    	        		});
						
					}//deletethisbook function closes
    	         }//action closes
    	       });
    	     
    	},//success closes
    	error:function(data){
    		console.log(data);
    	}//error closes
    	
    });//ajax closes
console.log(a);
   


}
</script>
</section>

   
  
   </main>
   
  <script>
  </script>
  <div id="snackbar">Some text some message..</div>
</body>
</html>