<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Points</title>
<style>

.hideoption{
	display:none;
	visibility:hidden;
	height:0;
	font-size:0;
}
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

#give-point-field{
  border: 4px solid rgb(255,232,57);
  width: 800px;
  margin:auto;
}


#list-point-field{
  border: 4px solid rgb(255,232,57);
  width: 600px;
  margin:auto;
}
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
	
			$(document).ready (function(){
				
				$.ajax({
					
					type:'GET',
					data:{
						action:"add point"
					},
					url:'Class',
					headers:{
							
						Accept: "application/json; charset=utf-8",
						"Content-type": "application/json; charset=utf-8"
					},
					success: function(classArray){
						
						
						/*-------list subject for give point and list student points----------*/
						
						var subjectList = $.parseJSON(classArray);
						
						$subjectListContainer = $('#subject-list');
						$subjectListContainer.empty();
						
						$pointSubjectList = $('#point-subject-list');
						$pointSubjectList.empty();
						
						var option = [];
						var pointOption = [];
						
						option.push('<option selected disable class="hideoption" >Subject</option>');
						pointOption.push('<option selected disable class="hideoption" >Subject</option>');
						
						for(var i=0; i<subjectList.length; i++){
							
							option.push('<option value = "' + subjectList[i].subjCode + '">' + subjectList[i].subjName + '</option>');
							pointOption.push('<option value = "' + subjectList[i].subjCode + '">' + subjectList[i].subjName + '</option>');
						}
						
						$subjectListContainer.append($(option.join('')));
						$pointSubjectList.append($(pointOption.join('')));
		
						
					}
					
				});//close listing subject
				
				$(document).on('change','#subject-list', function(){
					
					var selectedSubject = $("#subject-list").find("option:selected").text();
					
					//console.log(selectedSubject);
					
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
							console.log(groupArray);
							var groupList = $.parseJSON(groupArray);
							
							$groupListContainer = $('#group-list');
							var option=[];
							
							//option.push('<select id="group-list">');
							//option.push('<option selected disable class="hideoption" >Please Select Group</option>');
							
							for(var i=0; i<groupList.length; i++){
								
								option.push('<option>' + groupList[i].groupId + '</option>')
							}
							
							//option.push('</select>');
							$groupListContainer.append($(option.join('')));
						}
					})
					
					
				});//close listing group
				
				$(document).on('change','#group-list', function(){
					
					var selectedGroup = $("#group-list").find("option:selected").text();
					var selectedSubject = $("#subject-list").find("option:selected").text();
					
					$.ajax({
						
						type:'GET',
						data:{
							
							selectedGroup: selectedGroup,
							selectedSubject: selectedSubject,
							action:"give point"
							
						},
						url:'Reward',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(data){
							
							var split = data.split("%");
							
							var studentNameList = $.parseJSON(split[0]);
							var rewardList = $.parseJSON(split[1]);
							var fixRewardList = $.parseJSON(split[2]);
							
							$studentContainer = $('#student-list');
							//$studentContainer.empty();
							
							var studentOption = [];
							
							for(var i=0; i<studentNameList.length; i++){
								
								studentOption.push('<option value="' + studentNameList[i].studId + '">' + studentNameList[i].studName + '</option>');
							}
							
							$studentContainer.append($(studentOption.join('')));
							
							
							
							$rewardContainer = $('#reward-list');
							//$rewardContainer.empty();
							
							console.log(fixRewardList);
							
							var rewardOption = [];
		
							for(var i=0; i<rewardList.length; i++){
								
								rewardOption.push('<option value="'+rewardList[i].rewardPoint+'">' + rewardList[i].rewardType + '</option>');
							}
							
							for(var i=0; i<fixRewardList.length; i++){
								
								rewardOption.push('<option value="'+ fixRewardList[i].rewardPoint +'">' + fixRewardList[i].rewardType + '</option>');
							}
							
							$rewardContainer.append($(rewardOption.join('')));
						}
					})
					
				});//close listing name and reward
				
				$(document).on('click','#btn-give-point', function(){
					
				
					
					var selectedGroup = $("#group-list").find("option:selected").text();
					var selectedSubjectCode = $("#subject-list").find("option:selected").val();
					var selectedSubject = $("#subject-list").find("option:selected").text();
					var selectedStudent = $("#student-list").find("option:selected").val();
					var selectedReward = $("#reward-list").find("option:selected").text();
					var selectedRewardPoint = $("#reward-list").find("option:selected").val();
					
					console.log(selectedRewardPoint);
					
					$.ajax({
						
						type:'POST',
						data:{
							
							action:"give reward",
							subjCode: selectedSubjectCode,
							subjName: selectedSubject,
							groupId: selectedGroup,
							studId: selectedStudent,
							rewardType: selectedReward,
							rewardPoint: selectedRewardPoint
						},
						url:'Reward',
						succes: function(){
							
							alert("You have given marks to the student");
						}
					})
				});
				
				/*----------list student points---------*/
				
				$(document).on('change','#point-subject-list', function(){
					
					var selectedSubject = $("#point-subject-list").find("option:selected").text();
					
					//console.log(selectedSubject);
					
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
							console.log(groupArray);
							var groupList = $.parseJSON(groupArray);
							
							$groupListContainer = $('#point-group-list');
							var option=[];
							
							//option.push('<select id="group-list">');
							//option.push('<option selected disable class="hideoption" >Please Select Group</option>');
							
							for(var i=0; i<groupList.length; i++){
								
								option.push('<option>' + groupList[i].groupId + '</option>')
							}
							
							//option.push('</select>');
							$groupListContainer.append($(option.join('')));
						}
					})
					
					
				});//close listing group
				
				$(document).on('click', '#btn-point-list', function(){
					
					var selectedSubject = $("#point-subject-list").find("option:selected").val();
					var selectedGroup = $("#point-group-list").find("option:selected").text();
					
					$.ajax({
						
						type:'GET',
						data:{
							subjCode: selectedSubject,
							groupId: selectedGroup,
							action:"student point list"
						},
						url:'Enroll',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(enrollArray){
							
							console.log(enrollArray);
							
							var enrollList = $.parseJSON(enrollArray);
							
							$pointListContainer = $('#student-point-list');
							$pointListContainer.empty();
							
							var tr= [];
							
							tr.push('<table border="2" style= "background-color: #00FFFF; color: #000000; margin: 0 auto;" >');
							tr.push('<thead>');
							tr.push('<tr><th colspan="5">Student Details</th></tr>');		
							tr.push('<tr><th>No</th>');
							tr.push('<th>Student ID</th>');
							tr.push('<th>Student Name</th>');
							tr.push('<th>Total Points</th></tr>');
							tr.push('</thead>');
							tr.push('<tbody>');
							
							for(var i=0; i<enrollList.length; i++){
								
								tr.push('<tr>');
								tr.push('<td>' + (i+1) +'</td>');
								tr.push('<td>' + enrollList[i].studId +'</td>');
								tr.push('<td>'+ enrollList[i].studName +'</td>');
								tr.push('<td align="center">'+ enrollList[i].points +'</td>');
								tr.push('</tr>');
								
							}
							tr.push('</tbody>');
							
							$pointListContainer.append($(tr.join('')));
						}
					})
				});
				
			});
			}//close else
			
		}// close success call
	
	})//close ajax
})
	/*---drop request-------*/
	
	
		</script>
</head>
<script type="text/javascript" src="jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript"></script>
<body>
	<fieldset id="give-point-field"><legend align="center"><h3>Reward Points</h3></legend>
		<form align="center" >    
			<select name="subject" id="subject-list">
			</select>
			<select id="group-list">
				<option selected disable class="hideoption" >Please Select Group</option>
			</select>
			<select name="student" id="student-list">
				<option selected disable class="hideoption" >Student</option>
			</select>
			<select name="reward" id="reward-list">
				<option selected disable class="hideoption" >Reward Type</option>
			</select>	
			
			<button type="submit" class="button" name="submit" id="btn-give-point">Give Point</button>

    	 </form>
    </fieldset>
    <p>&nbsp</p>
    <fieldset id="list-point-field">
    	<legend align="center"><h3>Student Points List</h3></legend>
    	<center>	
			<select id="point-subject-list"></select>
			<select id="point-group-list">
				<option selected disable class="hideoption" >Please Select Group</option>
			</select>
			<button id="btn-point-list">Show Student Point</button>	
		</center>
	</fieldset>
	<p>&nbsp</p>
	<div id="student-point-list"></div>
    
    
    <p>&nbsp</p>
			<form method="link" action="LecturerHome.jsp" align="center">
				<input type="submit" value="Back">
			</form>
			
			<!-- Kena redirect ke page yg display points student -->
</body>
</html>