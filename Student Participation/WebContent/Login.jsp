<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="styles.css">
<link rel="stylesheet" href="css/login/student.css">
<style>
html { 
  background: url(image/bg.jpg) no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
</style>

<script type="text/javascript" src="jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript" >

	$(document).ready(function(){
	
		$(document).on('click', '#stud-login', function(){
			
			$.ajax({
				
				type:'GET',
				data:{
					stud_id: $('#studId').val(),
					stud_pass: $('#studPass').val()
				},
				url:'StudentAuthen',
				
				success: function(result){
					console.log(result);
					window.location='StudentHome.jsp';
				}
				
			})
			
		});//close student log in
		
		$(document).on('click', '#lec-login', function(){
			
			$.ajax({
				
				type:'GET',
				data:{
				lecId: $('#lec-id').val(),
				lecPass: $('#lec-pass').val()
				},
				url:'LecturerAuthen',
				success: function(exist){
					alert("hello");
					var exist = $.parseJSON(exist);
					
					if(exist === true){
						
						window.location='LecturerHome.jsp';
					}
					else{
						window.location='LecturerError.jsp';	//**wrong password not go here
					}
					
				}
			})
			
		});//close lecturer log in
		
	});
	

</script>
</head>
<body>
<fieldset><legend align="center"><h2>Sign In</h2></legend>

	<center><img src="image/teen.jpg"></center></br>
	<center><button onclick="document.getElementById('id01').style.display='block'" style="width:auto; margin:auto">Student</button>
	
	<button onclick="document.getElementById('id02').style.display='block'" style="width:auto; margin:auto;">Instructor</button></center>
	
	
					<!------ Student login ------->
					
	
	<div id="id01" class="modal">
	  
		<form class="modal-content animate" id="01">
			<div class="imgcontainer">
				<span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
				<img src="image/student.png" alt="Avatar" class="avatar">
			</div>
			
			<div class="container">
				<label><b>ID</b></label>
				<input type="text" placeholder="Enter ID" name="id" id="studId" required>
				
				<label><b>Password</b></label>
				<input type="password" placeholder="Enter Password" name="pass" id="studPass" required>
				        
				<button type="submit" name="btn-login" id="stud-login">Sign In</button>
			</div>
			
			<div class="container" style="background-color:#f1f1f1">
				<button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
				<span class="psw">Forgot <a href="#">password?</a></span>
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
	
	
					<!------ Lecturer login ------->
	
	
	<div id="id02" class="modal">
	  
		<form class="modal-content animate">
			<div class="imgcontainer">
				<span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
				<img src="image/lecturer.png" alt="Avatar" class="avatar">
			</div>
			
			<div class="container">
				<label><b>ID</b></label>
				<input type="text" placeholder="Enter ID" name="id" id="lec-id" required>
				
				<label><b>Password</b></label>
				<input type="password" placeholder="Enter Password" name="pass" id="lec-pass" required>
				        
				<button type="submit" id="lec-login">Sign In</button>
			</div>
			
			<div class="container" style="background-color: #FFFFFF">
				<button type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn">Cancel</button>
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
	<center><a href="Register.jsp">Sign up here!</a></center>
</fieldset>

<div id="student-div">
</div>
<div id="lecturer-div">

</div>
</body>
</html>