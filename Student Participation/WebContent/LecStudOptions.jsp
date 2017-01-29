<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Students</title>

<style>

	.hideoption{
		display:none;
		visibility:hidden;
		height:0;
		font-size:0;
	}
	
	#student-field{
  		border: 4px solid rgb(255,232,57);
  		width: 600px;
  		margin:auto;
	}
	
	html { 
	background: url(image/Bookshelf.jpg) no-repeat center center fixed; 
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
</style>

<script type="text/javascript" src="jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript">
	

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
					
					
			/*********************************display student details***************************************/
					
					
					/*----------------event when click Display Student Details button------------*/
					
					
					$(document).on('click', '#btn-student-details', function(){
						
						$('#attd-details-div').empty();
						$('#group-list-div').empty();
						$('#subject-list-div').empty();
						
						
						$.ajax({
							
							type:'GET',
							data:{
								action: "for student details"
							},
							url:'Subject',
							success: function(subjectArray){
								
								var subjectList = $.parseJSON(subjectArray);
								
								$subjectListContainer = $('#subject-list-div');
								$subjectListContainer.empty();
								
								$requestContainer = $('#request-div');
								$requestContainer.empty();
								
								var option = [];
								
								option.push('<select id="subject-list" >');
								option.push('<option selected disable class="hideoption" >Please Select Subject</option>')
								for(var i=0; i<subjectList.length; i++){
									
									option.push('<option>' + subjectList[i].subjName + '</option>');
								}
								
								option.push('</select>');
								$subjectListContainer.append($(option.join('')));
							}
						})
					});//close student details event
					
					
					/*----------event when selecting subject list------------- */
					
					
					$(document).on('change', '#subject-list', function(){
						
						var selectedSubject = $("#subject-list").find("option:selected").text();
						
						console.log(selectedSubject);
						
						$.ajax({
							
							type:'GET',
							data:{
								selectedSubject: selectedSubject,
								action: "Student details"
							},
							url:'Group',
							headers:{
								
								Accept: "application/json; charset=utf-8",
								"Content-type": "application/json; charset=utf-8"
							},
							success: function(groupArray){
								var groupList = $.parseJSON(groupArray);
								
								$groupListContainer = $('#group-list-div');
								var option=[];
								
								option.push('<select id="group-list">');
								option.push('<option selected disable class="hideoption" >Please Select Group</option>');
								
								for(var i=0; i<groupList.length; i++){
									
									option.push('<option>' + groupList[i].groupId + '</option>')
								}
								
								option.push('</select>');
								$groupListContainer.append($(option.join('')));
							}
						})
					});
					
					
					/*--------event when selecting group-------*/
					
					
					$(document).on('change', '#group-list', function(){
						
						var selectedSubject = $("#subject-list").find("option:selected").text();
						var selectedGroup = $("#group-list").find("option:selected").text();
						
						console.log(selectedSubject);
						
						$.ajax({
							
							type:'GET',
							data:{
								action:"student list",
								selectedSubject: selectedSubject,
								selectedGroup: selectedGroup
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
								tr.push('<tr><th colspan="5">Student Details</th></tr>');
								tr.push('<tr>');
								tr.push('<th>No</th>');
								tr.push('<th>Id</th>');
								tr.push('<th>Name</th>');
								tr.push('<th>Email</th>');
								tr.push('<th>Phone No</th>');
								tr.push('</tr>	');
								tr.push('</thead>');
								tr.push('<tbody>');	      
						      
							
								
								for(var i=0; i<studentDetailsList.length;i++){
									
									tr.push('<tr>');
									tr.push('<td>' + (i+1) + '</td>');
									tr.push('<td>' + studentDetailsList[i].studId + '</td>');
									tr.push('<td>' + studentDetailsList[i].studName + '</td>');
									tr.push('<td>' + studentDetailsList[i].studEmail + '</td>');
									tr.push('<td>' + studentDetailsList[i].studPhone + '</td>');
									tr.push('</tr>');
								}
								
								tr.push('</tbody>');
								tr.push('</table>');
								
								$studentDetailsContainer.append($(tr.join('')));
							}
						})
					});//close selectiing group
					
					
					/*-----------drop request------------------------*/
					
					$(document).on('click','#btn-drop-request', function(){
						
						$('#attd-details-div').hide();
						
						$.ajax({
							
							type:'GET',
							data:{
								action:"drop request"
							},
							url:'Enroll',
							headers:{
								
								Accept: "application/json; charset=utf-8",
								"Content-type": "application/json; charset=utf-8"
							},
							success: function(enrollArray){
								
								var enrollList = $.parseJSON(enrollArray);
								
								$studentDetailsContainer = $('#student-details-div');
								$studentDetailsContainer.empty();
								
								$subjectListContainer = $('#subject-list-div');
								$subjectListContainer.empty();
								
								$groupListContainer = $('#group-list-div');
								$groupListContainer.empty();
								
								$requestContainer = $('#request-div');
								$requestContainer.empty();
								
								var tr = [];
								
								if(enrollList.length == 0){
									
									tr.push('<h1>Someone don' + "'" + 't want to leave yet. lol</h1>');
									$requestContainer.append($(tr.join('')));
								}
								else{
									
									tr.push('<table border="2" style= "background-color: #00FFFF; color: #000000; margin: 0 auto;" id="drop-table">');
									tr.push('<thead>');
									tr.push('<tr><th colspan="7">Student Details</th></tr>');
									tr.push('<tr>');
									tr.push('<th>No</th>');
									tr.push('<th>Id</th>');
									tr.push('<th>Name</th>');
									tr.push('<th>Group</th>');
									tr.push('<th>Subject Code</th>');
									tr.push('<th>Subject Name</th>');
				
									tr.push('</tr>	');
									tr.push('</thead>');
									tr.push('<tbody id="tbody">');	      
							      
								
									
									for(var i=0; i<enrollList.length;i++){
										
										//var classStudId = [enrollList[i].studId,enrollList[i].classId];	
										var classStudId = enrollList[i].studId + "%" + enrollList[i].classId;
										console.log(classStudId);
										
										tr.push('<tr>');
										tr.push('<td>' + (i+1) + '</td>');
										tr.push('<td>' + enrollList[i].studId + '</td>');
										tr.push('<td>' + enrollList[i].studName + '</td>');
										tr.push('<td>' + enrollList[i].groupId + '</td>');
										tr.push('<td>' + enrollList[i].subjCode + '</td>');
										tr.push('<td>' + enrollList[i].subjName + '</td>');
										tr.push('<td><button class="btn-approve" value="' + classStudId + '">Approve</button></td>');
										tr.push('</tr>');
									}
									
									tr.push('</tbody>');
									tr.push('</table>');
									
									$requestContainer.append($(tr.join('')));
									
								}
								
							}
						})
						
					});//close drop request
					
					/*-------approve drop request----------*/
					
					$(document).on('click','.btn-approve', function(){
						
						$button = $(this)
						var classStudId = $button.attr("value");
						
						var split = classStudId.split("%");			
						
						var studId = split[0];
						var classId = split[1];
			
						
						
						$.ajax({
							
							type:"POST",
							data:{
								action:"approve drop request",
								classId: classId,
								studId: studId
							},
							url:'Enroll',
							success: function(){
								
								window.location.reload(false); 
								
								/*
								console.log("i want delete this");
								$button.closest('tr').remove();
								return false;
								/* ------------tak jadi. tah apa tah
								//if($('#drop-table').find('tbody').children().length < 1){
								if ($('#drop-table tbody').children().length==0) {
									console.log("im empty");
									$('#drop-table').remove();
									//return false;
								}
								*/
								
								window.location.reload(false); 
							}
						})
						
					});
					
					
					
			/*******************************************ATTENDANCE******************************************************************/	
			
			
					
					$(document).on('click','#btn-attendance', function(){
						
						$('#student-details-div').empty();
						$('#group-list-div').empty();
						$('#subject-list-div').empty();
						
						$.ajax({
							
							type:'GET',
							data:{
								action: "for student details"
							},
							url:'Subject',
							success: function(subjectArray){
								
								var subjectList = $.parseJSON(subjectArray);
								
								
								
								$subjectListContainer = $('#subject-list-div');
								$subjectListContainer.empty();
								
								$requestContainer = $('#request-div');
								$requestContainer.empty();
								
								var option = [];
								
								option.push('<select id="att-subject-list" >');
								option.push('<option selected disable class="hideoption" >Please Select Subject</option>')
								for(var i=0; i<subjectList.length; i++){
									
									option.push('<option value="' +subjectList[i].subjCode+ '">' + subjectList[i].subjName + '</option>');
									
									
								}
								
								option.push('</select>');
								$subjectListContainer.append($(option.join('')));
							}
						})
						
					});
					
					$(document).on('change', '#att-subject-list', function(){
						
						var selectedSubject = $("#att-subject-list").find("option:selected").text();
						
						console.log(selectedSubject);
						
						$.ajax({
							
							type:'GET',
							data:{
								selectedSubject: selectedSubject,
								action: "Student details"
							},
							url:'Group',
							headers:{
								
								Accept: "application/json; charset=utf-8",
								"Content-type": "application/json; charset=utf-8"
							},
							success: function(groupArray){
								var groupList = $.parseJSON(groupArray);
								
								
								
								$groupListContainer = $('#group-list-div');
								var option=[];
								
								option.push('<select id="att-group-list">');
								option.push('<option selected disable class="hideoption" >Please Select Group</option>');
								
								for(var i=0; i<groupList.length; i++){
									
									option.push('<option>' + groupList[i].groupId + '</option>')
								}
								
								option.push('</select>');
								$groupListContainer.append($(option.join('')));
							}
						})
					});
					
					
					$(document).on('change', '#att-group-list', function(){
						
						var subjCode = $("#att-subject-list").find("option:selected").val();
						var subjName = $("#att-subject-list").find("option:selected").text();
						var selectedGroup = $("#att-group-list").find("option:selected").text();
						
						$('#drop-request-div').hide();
						
						$.ajax({
							
							type:'GET',
							data:{
								action:"student attendance",
								subjCode: subjCode,
								subjName: subjName,
								selectedGroup: selectedGroup
							},
							url:'Attendance',
							headers:{
								
								Accept: "application/json; charset=utf-8",
								"Content-type": "application/json; charset=utf-8"
							},
							success: function(attdArray){
								
								
								var attdList = $.parseJSON(attdArray);
								
								
								
								$attdDetailsContainer = $('#attd-details-div');
								$attdDetailsContainer.empty();
								var tr=[];
								
								tr.push('<table border="2" style= "background-color: #00FFFF; color: #000000; margin: 0 auto;" >');
								tr.push('<thead>');
								tr.push('<tr><th colspan="4">Student Details</th></tr>');
								tr.push('<tr>');
								tr.push('<th>No</th>');
								tr.push('<th>Id</th>');
								tr.push('<th>Name</th>');
								tr.push('<th>Absence (times)</th>');
								tr.push('</tr>	');
								tr.push('</thead>');
								tr.push('<tbody>');	      
						      
							
								
								for(var i=0; i<attdList.length;i++){
									
									console.log("total day class" + attdList[i].totalDayClass);
									console.log("total present" + attdList[i].present);
									
									
									tr.push('<tr>');
									tr.push('<td align="center">' + (i+1) + '</td>');
									tr.push('<td>' + attdList[i].studId + '</td>');
									tr.push('<td>' + attdList[i].studName + '</td>');
									tr.push('<td align="center"> ' + (attdList[i].totalDayClass - attdList[i].present) + ' </td>');
									tr.push('</tr>');
								}
								
								tr.push('</tbody>');
								tr.push('</table>');
								
								$attdDetailsContainer.append($(tr.join('')));
							}
						})
					});//close selectiing group
					/*
					$(document).on('click', '#upload-btn', function(){
						
						alert("hey");
						
						var subCode = $("#upload-sub-code").val();
						
						$.ajax({
							
							type: 'POST',
							data:{
								subCode: subCode
							},
							url:'upload',
							success: function(){
								
								
							}
							
						
						})
						
							
					});
					*/
					
				});
			}//close else
			
		}// close success call
		
	})//close ajax
}) //close ajax

</script>
</head>
<body>
<center>
	<fieldset>
		<legend>UPLOAD STUDENT FILE</legend>
		<form method="POST" action="upload" enctype="multipart/form-data" >
		<p>&nbsp;</p>
		File :
            <input type="file" name="file" id="file" /> <br>
            <br>
		Subject Code : 
            <input type = "text" name="upload-sub-code" /><br>
            <br>
            <input type="submit" value="Upload" name="upload" id="upload-btn" />
        </form>
	</fieldset>
	<p>&nbsp;</p>
	<fieldset id="student-field"><legend align="center">OPTIONS</legend>

		<input type="submit" id="btn-student-details" value="Display Student Details">

		<input type="submit" id="btn-drop-request" value="Drop Request">

		<input type="submit" id="btn-attendance" value="Attendance">
		
	</fieldset>
	
	<p>&nbsp</p>
	
	<div id="subject-list-div"></div>
	<div id="group-list-div"></div>
	<div id="student-details-div"></div>
	<div id="attd-details-div"></div>
	<div id="drop-request-div"></div>
	<div id="attendance-div"></div>
	
	<p>&nbsp</p>
	
	<div id="request-div"></div>
	
	<p>&nbsp</p>
</center>	
	<form action="LecturerHome.jsp" align="center">
		<input type="submit" value="Back">
	</form>
	
</body>
</html>