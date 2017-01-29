<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendance</title>
<style>
body { 
	background: url(image/hd.jpg) no-repeat center center fixed; 
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
#attendance-field{
  border: 4px solid rgb(255,232,57);
  width: 750px;
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
		
				$.ajax({
					
					type:'GET',
					data:{
						action: "for student details"
					},
					url:'Subject',
					headers:{
						
						Accept: "application/json; charset=utf-8",
						"Content-type": "application/json; charset=utf-8"
					},
					success: function(subjectArray){
						
						
						
						var subjectList = $.parseJSON(subjectArray);
						$subjectContainer = $('#subject-list');
						
						var subjectOption = [];
		
						
						for(var i=0; i<subjectList.length; i++){
							subjectOption.push('<option value="' + subjectList[i].subjCode + '">' + subjectList[i].subjName+ '</option>');
						}
						
						$subjectContainer.append($(subjectOption.join('')));
						
						$dateContainer = $('#date-container');
						$dateContainer.empty();
						
						
					}
					
				});//close get subject list
				
				$(document).on('change','#subject-list', function(){
					
					var selectedSubject = $('#subject-list').find('option:selected').val();
					
					$.ajax({
							
						type:'GET',
						data:{
								
							action:"distinct group",
							subjCode : selectedSubject
						},
						url:'Subject',
						headers:{
								
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(groupArray){
								
							var groupList = $.parseJSON(groupArray);
								
							$groupContainer = $('#group-list');
								
							var groupOption = [];
							
							//option.push('<option selected disable class="hideoption" >Group</option>');
							
							for(var i=0; i<groupList.length; i++){
									
								groupOption.push('<option>' +groupList[i].groupId+ '</option>');
							}
								
							$groupContainer.append($(groupOption.join('')));
						}
					})
					
				});//close selecting subject
				
				/*---event when clicked give attendance button----------*/
				
				$(document).on('click','#btn-attendance', function(){
					
					console.log("clicked");
					
					var selectedSubject = $('#subject-list').find('option:selected').text();
					var selectedGroup = $('#group-list').find('option:selected').text(); 
			
					
					$.ajax({
						
						type:'GET',
						data:{
							action:"student list",
							selectedSubject: selectedSubject,
							selectedGroup: selectedGroup
						},
						url:'Attendance',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(bigData){
												
							var split = bigData.split("%");
							
							var studentList = $.parseJSON(split[0]);
							
							$tableContainer = $('#attendance-table');
							$tableContainer.empty();
							var tr=[];
							
							tr.push('<thead><tr><th colspan="4">ATTENDANCE</th></tr><tr><th>No</th><th>Student ID</th><th>Student Name</th><th>Student Attendance</th></tr></thead>');
		
							tr.push('<tbody>');	      
		
							for(var i=0; i<studentList.length;i++){
								
								console.log(studentList[i].studId);
								console.log(studentList[i].studName);
								
								tr.push('<tr>');
								tr.push('<td>' + (i+1) + '</td>');
								tr.push('<td>' + studentList[i].studId + '</td>');
								tr.push('<td>' + studentList[i].studName + '</td>');
								tr.push('<td align="center"><input type="checkbox"  class="checkbox-student-id" value="' + studentList[i].studId + '"</td>');
								tr.push('</tr>');
							}
							
							tr.push('</tbody>');
							tr.push('<tr><td colspan = "4" align="center"><input type="button" value="Submit Attendance" id="submit-att" /></td></tr>')
							
							$tableContainer.append($(tr.join('')));
							
							//for date
							
							var date = [];
							var todayDate = split[1];
							
							date.push('<input type="text" id="today-date" value=' + todayDate + '/>');
							
							$dateContainer.append($(date.join('')));
						}
					})
				});
				
				$(document).on('click', '#submit-att', function(){
					
					var selectedSubjectCode = $('#subject-list').find('option:selected').val();
					var selectedSubject = $('#subject-list').find('option:selected').text();
					var selectedGroup = $('#group-list').find('option:selected').text(); 
					var date = $('#today-date').val();
					
					var allStudentId = [];
				    
					$('#attendance-table').find('input[type="checkbox"]:checked').each(function () {
				      	var value = $(this).val();
				      	
				      	allStudentId.push(value);
				    	
				    });
					
					console.log(allStudentId);
					
					
					$.ajax({
						
						type:'POST',
						//dataType:'json',
						data:{
							subjCode:selectedSubjectCode,
							subjName:selectedSubject,
							groupId:selectedGroup,
							todayDate: date,
							allStudId : allStudentId,
							action:"submit attendance"
							
						},
						url:'Attendance',
						
						success: function(){
							
							alert("You have submitted today attendance");			//succes not function but error can execute. 
							window.location.reload(false); 
							
						},
						error: function(){
							
							alert("There is an error while submitting the attenaence")
							
						}
						
					})
					
				});
			});
		}//close else
		
		}// close success call
		
	})//close ajax
})
</script>
</head>
<body>
	<fieldset id="attendance-field"><legend color="white" align="center"><h2><font color="white">Attendance</font></h2></legend> 
			<center>
				<select name="subCode" id="subject-list">
					<option selected disable class="hideoption" value="Group">Subject Code</option>
				</select>
				</br></br>
				<select name="group" id="group-list">
					<option selected disable class="hideoption" value="Group">Group ID</option>
				</select>
				
				<p>&nbsp</p>

				<button type="submit" name="display" id="btn-attendance">Check Attendance</button>
			
				<p>&nbsp</p>
			
				<div id="date-container"></div>
				
			</center>
			
			<p>&nbsp</p>
			
			<table border="2" style= "background-color: #FFCCFF; color: #000000; margin: 0 auto;" id="attendance-table" ></table><br>
			
	</fieldset>
	<p>&nbsp</p>
	<form method="link" action="LecRegClass.jsp" align="center">
		<input type="submit" value="Back">
	</form>
	
</body>
</html>