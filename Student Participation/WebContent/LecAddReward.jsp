<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Reward</title>
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
	background: url(image/rewardbg.jpg) no-repeat center center fixed; 
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}

#show-reward-field{
  border: 4px solid rgb(255,232,57);
  width: 400px;
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
	
				$(document).ready(function(){
		
				$(document).on('click','#btn-add', function(){
					
					var rewardType = $('#reward-type').val();
					var rewardPoint = $('#reward-point').val();
					
					if(rewardPoint > 5 || rewardPoint < 0){
						
						alert("Reward point is from 1 - 5");
					
					}else{
						
						$.ajax({
							
							type:'POST',
							data:{
								
								action:"add reward",
								rewardType: rewardType,
								rewardPoint: rewardPoint
							},
							url:'Reward',
							success: function(result){
								
								var isExist = $.parseJSON(result);
								alert("hey");
								
								console.log(isExist);
								
								if(isExist == false){
									
									alert("The reward already in the list");
									
								}else{
									
									alert("Reward added");
								}
							}
						})
						
					}
			
					
				});//close insert reward
				
				
				$(document).on('click', '#btn-view-reward', function(){
					
					$.ajax({
						
						type:'GET',
						data:{
							action: "view reward"
						},
						url:'Reward',
						headers:{
							
							Accept: "application/json; charset=utf-8",
							"Content-type": "application/json; charset=utf-8"
						},
						success: function(rewardArray){
							
							var rewardList = $.parseJSON(rewardArray);
							
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
								tr.push('<tr align="center">');
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
			
			}//close else
			
		}// close success call
	
	})//close ajax
})
</script>
</head>

<body>
	<fieldset id="show-reward-field"><legend align="center"><h2>Reward</h2></legend>

		<center><button id="btn-add-reward" onclick="document.getElementById('id01').style.display='block'" style="width:auto; margin:auto">Add Reward</button>
		<a class="button button3" id="btn-view-reward" >View Rewards</a></center>

		<div id="id01" class="modal">
  
	  		<form class="modal-content animate">
	    
		    	<div class="imgcontainer">
			      <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
			      <img src="image/reward.jpg" alt="Avatar" class="avatar">
			    </div>
			
			    <div class="container">
			      <label><b>Type of Reward</b></label>
			      <input type="text" placeholder="Enter Element" name="name" id="reward-type" required>
			      
			      <label><b>Points</b></label>
			      <input type="text" placeholder="Enter Points" name="points" id="reward-point" required>
			      
			      <button type="submit" name="btn-add" id="btn-add" >Add</button>
			    </div>
			
			    <div class="container" style="background-color:#f1f1f1">
			      <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
			    </div>
		 	</form>
		</div>
		<p>&nbsp</p>
		<div id="reward-list-div"></div>
	
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
	
	<br>
	
	<form method="LINK" action="LecturerHome.jsp" align="center">
	<input type="submit" value="Back">
	</form>
	
</body>
</html>