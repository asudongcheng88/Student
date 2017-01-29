<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lecturer's Dashboard</title>
<link rel="stylesheet" href="slides.css">
<link rel="stylesheet" href="css/dashboard/statusbox.css">
<link rel="stylesheet" href="css/dashboard/subjectDropdown.css">
<link rel="stylesheet" href="css/dashboard/studentAchievement.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style>

	#student-active-div, #reminder-div, #attd-alert-div, #pic-div, #top-div{
		border-style: solid;
    	border-width: medium;
	}
	
	#student-active-div{
		width: 500px;
		height: 250px;
	}
	
	#reminder-div{
		width: 500px;
		height: 225px;
		padding: 10px;
		
	}
	
	#attd-alert-div{
		width: 700px;
		height: 200px;
		padding: 10px;
	}
	
	#katun-div{
		width: 300px;
		height: 200px;
		padding: 10px;
	}
	
	#top-div{
		width: 1020px;
		height: 220px;
	}
	.hideoption{
	display:none;
	visibility:hidden;
	height:o;
	font-size::0;	
}
body { 
	background-image: url('./image/books2.jpg'); 
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}

	
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
					
					$.ajax({
						
						type:'GET',
						data:{
							action: "reminder drop request"
						},
						url:'Enroll',
						success: function(gotRequest){
							
							var request = $.parseJSON(gotRequest);
							
							$rowdiv = $('#attd-alert-div');
							$rowdiv.empty();
							
							var tr = [];
							
							if(request == true){
										
								
								tr.push('<div class="container"><div class="row"><div class="col-md-12"><div class="update-nag">' +
								'<div class="update-split update-danger"><i class="glyphicon glyphicon-warning-sign"></i></div>' +
								'<div class="update-text">There is student make a leave request <a href="LecStudOptions.jsp">here</a>'+
								'</div></div></div></div></div>');
								
								$rowdiv.append($(tr.join('')));
								
								
							}
							
							
							
							/*------attendance alert---------*/
							
							
							$.ajax({
								
								type:'GET',
								data:{
									action: "student attendance alert"
								},
								url:'Attendance',
								headers:{
									
									Accept: "application/json; charset=utf-8",
									"Content-type": "application/json; charset=utf-8"
								},
								
								success: function(attdAlertArray){
									console.log(attdAlertArray);
									var attdAlertList = $.parseJSON(attdAlertArray);
									
									//$attdAlert = $('#attd-alert-div');
									//$attdAlert.empty();
									var td = [];
									if(attdAlertList.length !== 0){
										
										//$attdAlert.append('<p>All of your student have absence count below of 3</p>')
										for(var i=0; i<attdAlertList.length; i++){
											td.push('<div class="container"><div class="row"><div class="col-md-12"><div class="update-nag">' +
													'<div class="update-split update-danger"><i class="glyphicon glyphicon-warning-sign"></i></div>' +
													'<div class="update-text"><strong>' + attdAlertList[i].studName + ' </strong>from group<strong> ' +attdAlertList[i].groupId+ ' ('
													+ attdAlertList[i].subjCode +')</strong> has absent <strong>' + attdAlertList[i].absence + ' times</strong>'+
													'</div></div></div></div></div>');
										}
										$rowdiv.append($(td.join('')));
										
									}
									
								}//close attd alert success
								
							}) //close attd alert ajax
							
							
							/*-----subject list----------*/
							
							
							$.ajax({
							
								type:'GET',
								data:{
									action: "for student details",
								},
								url:'Subject',
								headers:{
										
									Accept: "application/json; charset=utf-8",
									"Content-type": "application/json; charset=utf-8"
								},
								success: function(subjectArray){
									
									console.log(subjectArray);
									var subjectList = $.parseJSON(subjectArray);
									
									$studActiveDiv = $('#student-active-div');
									$topDiv = $('#top-stud-list');
									
									var li1 = [];
									var li2 = [];
									
									li1.push('<select id="top-subj-list">');
									li1.push('<option class="hideoption">Subject</option>');
									li2.push('<select id="active-subj-list">');
									li2.push('<option class="hideoption">Subject</option>');
									
									for(var i=0; i<subjectList.length; i++){
										
										li1.push('<option value="'+ subjectList[i].subjCode +'">' + subjectList[i].subjName + '</option>');
										li2.push('<option value="'+ subjectList[i].subjCode +'">' + subjectList[i].subjName + '</option>');
										
									}
									
									li1.push('</select>');
									li2.push('</select>');
									
									$studActiveDiv.append($(li2.join('')));
									$topDiv.append($(li1.join('')));
									
								}
								
							})//close ajax

							
						}//close drop request success
					})//close drop request ajax
					
					
					
					/*---student achievement------*/
					
					
					$(document).on('change','#top-subj-list', function(){
						
						var selectedSubject = $("#top-subj-list").find("option:selected").text();

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
								
								var option = [];
								
								$topDiv = $('#top-group-list');
								$topDiv.empty();
								
								option.push('<select id="top-group">');
								option.push('<option class="hideoption">Group</option>');
								
								for(var i=0; i<groupList.length; i++){
									
									option.push('<option>' + groupList[i].groupId + '</option>')
									
								}
								
								option.push('</select>');
								
								$topDiv.append($(option.join('')));
								
							}
							
						})
						
						
					});
					
						
					$(document).on('change','#top-group', function(){
						
						var selectedSubject = $("#top-subj-list").find("option:selected").val();
						var selectedGroup = $("#top-group").find("option:selected").text();
						
						showStudentAchievement(selectedSubject, selectedGroup);
						
						$('#level-p').hide();
						$('#level-div').show();
						
					});
				
					function showStudentAchievement(code, group){
						
						$.ajax({
							
							type:'GET',
							data:{
								action: "student point list",
								subjCode: code,
								groupId: group
							},
							url:'Status',
							headers:{
									
								Accept: "application/json; charset=utf-8",
								"Content-type": "application/json; charset=utf-8"
							},
							success: function(studentArray){
								
								var studList = $.parseJSON(studentArray);
								
								var countExp = 0;
								var countPro = 0;
								var countCom = 0;
								var countBeg = 0;
								var countNov = 0;
								
								$expDiv = $('#exp-div');
								$proDiv = $('#pro-div');
								$comDiv = $('#com-div');
								$begDiv = $('#beg-div');
								$novDiv = $('#nov-div');
								
								$expDiv.empty();
								$proDiv.empty();
								$comDiv.empty();
								$begDiv.empty();
								$novDiv.empty();
								
								var exp = [];
								var pro = [];
								var com = [];
								var beg = [];
								var nov = [];
								
								for(var i=0; i<studList.length; i++){
									
									var points = studList[i].points;

									if(points >= 75 && points <= 100){
										
										countExp++;
										
									}else if(points >= 60 && points <= 74){
										
										countPro++;
										
									}else if(points >= 47 && points <= 59){
										
										countCom++;
										
									}else if(points >= 40 && points <= 46){
										
										countBeg++;
										
									}else{
										
										countNov++;
										
									}
								}
								
								$expDiv.append('<h1 class="panel-title text-center">'+ countExp +'</h1>');
								$proDiv.append('<h1 class="panel-title text-center">'+ countPro +'</h1>');
								$comDiv.append('<h1 class="panel-title text-center">'+ countCom +'</h1>');
								$begDiv.append('<h1 class="panel-title text-center">'+ countBeg +'</h1>');
								$novDiv.append('<h1 class="panel-title text-center">'+ countNov +'</h1>');
								
								
								
								
							}//close student point success
							
						})//close student point ajax
						
						
					}//close getSubjectList function
					
					$(document).on('click','#sign-out-btn', function(){
						alert("You will be sign out");
						$.ajax({
							
							type:'GET',
							data:{
								action: "log out"
								
							},
							url:'Status',
							headers:{
									
								Accept: "application/json; charset=utf-8",
								"Content-type": "application/json; charset=utf-8"
							},
							success: function(result){
								var lecId = result;
								console.log(typeof lecId);
								if(lecId === "null"){
									
									window.location = "LecLogin.html";
								}
								
							}
						
						})
				
						
					})
					
				}//close else
				
			}// close success call
		
		})//close ajax
		
		/*---drop request-------*/
});
</script>
</head>

<body>
<nav class="navbar navbar-inverse">
	<div class="container-fluid">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		
		<a class="navbar-brand">CLASSMOTVR</a>
    </div>
    
    <div class="collapse navbar-collapse" id="myNavbar">
		<ul class="nav navbar-nav">
			<li class="active"><a href="LecturerHome.jsp">Home</a></li>
			<li><a href="LecRegClass.jsp">Class</a></li>
			<li><a href="LecStudOptions.jsp">Students</a></li>
			<li><a href="LecAddPoints.jsp">Points</a></li>
			<li><a href="LecAddReward.jsp">Rewards</a></li>
		</ul>
		
		<ul class="nav navbar-nav navbar-right">
			<li id="sign-out-btn"><a><span class="glyphicon glyphicon-log-out"></span>Sign Out</a></li>
		</ul>
    </div>
	</div>
</nav>
<center>
<table>
	<tr>
	
		<td>
			<fieldset>
				<center>
					<legend><h3><strong>Notification</strong></h3></legend>
				</center>
				<div id="attd-alert-div"></div>
				
			</fieldset>
		</td>
	</tr>

	<!-- 
	<tr>
		<td>
			<fieldset>
				<center>
					<legend><h3><strong>Active Student This Week</strong></h3></legend>
				</center>
				<div id="student-active-div"> 
					
				
				</div>
			</fieldset>

		</td>
	</tr>
	 -->
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2">
			<fieldset>
				<center>
					<legend><h3><strong>Student Achievement</strong></h3></legend>
				</center>
				<div id="top-div">
					<center>
						<h1 id="level-p">Check your student level here</h1>
					
						<table>
							<tr>
								<td><div id="top-stud-list"></div></td>
								<td><div id="top-group-list"></div></td>
							</tr>
						</table>
					</center>
		            <!-- <div id="top-stud-list"></div> -->
		            	<br/><br/>
		            <div id="level-div" style="display:none">
				        <div class="col-xs-6 col-md-3" style="width:202px">
					   		<div class="panel status panel-danger">
					    		<div class="panel-heading">
					   				<div id="exp-div"></div>
					                    	
					         	</div>
					         	<div class="panel-body text-center">                        
					           		<h4><strong>Expertise</strong></h4>
					          	</div>
					      	</div>
				      	</div> 
				        	        
						<div class="col-xs-6 col-md-3" style="width:202px">
							<div class="panel status panel-warning">
								<div class="panel-heading">
									<div id="pro-div"></div>
						  		</div>
						     	<div class="panel-body text-center">                        
						      		<h4><strong>Proficiency</strong></h4>
						       	</div>
						  	</div>
						</div>
						        
						<div class="col-xs-6 col-md-3" style="width:202px"> 
							<div class="panel status panel-success">
						    	<div class="panel-heading">
						     		<div id="com-div"></div>
						       	</div>
						       	<div class="panel-body text-center">                        
						   			<h4><strong>Competence</strong></h4>
						     	</div>
						 	 </div>
						</div>
						        
						<div class="col-xs-6 col-md-3" style="width:202px">
							<div class="panel status panel-info">
								<div class="panel-heading">
						   			<div id="beg-div"></div>
						    	</div>
						 		<div class="panel-body text-center">                        
						   			<h4><strong>Beginner</strong></h4>
						 		</div>
							</div>
						</div>
						<div class="col-xs-6 col-md-3" style="width:100px">
							<div class="panel status panel-default">
								<div class="panel-heading">
						  			<div id="nov-div"></div>
							   	</div>
								<div class="panel-body text-center">                        
						    		<h4><strong>Novice</strong></h4>
						       	</div>
						 	</div>
						</div>
					</div>
				</div>
			</fieldset>
	<!--<tr>
		<td>
			<fieldset>
				<center>
					<legend>Notification</legend>
				</center>
				
				<div id="reminder-div">
				
					<div class="container">
					    <div class="row">
    
						</div>
					</div>
				</div>
				
			</fieldset>
		</td>
		<td>&nbsp;</td>
		<td>
			
		</td>
	</tr>
	-->

</table>
</center>

</script>
</body>
</html>
