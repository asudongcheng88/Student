<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display Reward</title>
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
	background: url(image/rewardpoints.jpg) no-repeat center center fixed; 
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}
#reward-field{
  		border: 4px solid rgb(255,232,57);
  		width: 500px;
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
<script type="text/javascript">

				$(document).ready(function(){
		
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
				
				$(document).on('click', '#btn-view-reward', function(){
					
					var selectedSubject = $("#subject-name").find("option:selected").val();
					
					$.ajax({
						
						type:'GET',
						data:{
							action: "view reward for student",
							subjCode:selectedSubject
						},
						url:'Reward',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(rewardArray){
							
							var rewardList = $.parseJSON(rewardArray);
							
							console.log(rewardList);
							
							$rewardContainer = $('#reward-list-div');
							$rewardContainer.empty();
							
							var tr=[];
							
							tr.push('<table border="2" style= "background-color: #00FFFF; color: #000000; margin: 0 auto;" >');
							tr.push('<thead>');
							tr.push('<tr><th colspan="3">Reward</th></tr>');
							tr.push('<tr>');
							tr.push('<th>No</th>');
							tr.push('<th>Reward Type</th>');
							tr.push('<th>Reward Value</th>');
							tr.push('</tr>');
							tr.push('</thead>');
							tr.push('<tbody>');
							
							
							for(var i=0; i<rewardList.length; i++){
								tr.push('<tr>');
								tr.push('<td>' + (i+1) + '</td>');
								tr.push('<td>' + rewardList[i].rewardType + '</td>');
								tr.push('<td>' + rewardList[i].rewardPoint + '</td>');
								tr.push('<tr>');
							}
							
							tr.push('</tbody>');
							tr.push('</table>');
							$rewardContainer.append($(tr.join('')));
							
						}
					})
				});//close view rewards
				
			});
		
</script>
</head>

<body>
	<fieldset id="reward-field"><legend align="center"><h2>Reward</h2></legend>
		
		<center>
			<select id="subject-name">
				<option class="hideoption" >Subject List</option>
			</select><br>
			<br>
			<a class="button button3" id="btn-view-reward" >View Rewards</a>
		</center>

		<p>&nbsp</p>
		<div id="reward-list-div"></div>
	
	</fieldset>
	
	<br>
	
	<form method="LINK" action="StudentHome.jsp" align="center">
	<input type="submit" value="Back">
	</form>
	
</body>
</html>