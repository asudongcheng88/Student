<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Classmates Details</title>
<style>
body { 
	background: url(image/children.jpg) no-repeat center center fixed; 
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
#details-field{
  		border: 4px solid rgb(255,232,57);
  		width: 800px;
  		margin:auto;
	}
</style>
<script type="text/javascript" src="jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript" >

				$(document).ready(function(){
	
				console.log("ready");
				$.ajax({
					
					type:'GET',
					data:{
						
						action: "classmate details"
					},
					url:'Subject',
					headers:{
						
						Accept: "application/json; charset=utf-8",
						"Content-type": "application/json; charset=utf-8"
					},
					success: function(subjectArray){
		
						var subjectList = $.parseJSON(subjectArray);
						$selectSubjectContainer = $('#subject-name');
						
						var subjectOption = [];
						
						for(var i=0; i<subjectList.length; i++){
		
							subjectOption.push('<option value="' + subjectList[i].subjCode + '">' + subjectList[i].subjName + '</option>');
						}
						$selectSubjectContainer.append($(subjectOption.join('')));
					}
				})
				
				$(document).on('change','#subject-name', function(){
					
					var selectedSubject = $("#subject-name").find("option:selected").text();
					
					$.ajax({
						
						type:'GET',
						data:{
							action:"classmate details",
							subjName:selectedSubject
						},
						url:'Group',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(groupArray){
		
							var groupList = $.parseJSON(groupArray);
							
							$groupContainer = $('#group-list');
							$groupContainer.empty();
							
							var groupOption = [];
							
							for(var i=0; i<groupList.length; i++){
		
								groupOption.push('<option>' + groupList[i].groupId + '</option>')
							}
							
							$groupContainer.append($(groupOption.join('')));
						}
					})
				});
				
				$(document).on('click','#display-details', function(){
					console.log("display");
					var selectedSubject = $("#subject-name").find("option:selected").text();
					var selectedGroup = $("#group-list").find("option:selected").text();
					
					$.ajax({
						
						type:'GET',
						data:{
							action: "classmate details",
							subjName : selectedSubject,
							groupId: selectedGroup
						},
						url:'Details',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(studentArray){
							var studentDetailsList = $.parseJSON(studentArray);
							
							console.log(studentArray);
							
							$studentDetailsContainer = $('#student-details-div');
							$studentDetailsContainer.empty();
							var tr=[];
							
							tr.push('<table border="2" style= "background-color: #00FFFF; color: #000000; margin: 0 auto;" >');
							tr.push('<thead>');
							tr.push('<tr><th colspan="4">Student Details</th></tr>');
							tr.push('<tr>');
							tr.push('<th>No</th>');
							tr.push('<th>Id</th>');
							tr.push('<th>Name</th>');
							tr.push('<th>Email</th>');
							tr.push('</tr>	');
							tr.push('</thead>');
							tr.push('<tbody>');	      
					      
						
							
							for(var i=0; i<studentDetailsList.length;i++){
								
								tr.push('<tr>');
								tr.push('<td>' + (i+1) + '</td>');
								tr.push('<td>' + studentDetailsList[i].studId + '</td>');
								tr.push('<td>' + studentDetailsList[i].studName + '</td>');
								tr.push('<td>' + studentDetailsList[i].studEmail + '</td>');
								tr.push('</tr>');
							}
							
							tr.push('</tbody>');
							tr.push('</table>');
							
							$studentDetailsContainer.append($(tr.join('')));
						}
					})
					
				});
			});
			
</script>
</head>

<body>
	<center><fieldset id="details-field"><legend><h2>Display Classmates Details</h2></legend>
		
		    <p>&nbsp</p>
		    
		    <select name="subCode" id="subject-name">
				<option selected disable class="hideoption">Subject Code</option>
			</select>
			
			<select name="group" id="group-list">
				<option selected disable class="hideoption" value="Group">Group</option>
			</select>

			<button type="submit" name="display" id="display-details">Display</button>
		
			<p>&nbsp</p>
		
			<div id="student-details-div"></div>
		
	</fieldset>
	</center>
	<p>&nbsp</p>
	<form method="link" action="StudentHome.jsp" align="center">
		<input type="submit" value="Back">
	</form>
	
</body>
</html>