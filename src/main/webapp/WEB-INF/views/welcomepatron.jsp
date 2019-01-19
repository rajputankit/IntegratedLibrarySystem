<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
.mdl-layout__content {
	padding: 24px;
	flex: none;
}

#library1 {
	height: 700px;
	margin-top: 30px;
}
#deletingcartbutton{

}
#searchbutton2{
margin-top:50px;
margin-left:200px;}
#logout{
position:absolute;
margin-left:1000px;
width:100px;
color:white;
}
#waitlistbutton{

font-size:10px;
}
#summarytable2{
margin-top:10px;
margin-left:0px;
}
#summarydiv{
margin-left:220px;
}
#returnbutton{
margin-left:280px;
margin-top:30px;
}
#searchform {
	margin-left: 420px;
	margin-bottom: 30px;
}
#waitlistdiv{
margin-top:30px;
}
#searchbutton {
	margin-left: 230px;
}

#mycart {
	margin-left: 450px;
	margin-bottom: 40px;
}
#carttab{
margin-left:110px;
margin-top:30px;
}

#mainform {
	margin-left: 390px;
}

#updatebook {
	margin-left: 390px;
}

#updatebutton {
	margin-top: 20px;
}

#chooselibrarian {
	height: 300px;
	margin-left: 300px;
}

#library1 {
	height: 300px;
	margin-left: 300px;
}

#hi {
	margin-left: 310px;
}

#table5 {
	margin-left: 400px;
}

#buttonsdiv {
	margin-top:50px;
	margin-left: 412px;
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
	<!-- Simple header with scrollable tabs. -->
	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
		<header class="mdl-layout__header">
			<div class="mdl-layout__header-row">
				<!-- Title -->
				<button id="logout"class="mdl-button mdl-js-button mdl-js-ripple-effect" onclick="logOutPatron();" style="width:200px;">
      Log Out
    </button>
				<span class="mdl-layout-title">SJSU Library</span>
			</div>
			<!-- Tabs -->
			<div class="mdl-layout__tab-bar mdl-js-ripple-effect">
				<a href="#scroll-tab-1" class="mdl-layout__tab is-active">Home</a> <a
					href="#scroll-tab-2" class="mdl-layout__tab">Search Catalog</a> <a
					href="#scroll-tab-6" class="mdl-layout__tab" id="mycart">My
					Cart</a>
			</div>
		</header>

		<main class="mdl-layout__content">

		<section class="mdl-layout__tab-panel is-active" id="scroll-tab-1">
			<div class="page-content" id="">
			<div id="summarydiv"></div>
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
			$(document).ready(function(){
				var returndate;
				var systemdate;
				window.newsystemdate=new Date();
				showsummary();
				window.bookreturn={};
				function showsummary(){
				$.ajax({ url: "/patron/getpatroninfo",
				        type:"GET",
				        success: function(data){
				        	
				        	window.patronloggedin=data;
				           console.log(data);
				           //returndate=data.bookCheckouts[0].returnDate;
				           console.log("order1");
				           $.ajax({
								url:"/getdate",
								type:"GET",
								async:false,
								success:function(data){
									systemdate=new Date(parseInt(data.systemDate));
									console.log(systemdate);
									//console.log(returndate);
									//console.log(Date.UTC(systemdate.getFullYear(), systemdate.getMonth(), systemdate.getDate()));
									a=returndate;
									newsystemdate=Date.UTC(systemdate.getFullYear(), systemdate.getMonth(), systemdate.getDate());
									console.log(newsystemdate);
									console.log(((a-newsystemdate)/(1000 * 60 * 60 * 24))-1);
								},
								error:function(error){
									window.errorMsg = JSON.parse(error.responseText);

				        			showError(errorMsg.errorMessage);
								}
							});
				           console.log("order2");
				          // window.loggedinpatron=data;
				           var content='<h1>Welcome '+data.firstName+'</h1>';
				           //alert(data.firstName);
				           if(data.currentlyCheckoutBooks>0){
				           var d=new Date(data.bookCheckouts[0].checkoutDate);
				           console.log(new Date(data.bookCheckouts[0].checkoutDate).getFullYear());
				           console.log(new Date(data.bookCheckouts[0].checkoutDate).getDate() + '/' + new Date(data.bookCheckouts[0].checkoutDate).getDate() );
				           content +='<h3> Your Fine is $ '+data.totalFine+'</h3>';
				           content+='<h3>These are your currently checked out books</h3>';
							
							
				       	content += '<table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp" id="summarytable">'
							+ ' <thead><tr>  <th>Book Name</th><th>Author</th><th>Publisher</th><th>CheckOut Date</th><th>Due Date</th><th>Selected</th><th>Renew</th>'
							+ '    </tr>  </thead> <tbody>';
							console.log("reaching here");
							for (i = 0; i < data.bookCheckouts.length; i++) {
							//	alert("hi");
								console.log("MY TEST " + data.bookCheckouts[i].checkoutBook.title);
								content += '<tr><td class="mdl-data-table__cell--non-numeric">'
										+ data.bookCheckouts[i].checkoutBook.title
										+ '</td><td>'
										+ data.bookCheckouts[i].checkoutBook.author
										+ '</td><td>'
										+ data.bookCheckouts[i].checkoutBook.publisher
										+ '</td><td>'
										+ (parseInt(new Date(data.bookCheckouts[i].checkoutDate).getMonth())+1)+'/'+new Date(data.bookCheckouts[i].checkoutDate).getDate()+'/'+new Date(data.bookCheckouts[i].checkoutDate).getFullYear()
										+ '</td><td>'
										+ (parseInt(new Date(data.bookCheckouts[i].returnDate).getMonth())+1)+'/'+new Date(data.bookCheckouts[i].returnDate).getDate()+'/'+new Date(data.bookCheckouts[i].returnDate).getFullYear()

										+ '</td>';
										content+= '<td>  <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="'+  data.bookCheckouts[i].checkoutBook.bookId  +'">'
										+ '<input type="checkbox" value ="'+data.bookCheckouts[i].checkoutBook.title+'" name="returnbooks"id="'+data.bookCheckouts[i].checkoutBook.bookId+'" class="mdl-checkbox__input"  >'
										+ '<span class="mdl-checkbox__label">   </span> </label></td>';
										//if((data.bookCheckouts[i].returnDate)-newsystemdate){}
										//console.log(data.bookCheckouts[i].returnDate);
										c=data.bookCheckouts[i].returnDate-newsystemdate;
										//console.log(((a-newsystemdate)/(1000 * 60 * 60 * 24))-1);

										//console.log(((c)/(1000 * 60 * 60 * 24))-1);
										if(((c)/(1000 * 60 * 60 * 24))-1<=5 && ((c)/(1000 * 60 * 60 * 24))-1>0){
											console.log("hi");
											content+='<td><button id="waitlistbutton" onclick="renewbook('+data.bookCheckouts[i].checkoutId+')" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Renew</button></td>'

										}else{
											content+='<td>Cannot be renewed at This time</td>';
										}
										//content+='<td><button id="waitlistbutton" onclick="renewbook('+data.bookCheckouts[i].checkoutBook.bookId+')" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Renew</button></td>'
										content+= '</tr>';
										
										

							}
							 content += '</tbody></table>';
								content += '<button id="returnbutton" onclick="returningbook()"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Return Book</button>';

				           }else{
				        	   
				        	   content+='<p>You Do Not Have Any Books Checked out at this time.</p>';
				        	   
				           }
				           
				           if(data.waitListedBooks.length==0){
				        	   content+='<p> You do not have any books in the waitlist</p>';
				           }else{
				        	   content+='<div id="waitlistdiv"><h3>You currently have '+data.waitListedBooks.length+' books waitlisted:</h3>';
				        	   
				        		content += '<table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp" id="summarytable2">'
									+ ' <thead><tr>  <th>Book Name</th><th>Author</th><th>Publisher</th><th>ISBN</th>'
									+ '    </tr>  </thead> <tbody>';
									
								for(i=0;i<data.waitListedBooks.length;i++){
									content += '<tr><td class="mdl-data-table__cell--non-numeric">'
										+ data.waitListedBooks[i].title
										+ '</td><td>'
										+ data.waitListedBooks[i].author
										+ '</td><td>'
										+ data.waitListedBooks[i].publisher
										+ '</td><td>'
										+ data.waitListedBooks[i].isbn
										+ '</td><td>'
									
								}
								 content += '</tbody></table></div>';

				           }
							
				          
							document.getElementById('summarydiv').innerHTML = content;

							var limit = 10;
							$('input[name=returnbooks]').on('click',function(evt) {
												if ($('[name=returnbooks]:checked').length > limit) {
													this.checked = false;

												}

											});
							
							 window.returningbook=function(){
							
									if ($('input[name=returnbooks]:checked').length > 0) {
										var chx1 = document.getElementsByName('returnbooks');
									console.log(chx1.length);
										for (var i = 0; i < chx1.length; i++) {
											if (chx1[i].checked) {
												// bookscart.push(chx1[i].value);
												console.log(chx1[i]);
												bookreturn[chx1[i].id] = chx1[i].value;
												console.log(bookreturn);
		
											}
										}

									}
									var temp = [];
									for(var i in bookreturn){
										temp.push(i);								
									}
									
									console.log(temp);
								finalizereturn(temp);
							}
							 window.renewbook=function(x){
								 url="/book/renew/"+x;
								 $.ajax({
									 url:url,
									 type:"GET",
									 success:function(data){
										 console.log("Renewwed success");
										 window.location.reload();
									 },
									 error:function(error){
										 window.errorMsg = JSON.parse(error.responseText);

						        			showError(errorMsg.errorMessage);
										 console.log("error renewing");
									 }
								 });
								 console.log(x);
							 }
							function finalizereturn(bookIds){
								$.ajax({
									type : "POST",
									url : "/book/return",
									data: {"bookIds": bookIds},
									success : function(error) {
										
										window.location='/patron/home';
										
									},error:function(error){
										window.errorMsg = JSON.parse(error.responseText);

					        			showError(errorMsg.errorMessage);
									}
								});
							}
							

				        }
				});
				
				
				}
				});
		</script>
				<!-- Your content goes here -->
				
			
			</div>
		</section>

		<section class="mdl-layout__tab-panel" id="scroll-tab-2">
			<div class="page-content">
			<div style="margin-left:470px;margin-bottom:40px;">Search By Author,Keywords,Title, Publisher, Year Or ISBN</div>
				<form id="searchform">
					<div
						class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
						<label class="mdl-button mdl-js-button mdl-button--icon"
							for="sample6"> <i class="material-icons">search</i>
						</label>
						<div class="mdl-textfield__expandable-holder">
							<input class="mdl-textfield__input" type="text" id="sample6">
							<label class="mdl-textfield__label" for="sample-expandable">Expandable
								Input</label>
						</div>
						<button id="searchbutton" onclick="searchresults();return false;"
							class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
							Search</button>
				</form>
				<script>
				function logOutPatron() {	
		    		$.ajax({
		    			type : "GET",
		    			url : "/logout",	
		    			success : function(data) {
		    			},
		    			error: function(error){
		    				console.log(error);
		    				console.log(error);
		        			errorMsg = JSON.parse(error.responseText);

		        			showError(errorMsg.errorMessage);
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

		    	$('#logout').click(function(e){
		    		console.log("clicked button");
		    		logOutPatron();	
		    	});
				window.booksaved = {};
					function searchresults() {
						var keysearch = document.getElementById('sample6').value;
						var urlstring = "/book/search?search=" + keysearch;
					//	alert(urlstring);
						console.log(keysearch);
						

					//	console.log(conversiondate[0] + "a");
					//	var currentdate = new Date(parseInt(conversiondate[0]),
					//			parseInt(conversiondate[1]),
					//			parseInt(conversiondate[2]));
					//	console.log(currentdate);
						$
								.ajax({
									type : "GET",
									url : urlstring,
								
									success : function(data) {
										console.log(data);
										window.booksdata = data;
										main = '<table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp" id="table4">'
												+ ' <thead><tr>  <th class="mdl-data-table__cell--non-numeric">Book Name</th><th>Author</th><th>Publisher</th><th>Location</th><th>Selected</th>'
												+ '    </tr>  </thead> <tbody>';
												//var j=0;
										for (i = 0; i < data.length; i++) {
											if(data[i].author!=null){
												//change condition here for current status
												if(data[i].currentNumberOfCopies==0){
													
													main += '<tr><td class="mdl-data-table__cell--non-numeric">'
														+ data[i].title
														+ '</td><td>'
														+ data[i].author
														+ '</td><td>'
														+ data[i].publisher
														+ '</td><td>'
														+ data[i].location
														+ '</td>';
											main+= '<td><button id="waitlistbutton" onclick="waitlistbook('+data[i].bookId+')" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Join Waitlist</button></td></tr>';
													
												}else{
												console.log("hey");
													
													
															main += '<tr><td class="mdl-data-table__cell--non-numeric">'
																+ data[i].title
																+ '</td><td>'
																+ data[i].author
																+ '</td><td>'
																+ data[i].publisher
																+ '</td><td>'
																+ data[i].location
																+ '</td>';
													main+= '<td>  <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="'+  data[i].bookId  +'">'
													+ '<input type="checkbox" value ="'+data[i].title+'" name="bookselection"id="'+data[i].bookId+'" class="mdl-checkbox__input"  >'
													+ '<span class="mdl-checkbox__label">   </span> </label></td></tr>';
													
												}	
										}
											//add currentstatus = 0 check over here
										}
										//console.log(main);
										bookscart = [];
										main += '</tbody></table>';
										main += '<button id="searchbutton2" onclick="addingcart()"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Add to Cart</button>';
										document.getElementById('hi').innerHTML = main;
										

										var limit = 5;
										$('input[name=bookselection]')
												.on(
														'click',
														function(evt) {
															if ($('[name=bookselection]:checked').length > limit) {
																this.checked = false;

															}

														});
									},error:function(error){
										errorMsg = JSON.parse(error.responseText);

					        			showError(errorMsg.errorMessage);
									}

								});

					}
					function addingcart() {
						console
								.log($('input[name=bookselection]:checked').length);
						if ($('input[name=bookselection]:checked').length > 0) {
							var chx1 = document
									.getElementsByName('bookselection');

							for (var i = 0; i < chx1.length; i++) {
								if (chx1[i].checked) {
									// bookscart.push(chx1[i].value);
									console.log(chx1[i]);
									booksaved[chx1[i].id] = chx1[i].value;
									localStorage.setItem('items', chx1.length);
									localStorage.setItem('books', JSON
											.stringify(booksaved));
									bookscart.push(chx1[i].value);

								}
							}

						}
						console.log(booksaved);
						console.log(bookscart);
						$(".mdl-layout__tab:eq(2) span").click();
						calculatecart();

					}
					window.waitlistbook=function(id){
						console.log(id);
						url="/book/waitlist/"+id;
						console.log(url);
						$.ajax({
							type:"GET",
							url:url,
							success:function(data){
								window.location.reload();
								
							},
							error:function(error){
								window.errorMsg = JSON.parse(error.responseText);

			        			showError(errorMsg.errorMessage);
								console.log("error while waitlisting");
							}
						});
					}
					window.calculatecart = function() {
						var data = JSON.parse(localStorage.books);
						console.log(data);
						console.log(Object.keys(data).length);
						if (Object.keys(data).length != 0) {

							var maincontent = '<table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp" id="table5">'
									+ ' <thead><tr>  <th class="mdl-data-table__cell--non-numeric">Book Name</th><th>Selected</th>'
									+ '   </tr>  </thead> <tbody>';
							for ( var key in data) {
								maincontent += '<tr><td class="mdl-data-table__cell--non-numeric">'
										+ data[key]
										+ '</td>'
										+ '<td>  <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="'+key+'">'
										+ '<input type="checkbox" name="bookdeletioncart"id="'+key+'" class="mdl-checkbox__input"  >'
										+ '<span class="mdl-checkbox__label">   </span> </label></td></tr>';

							}
							maincontent += '</tbody></table> <div id="buttonsdiv"><button id="deletingcartbutton"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" onclick="deletecart()"> Delete </button>';
							maincontent += ' <button id="checkout"class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" onclick="checkout()"> Checkout </button></div>';
							//console.log(maincontent);
							document.getElementById('carttab').innerHTML = maincontent;
						} else {
							var emptycontent = '<h2>Your Cart Is Empty</h2';
							document.getElementById('carttab').innerHTML = emptycontent;
						}

					}
					window.deletecart = function() {

						if ($('input[name=bookdeletioncart]:checked').length > 0) {
							var del = document
									.getElementsByName('bookdeletioncart');
							var bookdel = {};
							for (var i = 0; i < del.length; i++) {
								if (del[i].checked) {
									// bookscart.push(chx1[i].value);
									console.log(del[i]);
									var data = JSON.parse(localStorage.books);
									console.log(data);
									delete data[del[i].id];
									console.log("delete this" + del[i].id);
									console.log(data);
									localStorage.setItem('books', JSON
											.stringify(data));
									//    sessionStorage.setItem('total',Object.keys(data).length);

									calculatecart();

									//    $(".mdl-layout__tab:eq(5) span").click ();
									//      booksaved[del[i].id]=del[i].value;
									//  sessionStorage.setItem('items', chx1.length);
									//sessionStorage.setItem('books', JSON.stringify(booksaved));
									//bookscart.push(chx1[i].value);

								}
							}

						}
					}
					window.checkout = function() {
						var data = JSON.parse(localStorage.books);
						var count1 = localStorage.getItem('total');
						console.log(count1);
						if (count1 == null) {
							localStorage.setItem('total',
									Object.keys(data).length);
							console.log(JSON.parse(localStorage.total));
							//$(".mdl-layout__tab:eq(1) span").click();
						//	finalizeCheckout(temp);

						} else {
							console.log("inside not null");
							var count = JSON.parse(localStorage.total);

							/*if (count + Object.keys(data).length > 10) {
								alert("You can take upto 10 books a day. Please delete "
										+ (count + Object.keys(data).length - 10)
										+ " books");
							} */
							localStorage.setItem('total',
									Object.keys(data).length + count);

							console.log(JSON.parse(localStorage.total));
						//	$(".mdl-layout__tab:eq(1) span").click();

							//    localStorage.clear();
							// if(JSON.parse(sessionStorage.total)+)
						}
							var temp = [];
							for(var i in booksaved){
								temp.push(i);								
							}
							
							console.log(temp);
								
							finalizeCheckout(temp);
						
					}
					
					function finalizeCheckout(bookIds){	
											
						$.ajax({
							type : "POST",
							url : "/book/checkout",
							data: {"bookIds": bookIds},
							success : function(data) {
								console.log(data);
								localStorage.clear();
								window.location='/patron/home';
								
							},error:function(error){
								console.log("getting hit");
								errorMsg = JSON.parse(error.responseText);

			        			showError(errorMsg.errorMessage);
							}
						});
					}
					
					
					
				</script>

			</div>
			<div id="hi"></div>
	</div>
	</section>



	<section class="mdl-layout__tab-panel" id="scroll-tab-6">
		<div class="page-content" id="carttab">
			<!-- Your content goes here -->



		</div>
	</section>
	</main>
	</div>
	<div id="snackbar">Some text some message..</div>
</body>
</html>
