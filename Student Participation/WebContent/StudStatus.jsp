<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Status & Leaderboard</title>
<link rel="stylesheet" href="css/reward/reward-achievement.css">
<style>
	.row{
    	width:260px;
    	}
    
    html { 
		background: url(image/Education.jpg) no-repeat center center fixed; 
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
</style>
<script type="text/javascript" src="jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript">
			$(document).ready(function(){
				
				$.ajax({
					
					type:'GET',
					data:{
						action:"subject list"
					},
					url:'Class',
					headers:{
							
						Accept: "application/json; charset=utf-8",
						"Content-type": "application/json; charset=utf-8"
					},
					success: function(classArray){
						
						console.log(classArray);
						
						/*-------list subject for give point and list student points----------*/
						
						var subjectList = $.parseJSON(classArray);
						
						$subjectListContainer = $('#subject-list');
						$subjectListContainer.empty();
						
						var option = [];
						
						option.push('<option selected disable class="hideoption" >Subject</option>');
						
						for(var i=0; i<subjectList.length; i++){
							
							option.push('<option value = "' + subjectList[i].subjCode + '">' + subjectList[i].subjName + '</option>');
						}
						
						$subjectListContainer.append($(option.join('')));
						
					}
				})
				
				$(document).on('change','#subject-list', function(){
					
					var selectedSubject = $("#subject-list").find("option:selected").text();
					
					console.log("this is selected subject "+selectedSubject);
					
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
				
				
				$(document).on('click','#btn-show-point', function(){
					
					var selectedSubject = $("#subject-list").find("option:selected").val();
					var selectedGroup = $("#group-list").find("option:selected").text();
						
					$.ajax({
						
						type:'GET',
						data:{
							action: "student point list",
							subjCode: selectedSubject,
							groupId: selectedGroup
						},
						url:'Status',
						headers:{
								
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(bigList){
							
							var studentList = $.parseJSON(bigList);
							
							
							var exp = [];
							var pro = [];
							var com = [];
							var beg = [];
							var nov = [];
							
							$expDiv = $('#exp-div');
							$proDiv = $('#pro-div');
							$comDiv = $('#com-div');
							$begDiv = $('#beg-div');
							$novDiv = $('#nov-div');
							
							var countExp = 0;
							var countPro = 0;
							var countCom = 0;
							var countBeg = 0;
							var countNov = 0;
							
							
							
							for(var i=0; i<studentList.length; i++){
								
								var points = studentList[i].points;
		
								
								if(points >= 75 && points <= 100){
									
									exp.push('<p>' + studentList[i].studName + '</p>');
									
									countExp++;
									
									//document.getElementById("exp-text").style.display = "none";
									
								}else if(points >= 60 && points <= 74){
									
									pro.push('<p>' + studentList[i].studName + '</p>');	
									
									countPro++;
									
									//document.getElementById("pro-text").style.display = "none";
									
								}else if(points >= 47 && points <= 59){
									
									com.push('<p>' + studentList[i].studName + '</p>');
									
									countCom++;
									
									//document.getElementById("com-text").style.display = "none";
									
								}else if(points >= 40 && points <= 46){
									
									beg.push('<p>' + studentList[i].studName + '</p>');
									
									countBeg++;
									
									//document.getElementById("beg-text").style.display = "none";
									
								}else{
									
									nov.push('<p>' + studentList[i].studName + '</p>');
									
									countNov++;
									
									//document.getElementById("nov-text").style.display = "none";
									
								}
								
								
							}
							
							
							if(countExp !== 0){
								
								$expDiv.empty();
								
							}
							if(countPro !== 0){
								
								$proDiv.empty();
								
							}if(countCom !== 0){
								
								$comDiv.empty();
								
							}if(countBeg !== 0){
								
								$begDiv.empty();
								
							}
							if(countNov !== 0){
								
								$novDiv.empty();
								
							}
							
							$expDiv.append($(exp.join('')));
							$proDiv.append($(pro.join('')));
							$comDiv.append($(com.join('')));
							$begDiv.append($(beg.join('')));
							$novDiv.append($(nov.join('')));
							
							
							/*
							var split = bigList.split("%");
							
							//for highest list
							
							var highestList = $.parseJSON(split[0]);
							
							if(highestList.length !== 0){
								
								console.log(highestList.length);
								
								console.log("highest list");
								console.log(highestList);
								
								$firstPlaceContainer = $('#exp-div');
								$firstPlaceContainer.empty();
								
								var first = [];
								
								for(var i=0; i<highestList.length; i++){
									//alert(highestList[i].studName);
									first.push('<p>' + highestList[i].studName + '</p>');
								}
								
								$firstPlaceContainer.append($(first.join('')));
								
							}else{
								
								third.push('<p>No one are here yet</p>');
								
							}
							
							
							//for second highest list
							
							var secondHighestList = $.parseJSON(split[1]);
							
							$secondPlaceContainer = $('#pro-div');
							$secondPlaceContainer.empty();
							
							var second = [];
							
							if(secondHighestList.length !== 0){
								
								console.log("second highest list");
		
								console.log(secondHighestList);
		
								for(var i=0; i<secondHighestList.length; i++){
								
									second.push('<p>' + secondHighestList[i].studName + '</p>');
								}
								
							}else{
								
								second.push('<p>No one are here yet</p>');
								
							}
							
							$secondPlaceContainer.append($(second.join('')));
							
							
							//for third highest list
							
							var thirdHighestList = $.parseJSON(split[2]);
							
							$thirdPlaceContainer = $('#com-div');
							$thirdPlaceContainer.empty();
							
							var third = [];
							
							if(thirdHighestList.length !== 0){
								
								console.log("third highest list");
		
								console.log(thirdHighestList);
								
								for(var i=0; i<thirdHighestList.length; i++){
									
									third.push('<p>' + thirdHighestList[i].studName + '</p>');
								}
								
							}else{
								
								third.push('<p>No one are here yet</p>');
								
							}
							$thirdPlaceContainer.append($(third.join('')));
						
							*/
							}
					
					})
					
				});
				
			});
			
</script>
</head>
<body>
	
	<p>&nbsp</p>
	
	<select id="subject-list"></select>
		<option selected disable class="hideoption">Select Subject</option>
		
	<select id="group-list">
		<option selected disable class="hideoption">Select Group</option>
	</select>
	
	<button id="btn-show-point">Show Points</button>
	
	<p>&nbsp</p>
	
	<div id="status-point">
		<div id="first-place"></div>
		<div id="second-place"></div>
		<div id="third-place"></div>
	</div>
	<div id="reward-achivement">
		<div class="container">
	<table>
	<tr>
	<td>
		<div class="row">
			<div class="col-xs-3">
				<div class="offer offer-success">
					<div class="shape">
						<div class="shape-text">
							Expertise							
						</div>
					</div>
					<div class="offer-content">	
						<div id="exp-div">				
							<p id="exp-text">
								<strong><h2>75-100 points</h2></strong>
							</p>
						</div>
					</div>
				</div>
			</div>	
        </div>	        
	</td>
	        
	<td>
		<div class="row">
			<div class="col-xs-3">
				<div class="offer offer-warning">
					<div class="shape">
						<div class="shape-text">
							Proficiency						
						</div>
					</div>						
					<div class="offer-content">
						<div id="pro-div">				
							<p id="pro-text">
								<strong><h2>60 - 74 points</h2></strong>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</td>
				
	<td>
		<div class="row">
			<div class="col-xs-3">
				<div class="offer offer-radius offer-danger">
					<div class="shape">
						<div class="shape-text">
							Competence						
						</div>
					</div>
					<div class="offer-content">
						<div id="com-div">				
							<p id="com-text">
								<strong><h2>47 - 59 points</h2></strong>
							</p>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</td>
	
	<td>
		<div class="row">				
			<div class="col-xs-3">
				<div class="offer offer-radius offer-primary">
					<div class="shape">
						<div class="shape-text">
							Beginner						
						</div>
					</div>
					<div class="offer-content">
						<div id="beg-div">				
							<p id="beg-text">
								<strong><h2>40 - 46 points</h2></strong>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</td>	
	
	<td>
		<div class="row">
			<div class="col-xs-3">
				<div class="offer offer-radius offer-info">
					<div class="shape">
						<div class="shape-text">
							Novice						
						</div>
					</div>
					<div class="offer-content">
						<div id="nov-div">				
							<p id="nov-text">
								<strong><h2>0 - 39 points</h2></strong>
							</p>
						</div>
					</div>					
				</div>
			</div>
		</div>
	</td>
	</tr>
	</table>		
	</div>
</div>
	

	<form method="link" action="StudentHome.jsp" align="center">
		<input type="submit" value="Back">
	</form>
</body>
</html>