<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student's Dashboard</title>
<link rel="stylesheet" href="slides.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style>
	#student-active-div, #reminder-div, #pic-div{
		border-style: solid;
    	border-width: medium;
	}
	
	#student-active-div{
		width: 500px;
		height: 250px;
	}
	
	#reminder-div{
		width: 600px;
		height: 250px;
		padding: 10px;
		
	}
	
	#attd-alert-div{
		width: 500px;
		height: 250px;
		padding: 10px;
	}
	
	.work-progres {
	    box-shadow: 0px 0px 2px 1px rgba(0,0,0,0.15);
	    padding: 1em 1em;
	    background: #fff;
	}
	.chit-chat-heading {
	    font-size: 1.2em;
	    font-weight: 700;
	    color: #5F5D5D;
	    text-transform: uppercase;
	   	font-family: 'Carrois Gothic', sans-serif;
	}
	.chit-chat-layer1{
		width: 50em;
	}
	.label{
		font-size: 90%;
	}

	body { 
		background-image: url('./image/present.jpg'); 
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
						action: "attendance record"
					},
					url:'Attendance',
					headers:{
						
						Accept: "application/json; charset=utf-8",
						"Content-type": "application/json; charset=utf-8"
					},
					
					success: function(attdRecordArray){
						
						var attdRecord = $.parseJSON(attdRecordArray);
						
						$attdRecordDiv = $('.table-responsive');
						$attdRecordDiv.empty();
						
						if(attdRecord.length == 0){
							
							$attdRecordDiv.append('<p>You did not registered any subject yet. Register your subject <a href="StudRegClass.jsp">here</a></p>');
							
						}else{
							
							var table = [];
							
							table.push('<table class="table table-hover"><thead><tr><th><h4>Subject</h4></th><th><h4>Code</h4></th><th><h4>Absent</h4></th></tr></thead><tbody>');
							
							for(var i=0; i<attdRecord.length; i++){
								
								table.push('<tbody><tr>');
								table.push('<td>' + attdRecord[i].subjName + '</td>');
								table.push('<td>' + attdRecord[i].subjCode + '</td>');
								
								if(attdRecord[i].absence >= 3){
									
									table.push('<td><span class="label label-danger">'+ attdRecord[i].absence +' times</span></td>');
									
								}else if(attdRecord[i].absence >= 1 && attdRecord[i].absence <= 2){
									
									table.push('<td><span class="label label-warning">'+ attdRecord[i].absence +' times</span></td>');
									
								}else if(attdRecord[i].absence == 0){
									
									table.push('<td><span class="label label-success">'+ attdRecord[i].absence +' times</span></td>');
									
								}
								
								table.push('</tr>');
		
							}//close for loop
		
							table.push('</tbody></table>');
							
							$attdRecordDiv.append($(table.join('')));
							
							
							
						}
						
					}
					
				})//close ajax
				
				
				$.ajax({
					
					type:'GET',
					data:{
						action: "get notification"
					},
					url:'Notification',
					headers:{
						
						Accept: "application/json; charset=utf-8",
						"Content-type": "application/json; charset=utf-8"
					},
					
					success: function(notification){
						
						
						var notiList = $.parseJSON(notification);
						
						if(notiList.length === 0){
							
							$notiDiv = $('#reminder-div');
							$notiDiv.empty();
							$notiDiv.append('<p><strong>You have no notification</strong></p>');
							
						}else{
							
							var sorted = notiList.slice(0);
							
							var today = new Date();
							var dd = today.getDate();
							var mm = today.getMonth()+1; //January is 0!
		/*
							var yyyy = today.getFullYear();
							if(dd<10){
							    dd='0'+dd;
							} 
							if(mm<10){
							    mm='0'+mm;
							} 
							var todayDate = dd+'/'+mm+'/'+yyyy;
							
		*/					
							var li = [];
							
							$notiDiv = $('#reminder-div');
							$notiDiv.empty();
							
							li.push('<ul>')
							
							for(var i=0; i< notiList.length; i++){
								
								var notiDate = notiList[i].notiDate.split("/");
								var tdd = notiDate[1];
								
								//var days =  Math.floor(( Date.parse(todayDate) - Date.parse(notiList[i].notiDate) ) / 86400000);
								var days = +dd - tdd;
		
								
								if(days == 0){
									
									//this one is for today
									
									li.push('<li>You have receive <strong>' + notiList[i].reward +'</strong> reward for subject <strong>'
											+ notiList[i].subjName + ' </strong>today </li>');
									
								}else{
									
									//this one is for yesterday and so on
									
									li.push('<li>You have receive <strong>' + notiList[i].reward +'</strong> reward for subject <strong>'
											+ notiList[i].subjName + ' ' + days + ' </strong>day ago </li>');
								}
		
							}
							
							li.push('</ul>');
							
							$notiDiv.append($(li.join('')));
							
						}
							
					}
			
				})//close ajax
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
				})
		
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
			<li class="active"><a href="StudentHome.jsp">Home</a></li>
			<li><a href="StudRegClass.jsp">Class</a></li>
			<li><a href="StudDispDetails.jsp">Students</a></li>
			<li><a href="StudDispReward.jsp">Rewards</a></li>
			<li><a href="StudStatus.jsp">Leaderboard</a></li>
		</ul>
		
		<ul class="nav navbar-nav navbar-right">
			<li><a href="Logout.jsp"><span class="glyphicon glyphicon-log-out"></span>Sign Out</a></li>
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
				
				<div id="reminder-div" >
				
					<div class="container">
					    <div class="row">
    
						</div>
					</div>
				</div>
				
			</fieldset>
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td>
			<fieldset>
				<center>
					<legend><h3><strong>Attendance Record</strong></h3></legend>
				</center>
				<div id="attd-alert-div">
				<div class="chit-chat-layer1">
					<div class="col-md-6" style="width: 530px;">
						<div class="work-progres">
					                            
					        <div class="table-responsive"></div>
					 	</div>
					</div>
				</div>
				</div>
			</fieldset>
		</td>
	</tr>
	<!--  
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="3">
			<fieldset>
			<center>
				<legend><h3><strong>Active student this week</strong></h3></legend>
			</center>
				<div id="student-active-div"> </div>
			</fieldset>	
		</td>
	<tr><td>&nbsp;</td></tr>
	
	<tr>
		
	</tr>
	-->
	</table>
	</center>

</script>
</body>
</html>
