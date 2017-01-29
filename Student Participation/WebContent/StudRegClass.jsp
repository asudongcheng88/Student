<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Class</title>
<link rel="stylesheet" href="styles.css">
<style>
.button3{
	background-color: #4CAF50;
    border: none;
    color: white;
    padding: 13.3px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
	font-size: 15px;
    margin: 4px 2px;
    cursor: pointer;
}
html { 
	background: url(image/reg.jpg) no-repeat center center fixed; 
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}
#class-field{
  		border: 4px solid rgb(255,232,57);
  		width: 600px;
  		margin:auto;
	}
.hideoption{
	display:none;
	visibility:hidden;
	height:o;
	font-size::0;	 
}
</style>
<script type="text/javascript" src="jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript" >

			$(document).ready(function(){
				
				/*----------event when clicked register class button---------------*/
				
				$(document).on('click','#btn-reg-class', function(){
					
					$.ajax({
						
						type:"GET",
						data:{
							action:"distinct subject"
						},
						url:'Subject',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(subjectArray){
							
							
							var subjectList = $.parseJSON(subjectArray);
							
							var subjectContainer = $('#subject-list');
							
							subjectContainer.empty();
							
							var subjectOption = [];
							subjectOption.push('<option class="hideoption" selected disable >Select Subject</option>');
							for(var i=0; i< subjectList.length; i++){
								
								subjectOption.push('<option value="' + subjectList[i].subjCode + '">' + subjectList[i].subjName + '</option>');
								
							}
							subjectContainer.append($(subjectOption.join('')));
							
						}
						
					})
				});//close clicked register class button
				
				
				/*-----------event when selecting subject-------------*/
				
				
				$(document).on('change', '#subject-list', function(){
					
					var selectedSubjectCode = $("#subject-list").find("option:selected").val();
					
					$.ajax({
					
						type:'GET',
						data:{
							action:"distinct group",
							subjCode: selectedSubjectCode
						},
						url:'Subject',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(groupArray){
							
							var groupList = $.parseJSON(groupArray);
							
							var groupContainer = $('#group-list');
							//groupContainer.empty();
							
							var groupOption = [];
							
							for(var i=0; i< groupList.length; i++){
								
								
								groupOption.push('<option>' + groupList[i].groupId + '</option>')
							}
							
							groupContainer.append($(groupOption.join('')));
						}
					})
					
					
				});//close selecting subject
				
				/*--------------registering class-----------------*/
				
				$(document).on('click','#btn-submit-register', function(){
					
					var selectedSubjectCode = $("#subject-list").find("option:selected").val();
					var selectedGroup = $("#group-list").find("option:selected").text();
					
					console.log(selectedSubjectCode);
					console.log(selectedGroup);
					
					$.ajax({
						
						type:'GET',
						dataType:'json',
						data:{
							subjCode: selectedSubjectCode,
							groupId : selectedGroup,
							action : "enroll student"
						},
						url:'Enroll'
						/*
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(isStudent){
							
							if(isStudent === false){
								console.log(isStudent);
		;
								
							}
							
						},
						error: function(error){
							
							alert("You are not student");
						}
						*/
						
					})
				});//close registered class
				
				$(document).on('click','#btn-view-class', function(){
					
					$.ajax({
						
						type:"GET",
						data:{
							action:"view class for student"
						},
						url:'Class',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(classArray){
							console.log(classArray);
							var classList = $.parseJSON(classArray);
							
							$requestContainer = $('#request-div');
							$requestContainer.empty();
							
							$viewClassContainer = $('#view-class-div');
							$viewClassContainer.empty();
							
							var tr = [];
							
							tr.push('<table border="2" style= "background-color: #00FFFF; color: #000000; margin: 0 auto;" >');
							tr.push('<thead>');
							tr.push('<tr><th colspan="5">Registered Class</th></tr>');
							tr.push('<tr>');
							tr.push('<th>Subject Code</th>');
							tr.push('<th>Subject Name</th>');
							tr.push('<th>Group</th>');
							tr.push('<th>Lecturer Name</th>');
							tr.push('</tr>	');
							tr.push('</thead>');
							tr.push('<tbody>');	  
							
							for(var i=0; i<classList.length;i++){
								
								tr.push('<tr>');
								tr.push('<td>' + classList[i].subjCode + '</td>');
								tr.push('<td>' + classList[i].subjName + '</td>');
								tr.push('<td>' + classList[i].groupId + '</td>');
								tr.push('<td>' + classList[i].lecName + '</td>');
								tr.push('</tr>');
							}
							
							tr.push('</tbody>');
							tr.push('</table>');
							
							$viewClassContainer.append($(tr.join('')));
						}
					})
				});//close view class
				
				$(document).on('click','#btn-request-leave', function(){
					
					dropRequest();
					
				});//close request leave
				
				
				function dropRequest(){
					
					$.ajax({
						
						type:"GET",
						data:{
							action:"view class for student"
						},
						url:'Class',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(classArray){
							console.log(classArray);
							var classList = $.parseJSON(classArray);
							
							$viewClassContainer = $('#view-class-div');
							$viewClassContainer.empty();
							
							$requestContainer = $('#request-div');
							$requestContainer.empty();
							
							var tr = [];
							
							tr.push('<table border="2" style= "background-color: #00FFFF; color: #000000; margin: 0 auto;" >');
							tr.push('<thead>');
							tr.push('<tr><th colspan="5">Registered Class</th></tr>');
							tr.push('<tr>');
							tr.push('<th>Subject Code</th>');
							tr.push('<th>Subject Name</th>');
							tr.push('<th>Group</th>');
							tr.push('<th>Lecturer Name</th>');
							tr.push('</tr>	');
							tr.push('</thead>');
							tr.push('<tbody>');	  
							
							for(var i=0; i<classList.length;i++){
								
								tr.push('<tr>');
								tr.push('<td>' + classList[i].subjCode + '</td>');
								tr.push('<td>' + classList[i].subjName + '</td>');
								tr.push('<td>' + classList[i].groupId + '</td>');
								tr.push('<td>' + classList[i].lecName + '</td>');
								
								if(classList[i].reqLeave == 0){
									
									tr.push('<td><button value="' + classList[i].classId +'" class="drop-subject">Drop</button></td>');
								
								}else{
									
									tr.push('<td><button value="' + classList[i].classId +'" class="drop-subject">Waiting approval</button></td>');
									
								}
								
								tr.push('</tr>');
							}
							
							tr.push('</tbody>');
							tr.push('</table>');
							
							$requestContainer.append($(tr.join('')));
						}
					})
				}
				
				
				$(document).on('click','.drop-subject', function(){
					
					var selectedClassId = $(this).attr("value");
					
					var selectedBtn = $(this).text();
					
					if(selectedBtn === "Drop"){
						
						$.ajax({
							
							type:'POST',
							data:{
								action:"request leave",
								classId: selectedClassId
							},
							url:'Enroll',
							success: function(){
								
								dropRequest();
							}
						})
						
					}else{
						
						alert("Your request is pending. Your lecturer will approve it later");
					}
					
					
					
				});
				
				
			});
		
			
		
</script>
</head>

<body>
	<fieldset id="class-field"><legend align="center"><h2>CLASS</h2></legend>
		<center><button id="btn-reg-class" onclick="document.getElementById('id01').style.display='block'" style="width:auto; margin:auto">Register Class</button>
		<a class="button button3" id="btn-view-class" >View Classes</a>
		<a class="button button3" id="btn-request-leave" >Request To Leave</a></center>
	
		<div id="id01" class="modal">
			<form class="modal-content animate" >
			    <div class="imgcontainer">
				<span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
				<img src="image/register1.png" alt="Avatar" class="avatar">
				</div>
		
		    <div class="container">
		    
		   		<label><b>Subject Name</b></label>
		      	<select id="subject-list"></select><br>
		      	
		      	<p>&nbsp</p>
		      	
		      	<label><b>Group</b></label>
		      	<select id="group-list">
		      		<option selected disable class="hideoption">Select Group</option>
		      	</select>
		      	
		      	<p>&nbsp</p>
		      	
		      	<button type="submit" name="btn-submit" id="btn-submit-register">Register</button>
		    </div>
		    
		    <!-- Temporary  -->
		    
		    
		
		    <div class="container" style="background-color:#f1f1f1">
		      <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
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
	<div id="id02" class="modal">
	  
		<form class="modal-content animate" id="02">
			<div class="imgcontainer">
				<span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
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
	</fieldset>
	
	<p>&nbsp</p>
	
	<div id="view-class-div"></div>
	
	<div id="request-div"></div>
	
	
	
	<p>&nbsp</p>
	
	<div id="class-details-div" ></div>			<!-- container for class detail list -->
	
	<p>&nbsp</p>
	
	<form action="StudentHome.jsp" align="center">
		<input type="submit" value="Back">
	</form>

</body>
</html>