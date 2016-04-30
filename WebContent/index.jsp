<!DOCTYPE html>
<html>
  <head>
  	<link rel="stylesheet" type="text/css" href="css/index.css"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Team 3 SF Park Routing</title>
    <style>
    form {
    	display: inline;
    	padding: 0px;
    	spacing: 0px;
	}
	
    html, body, #mapCanvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
      
    </style>
    <script src="js/jquery-1.11.2.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBcS9Vt7EHMK6dY0thNQlVJcjjMORSBHIc"></script>
    <script>


    	
		//for dragging origin and destinations on the map
		var rendererOptions = {
		  draggable: true
		};
		
		//direction display and routing
		var directionsService = new google.maps.DirectionsService();
		var directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);;
		
		//declaring the polyline
		var line;
		
		
		//declare the map
		var map;
		
		
		//initializing start and end points => parking blocks point from the db (some default value)
		var start = new google.maps.LatLng(37.8043800000,-122.4234820000); // "Bay and Polk"
		var end = new google.maps.LatLng(37.8088010000,-122.4210790000); //"N. end of Hyde"
		

		//build the polyline path
		var lineCoordinates = [start,end];
		
		//set the center of the map 
		var centerMap = new google.maps.LatLng(37.8054164152, -122.4236028968);
		
		//start time
		var startTime;
		
		//for clearing the interval
		var clrInterval;
		
		//to terminate
		var isTerminate;
		
		var move = 0;
		
		var walkingTime = 0;
		
		//method for calculating the route - given a start and end location
		function calculateRoute(start,end) {
			
			var request = {
				origin: start,
				destination: end,
				travelMode: google.maps.TravelMode.DRIVING
			  };
			  directionsService.route(request, function(response, status) {
				if (status == google.maps.DirectionsStatus.OK) { 
				  directionsDisplay.setDirections(response);
				}
			  });
		}
		
		//method for updating the new route
		function calculateNewRoute(start,end) {	
			var requestNew = {
				origin: start,
				destination: end,
				travelMode: google.maps.TravelMode.DRIVING
			  };
			directionsService.route(requestNew,function(response, status,statusCheck) {
			  if (status == google.maps.DirectionsStatus.OK) {
				  directionsDisplay.setDirections(response);
				  movementSimulator();
			  }
			});
		}

		
		//call => when page loads
		function initialize() {

		  //for setting up map attributes
		  var mapOptions = {
			zoom: 20,
			center: centerMap
		  };
		  
		  //initializing the map
		  map = new google.maps.Map(document.getElementById('mapCanvas'), mapOptions);		  
		  
		  //listener called when car route changes
		  google.maps.event.addListener(directionsDisplay, 'directions_changed', function() {
			computeTotalDistance(directionsDisplay.getDirections());
		  });
			  
		  //set the route on map
		  directionsDisplay.setMap(map);
		  
		  //for displaying the route direction panel
		  directionsDisplay.setPanel(document.getElementById('mainDirectionsPanel'));
		  
		  //calculating the route
		  calculateRoute(start,end);
		  
		}
		
		function showRoute(){
			
			if(document.getElementById("startLocation").value == ""){
				
				alert("Start Location cannot be blank");
				return false;
			}
			
			if(document.getElementById("endLocation").value == ""){
				alert("Destination Location cannot be blank");
				return false;
			}
			
			if(document.getElementById("chooseAlgorithm").value == "Choose Algorithm"){
				alert("Please select an algorithm to perform any operation");
				return false;
			}else{
				var algo = document.getElementById("chooseAlgorithm").value;
				document.getElementById("algorithmType").value = algo;
			}
			
			if(document.getElementById("startLocation").value != ""){
				start = document.getElementById("startLocation").value;
			}
			if(document.getElementById("endLocation").value != ""){
				end = document.getElementById("endLocation").value;
			}
			calculateRoute(start,end);
			
		}
		
		
		/*method for calculating the distance and time*/
		function computeTotalDistance(result) {
		  //getting all the co-ordinates for building the polyline path 
		  lineCoordinates = getAllPoints(result);

		}
		
		
		
		function initiateMovement(){
			if(document.getElementById("startLocation").value == ""){
				
				alert("Start Location cannot be blank");
				return false;
			}
			
			if(document.getElementById("endLocation").value == ""){
				alert("Destination location cannot be blank");
				return false;
			}
			
			if(document.getElementById("chooseAlgorithm").value == "Choose Algorithm"){
				alert("Please select an algorithm to perform any operation");
				return false;
			}else{
				var algo = document.getElementById("chooseAlgorithm").value;
				document.getElementById("algorithmType").value = algo;
			}
			
			if(document.getElementById("startLocation").value != ""){
				start = document.getElementById("startLocation").value;
			}
			if(document.getElementById("endLocation").value != ""){
				end = document.getElementById("endLocation").value;
			}
			document.getElementById("getDest").value = end;
			document.getElementById("end").innerHTML = end
			calculateRoute(start,end);
			startTime = performance.now();
			isTerminate = "CONTINUE";
			document.getElementById("start").innerHTML = start;
			movementSimulator();
		}

		
		/*to simulate movement*/
		function movementSimulator() {
		   
		
			
		   //initializing the polyline
		   line = new google.maps.Polyline({
				path: lineCoordinates,
				map: map,
				strokeColor: '#FF0000',
				strokeOpacity: 0.00001,
				strokeWeight: 0
		    });
			
			
			//for testing purpose only
			//var test = "(37.80543, -122.41527)";
			
			//for displaying markers at intervals to simulate movement
			clrInterval = window.setInterval(function() {	
				move = 0;
				if(move<line.getPath().getArray().length && isTerminate == "CONTINUE"){

					start = line.getPath().getArray()[move+1];
					console.log("IS Termimate in movement simulator first if:",isTerminate);
					if(line.getPath().getArray().length!=2 && isTerminate == "CONTINUE")
					{
						document.getElementById("getPoints").value = start;
						
						//invoke the algorithm - invoked internally when show route is clicked.
						document.getElementById('algoInvocation').click();
						isTerminate = "TERMINATE";
					}else{
						start = end;
						isTerminate = "TERMINATE";
						var endTime = performance.now();
						console.log("Start Time:",start);
						
						var timeElapsed = (endTime - startTime)/1000.0;
						console.log("Time Elapsed: ",timeElapsed);
						
						console.log("Walking Time Finally: ",walkingTime);
						if (walkingTime == null){
							walkingTime=120;
						}
						document.getElementById("queryTime").innerHTML = "Query time for Algo: "+timeElapsed+ " sec";
						
						timeElapsed = timeElapsed + parseInt(walkingTime);
						
						console.log("Calculated time elapsed: ",timeElapsed);
						
						document.getElementById("totalTimeTaken").innerHTML = "Slot acquired in: " + timeElapsed + " sec";
					}

					calculateRoute(start,end);
					move++;	
				}
			}, 3000);			
		}
		
		/*method for getting all the points on the polyline path*/
		function getAllPoints(result)
		{
			var route = result.routes[0];
			var points = new Array();
			var legs = route.legs;
			for (i = 0; i < legs.length; i++) {
				var steps = legs[i].steps;
				for (j = 0; j < steps.length; j++) {
					var nextSegment = steps[j].path;
					for (k = 0; k < nextSegment.length; k++) {
						points.push(nextSegment[k]);
					}
				}
			}
			return points;
		}
		
		
		/*for making an AJAX call*/
		$(document).ready(function(){
			$('#algoInvocation').click(function(){
				$.ajax({
					url: 'InvokeAlgorithm',
					type: 'POST',
					datatype: 'json',
					data: {
						getPoints : $('#getPoints').val(),
						getDest : $('#getDest').val(),
						getAlgoType : $('#algorithmType').val(),
						getIteration : $('#iteration').val(),
		            },
					success: function(response){
						//setting the endpoint co-ordinate
						//alert("started");
						
						if(response != ""){
							console.log("Got response from invoke algo");
							console.log(response);
							end = response.split("|")[0];
							console.log("End Time:",end);
							document.getElementById("routeTo").innerHTML = end;
							walkingTime = response.split("|")[1];
							console.log("Walking Time:",walkingTime);
						}
							
						
						//alert("end--->"+end);
						
						
						
						//clearing the current excuting timer
						clearInterval(clrInterval);
						
						isTerminate = "CONTINUE";
						//routing the user to the new route
						calculateNewRoute(start,end);
					}
				});
				return false
			});
			
			 $('.minibtn').click(function() {
				    // Show pop-up and disable background using #mask
				    $("#pop").fadeIn('slow');
				    $("#mask").fadeIn('slow');
				    // Load content.
				    $.post("contentPopup.html", function(data) {
				        $("#contentPop").html(data);
				    });
				    });
				       
				    // Hide pop-up and mask
				    $("#mask").hide();
				    $("#pop").hide();


				    // Size pop-up
				    var img_w = 520;
				    var img_h = 350;

				    // width and height in css.
				    $("#pop").css('width', img_w + 'px');
				    $("#pop").css('height', img_h + 'px');

				    // Get values ​​from the browser window
				    var w = $(this).width();
				    var h = $(this).height();

				    // Centers the popup  
				    w = (w / 2) - (img_w / 2);
				    h = (h / 2) - (img_h / 2);
				    $("#pop").css("left", w + "px");
				    $("#pop").css("top", h + "px");

				    // Function to close the pop-up
				    $("#close").click(function() {
				        $("#pop").fadeOut('slow');
				        $("#mask").fadeOut('slow');
				    });
			
		});
		
		
		
		google.maps.event.addDomListener(window, 'load', initialize);

    </script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    
    <script>
  $(function() {
    var availableTags = [
					"Beach and Polk",
					"North Point and Polk",
					"Bay and Polk",
					"W. end of Jefferson",
					"Beach and Larkin",
					"North Point and Larkin",
					"Bay and Larkin",
					"N. end of Hyde",
					"Jefferson and Hyde",
					"Beach and Hyde",
					"North Point and Hyde",
					"Bay and Hyde",
					"N. end of Leavenworth",
					"Jefferson and Leavenworth",
					"Beach and Leavenworth",
					"North Point and Leavenworth",
					"Midway 2700 block of Leavenworth",
					"Bay and Leavenworth",
					"Jefferson and Jones",
					"Beach and Jones",
					"North Point and Jones",
					"Bay and Jones",
					"Jefferson and Taylor",
					"Beach and Taylor",
					"North Point and Taylor",
					"Bay and Taylor",
					"Jefferson and Mason",
					"Beach and Mason",
					"North Point and Mason",
					"Bay and Mason",
					"Vandewater and Mason",
					"Jefferson and Powell",
					"Beach and Powell",
					"North Point and Powell",
					"Bay and Powell",
					"Beach and Stockton",
					"North Point and Stockton",
					"Bay and Stockton",
					"Beach and Grant",
					"North Point and Grant"
    ];
    $( "#tags" ).autocomplete({
      source: availableTags
    });
    
    $( "#end" ).autocomplete({
        source: availableTags
      });
  });
  
  var xmlhttp;
  var xmlhttp2;
  function populateStart(str)
  {
   
  var x=str;
  //s64ss

  if(x.length==null)
  {
  alert("Invalid place");
  }
  else
  {
  xmlhttp=GetXmlHttpObject();
  if (xmlhttp==null)
  {
  alert ("Your browser does not support XMLHTTP!");
  return;
  }
  var url="PopulateLatLong.jsp?place="+x;


  xmlhttp.onreadystatechange=stateChanged;
  xmlhttp.open("GET",url,true);
  xmlhttp.send(null);
  }
  }

  function populateEnd(str)
  {
   
  var x=str;
 
  if(x.length==null)
  {
  alert("Invalid place");
  }
  else
  {
  xmlhttp2=GetXmlHttpObject();
  if (xmlhttp2==null)
  {
  alert ("Your browser does not support XMLHTTP!");
  return;
  }
  var url="PopulateLatLong.jsp?place="+str;


  xmlhttp2.onreadystatechange=stateChanged;
  xmlhttp2.open("GET",url,true);
  xmlhttp2.send(null);
  }
  }
  
  
  
  function stateChanged()
  {
  if (xmlhttp.readyState==4)
  {
  document.getElementById("startLocation").value=xmlhttp.responseText;
  }
  if (xmlhttp2.readyState==4)
  {
  document.getElementById("endLocation").value=xmlhttp2.responseText;
  }
  }

  function GetXmlHttpObject()
  {
  if (window.XMLHttpRequest)
  {

  return new XMLHttpRequest();
  }
  if (window.ActiveXObject)
  {

  return new ActiveXObject("Microsoft.XMLHTTP");
  }

  }

  
  
  </script>
  </head>
  <form method="post">
	  <body>
	  	
	  <div id="mapCanvas" style="float:left;width:70%; height:100%"></div>
	    <div id="mainDirectionsPanel" style="float:right;width:30%;height: 100%;background-color: lightblue">
	
			<div style="padding: 10px;">
				<div id="text">Start Location</div>
				<div class="ui-widget">
  			<label for="tags"></label>
  			<input id="tags" name="inputStart" id="inputStart" onblur="populateStart(this.value)" class="input" style="width: 300px;height: 20px;border-radius: 3px;border: 1px solid #CCC;padding: 8px;padding-left: 10px;font-weight: 200;font-size: 15px;font-family: Verdana;box-shadow: 5px 5px 5px #CCC;">
			</div>
				<input type="text" name="startLocation" id="startLocation" hidden="hidden" />
				<br />
				<br />
				<div id="text">End Location</div>
				<div class="ui-widget">
  			<label for="end"></label>
  			<input id="end" name="Endip" id="Endip" onblur="populateEnd(this.value)" class="input" style="width: 300px;height: 20px;border-radius: 3px;border: 2px solid #CCC;padding: 8px;padding-left: 10px;font-weight: 200;font-size: 15px;font-family: Verdana;box-shadow: 5px 5px 5px #CCC;">
			</div>
				<input type="text" name="endLocation" id="endLocation" hidden="hidden" />
				<br />
				<br />
	
				<select id = "chooseAlgorithm">
					<option>Choose Algorithm</option>
					<option>RT Distance Based Gravitational Force</option>
					<option>RT Cost Based Gravitational Force</option>
					<option>RT Greedy Algorithm</option>
				</select><br><br>
				<input type = "button" id= "routeDisplay" onclick="showRoute();" value = "Show Route" class="button2" />
				<input type = "button" id= "Navigate" value="Navigate" onclick = "initiateMovement()" class="button2">
				<input type = "button" class= "minibtn" onclick="show();" value = "Show Results" />
			</div>
	    </div>
		
		<!-- Time Taken: <span id = "timeTaken"></span><br>	 -->
		
		<!-- For testing purpose -->
		<!-- Points: <span id = "points" name="points"></span><br>
		<input type = "hidden" id="getPoints" name="getPoints" value="test"></input> -->
		
		<input type = "hidden" id="getPoints" name="getPoints" value="test"></input>
		<!-- <input type = "hidden" id="terminate" name="getPoints" value="CONTINUE"></input> -->
		<input type = "hidden" id="getDest" name="getDest" value="test"></input>
		<input type = "hidden" id="algorithmType" name="algorithmType" value="test"></input>
		<input type = "hidden" id="iteration" name="iteration" value="first"></input>
		
		
		<input type="submit" id="algoInvocation" style="display: none;" value="test" onclick="InvokeAlgorithm"/> 
		<input type="submit" id="stopAlgo" style="display: none;" onclick = "TerminateAlgorithm"/>
		
		<!-- <input type="text" id="start" value = "test"/>
		<input type="text" id="end" value = "test"/><br><br -->
		
		
		<div id="pop">
		
            
            <div id="contentPop" align="left" style="padding: 20px;">
         
            
             <label id = "textstyle1">  RESULT </label><br><br><br>
            	
            	<label id = "textstyle">Start Location:<label> 
            	
				<span id="start"></span><br><br><br>
				
				
				Final Location: 
				<span id="routeTo"></span><br><br><br>
				<span id="totalTimeTaken"></span><br><br><br>
				
				<span id="queryTime"></span>
				
            
            </div>
            <div id="close">OK</div>
        </div>
          
         

        <div id="mask">
            <div id="page-wrap">
            
				
            </div>
        </div>

 
  </body>
  </form>
</html>