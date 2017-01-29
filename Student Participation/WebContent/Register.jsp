<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="styles.css">
<title>Sign Up</title>
<style>
html { 
  background: url(image/reg.jpg) no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
</style>
<script type="text/javascript" src="jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript" >

	$(document).ready (function(){
		
		
		/*---------REGISTER STUDENT----------------*/
	
		
		$(document).on('click', '#regStud', function(){
				
			$.ajax({
					
				type:'POST',
				data:{
						
					studId: $('#studId').val(),
					studName: $('#studName').val(),
					studEmail: $('#studEmail').val(),
					studPass: $('#studPass').val(),
					studPhone: $('#studPhone').val(),
						
				},
				url: 'StudentAuthen',
				success: function(){
					window.location='Login.jsp'			//***NOT go here
				}
						
			})
				
		});//close student register
		
/*---------REGISTER Lecturer----------------*/
	
		
		$(document).on('click', '#regLec', function(){
				
			console.log($('#lecName').val());
			
			$.ajax({
					
				type:'POST',
				data:{
						
					lecId: $('#lecId').val(),
					lecName: $('#lecName').val(),
					lecEmail: $('#lecEmail').val(),
					lecPass: $('#lecPass').val(),
					
						
				},
				url: 'LecturerAuthen',
				success: function(){
					window.location='Login.jsp'			//***NOT go here
				}
						
			})
				
		});//close student register
		
	});
</script>
</head>

<body>
	<fieldset><legend align="center"><h2>Sign Up</h2></legend>
	
		<center><img src="image/register.jpg"></center></br>
		<center><button onclick="document.getElementById('id01').style.display='block'" style="width:auto; margin:auto">Student</button>
		
		<button onclick="document.getElementById('id02').style.display='block'" style="width:auto; margin:auto;">Instructor</button></center>
		
		
		<!-- *******************Student Form******************* -->
		
		
		<div id="id01" class="modal">
		  
			<form class="modal-content animate" >
			  
				<div class="imgcontainer">
			    	<span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
			      	<img src="image/student.png" alt="Avatar" class="avatar">
			    </div>
			
			    <div class="container">
			      	<label><b>ID</b></label>
			      	<input type="text" placeholder="Enter ID" name="id" id="studId" required>
			      
			      	<label><b>Name</b></label>
			      	<input type="text" placeholder="Enter Name" name="name" id="studName" required>
			      
			      	<label><b>Email</b></label>
			      	<input type="text" placeholder="Enter Email" name="email" id="studEmail" required>
			      
			     	<label><b>Password</b></label>
			      	<input type="password" placeholder="Enter Password" name="pass" id="studPass" required>
			      
			      	<label><b>Phone Number</b></label>
			      	<input type="text" placeholder="Enter Phone Number" name="tel" id="studPhone" required>
			     
			      	<button type="submit" onclick="document.getElementById('id01')" name="btn-signup" id="regStud" >Register</button>
			    </div>
			
			    <div class="container" style="background-color:#f1f1f1">
			      	<button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
			      	<!--<span class="psw">Forgot <a href="#">password?</a></span>-->
			    </div>
			</form>
		</div>
		
		<script>
		// Get the modal
		var modal = document.getElementById('id01');
		
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
		    if (event.target == modal) {
		        modal.style.display = "none";
		    }
		}
		</script>
		
		
		<!-- *******************Lecturer Form******************* -->
		
		
		<div id="id02" class="modal">
		  
		  	<form class="modal-content animate">
		  
			    <div class="imgcontainer">
			      <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
			      <img src="image/lecturer.png" alt="Avatar" class="avatar">
			    </div>
			
			    <div class="container">
			      <label><b>ID</b></label>
			      <input type="text" placeholder="Enter ID" name="id" id="lecId" required>
			      
			      <label><b>Name</b></label>
			      <input type="text" placeholder="Enter Name" name="name" id="lecName" required>
			      
			      <label><b>Email</b></label>
			      <input type="text" placeholder="Enter Email" name="email" id="lecEmail" required>
			      
			      <label><b>Password</b></label>
			      <input type="password" placeholder="Enter Password" name="pass" id="lecPass" required>
			      
			      <button type="submit" onclick="document.getElementById('id02')" name="btn-9ooo9" id="regLec" >Register</button>
			    </div>
			
			    <div class="container" style="background-color:#f1f1f1">
			      <button type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn" value="x-">Cancel</button>
			      <span class="psw">Forgot <a href="#">password?</a></span>
		  		</div>
		  </form>
		</div>
		
		
		
		<script>
		// Get the modal
		var modal = document.getElementById('id02');
		
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
		    if (event.target == modal) {
		        modal.style.display = "none";
		    }
		}
		</script>
		<br>
		<center><a href="Login.jsp">Sign in here!</a></center>
	</fieldset>
</body>
</html>