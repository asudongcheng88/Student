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
.hideoption{
	display:none;
	visibility:hidden;
	height:o;
	font-size::0;	
}
#class-field{
  border: 4px solid rgb(255,232,57);
  width: 600px;
  margin:auto;
}
</style>
<script type="text/javascript" src="jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript" >

$(document).ready(function(){
	
	$.ajax({
		
		type:'GET',
		data:{
			action: "sign in"
			
		},
		url:'Status',
		headers:{
				
			Accept: "application/json; charset=utf-8",
			"Content-type": "application/json; charset=utf-8"
		},
		success: function(result){
			var lecId = result;
			
			if(lecId === "null"){
				alert(lecId);
				window.location = "LecLogin.html";
				
			}else{

				$(document).ready(function(){
		
		
				/*------event when CLICK REGISTER button-------------*/
				
				$(document).on('click', '#btn-reg', function(){
					
					
					
					$.ajax({
						
						type:'GET',
						data:{
							action: "Register"
						},
						url: 'Group',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(arrayGroup){
							//alert("hey");
							var groupList = $.parseJSON(arrayGroup);
							
							//alert(groupList);
		
							$container = $('#group-list');
							var option=[];
							
							for(var i=0; i<groupList.length; i++){
								
								//alert("Group id = "+groupList[i].groupId);
								
								option.push('<option id="group-id">'+groupList[i].groupId+'</option>');
								
							}
							
							option.push('<br>');
							$container.append($(option.join('')));
						}
					})
					
					
				}); //close REGISTER button event
				
				
				
				/*--------------REGISTER CLASS----------------*/
				
				
				$(document).on('click', '#submit-btn', function(){
					
					
					$groupId = $('#group-list').find('option:selected').text();
					$subjCode = $('#subj-code').val();
					$subjName = $('#subj-name').val();
					
					console.log($groupId);
					console.log($subjCode);
					console.log($subjName);
					
					$.ajax({
						
						type:'POST',
						data:{
							groupId: $groupId,
							subjCode : $subjCode,
							subjName : $subjName
						},
						
						url:'Class',
						success: function(){
							alert("Successfully registered class");
						}
						
					})
				});//close register class
				
				
				/*----------View Class--------------*/
				
				
				$(document).on('click', '#btn-view', function(){
					
					$.ajax({
					
						type:'GET',
						data:{
							action: "view class"
						},
						url:'Class',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(classArray){
							
							console.log(classArray);
							
							var classDetailsList = $.parseJSON(classArray);
							
							$container = $('#class-details-div');
							$container.empty();
							var tr=[];
							
							tr.push('<table id="class-details" border="2" style= "background-color: #00FFFF; color: #000000; margin: 0 auto;" >');
							tr.push('<thead>');
							tr.push('<tr><th colspan="5">Classes Details</th></tr>');
							tr.push('<tr>');
							tr.push('<th>No</th>');
							tr.push('<th>Group ID</th>');
							tr.push('<th>Subject Code</th>');
							tr.push('<th>Subject Name</th>');
							tr.push('<th>Total Number Of Students</th>');
							tr.push('</tr>');
							tr.push('</thead>');
							tr.push('<tbody>');	      
					      
						
							
							for(var i=0; i<classDetailsList.length;i++){
								
								tr.push('<tr>');
								tr.push('<td>' + (i+1) + '</td>');
								tr.push('<td>' + classDetailsList[i].groupId + '</td>');
								tr.push('<td>' + classDetailsList[i].subjCode + '</td>');
								tr.push('<td>' + classDetailsList[i].subjName + '</td>');
								tr.push('<td align="center">' + classDetailsList[i].total + '</td>');
								
								tr.push('</tr>');
							}
							
							tr.push('</tbody>');
							tr.push('</table>');
							
							$container.append($(tr.join('')));
						}
						
						
					})
					
				});
			});
			}//close else
			
		}// close success call
		
	})//close ajax
})//close ajax
	
</script>
</head>

<body>
	<fieldset id="class-field"><legend align="center"><h2>CLASS</h2></legend>
		<center><button id="btn-reg" onclick="document.getElementById('id01').style.display='block'" style="width:auto; margin:auto">Register Class</button>
		<a class="button button3" id="btn-view" >View Classes</a>
		<a class="button button3" id="btn-request" href="LecAttendance.jsp">Class Attendance</a></center>
	
		<div id="id01" class="modal">
			<form class="modal-content animate" >
			    <div class="imgcontainer">
				<span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
				<img src="image/register1.png" alt="Avatar" class="avatar">
				</div>
		
		    <div class="container">
		      <label><b>Group</b></label>
		      <select id="group-list"></select><br><br>
				<option class="hideoption" value="Group" selected disable>Group ID</option>	
		      
		      <label><b>Subject Code</b></label>
		      <input type="text" placeholder="Enter Subject Code" name="sid" id="subj-code" required>
		      
		      <label><b>Subject Name</b></label>
		      <input type="text" placeholder="Enter Subject Name" name="sname" id="subj-name" required>
		      <!--  
		      <label><b>Lecturer ID</b></label>
		      <input type="text" placeholder="Enter Lecturer ID" name="lid" id="lid" required>
		      -->
		      
		      <button type="submit" name="btn-submit" id="submit-btn">Register</button>
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
	</fieldset>
	
	<p>&nbsp</p>
	
	<div id="class-details-div" ></div>			<!-- container for class detail list -->
	
	<p>&nbsp</p>
	
	<form action="LecturerHome.jsp" align="center">
		<input type="submit" value="Back">
	</form>

</body>
</html>