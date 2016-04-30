//for dragging origin and destinations
		var rendererOptions = {
		  draggable: true
		};
		
		//for direction display and routing
		var directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);;
		var directionsService = new google.maps.DirectionsService();
		
		//declaring the map
		var map;
		
		//declaring the polyline
		var line;
		
		//initializing origin and destination (some default value)
		var start = new google.maps.LatLng(37.80573728,-122.4120841);
		var end = new google.maps.LatLng(37.8075689, -122.4214464);
		
		//setting the center of the map 
		var centerMap = new google.maps.LatLng(37.8054164152, -122.4236028968);
		
		//for building the polyline path
		var lineCoordinates = [start,end];
		
		//for clearing the interval
		var clrInterval;

		//called when page loads
		function initialize() {

		  //for setting up map attributes
		  var mapOptions = {
			zoom: 20,
			center: centerMap
		  };
		  
		  //initializing the map
		  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);		  
		  
		  //listener called when car route changes
		  google.maps.event.addListener(directionsDisplay, 'directions_changed', function() {
			computeTotalDistance(directionsDisplay.getDirections());
		  });
		  
			  
		  //setting the route on map
		  directionsDisplay.setMap(map);
		  
		  //for displaying the route direction panel
		  directionsDisplay.setPanel(document.getElementById('directionsPanel'));
		  
		  //calculating the route
		  calcRoute(start,end);
		  
		}
		
		
		/*method for calculating the route*/
		function calcRoute(start,end) {
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
		
		/*method for updating the new route*/
		function calcNewRoute(start,end) {			
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

		
		/*method for calculating the distance and time*/
		function computeTotalDistance(result) {
		  //getting all the co-ordinates for building the polyline path 
		  lineCoordinates = getAllPoints(result);

		 //displaying total distance
		  document.getElementById('total').innerHTML = result.routes[0].legs[0].distance.value/1000.0 + ' km';
		  
		  //displaying total time
		  document.getElementById('time').innerHTML = result.routes[0].legs[0].duration.value + ' seconds';
		}
		
		/*to simulate movement*/
		function movementSimulator() {
		   
			//getting the start time 
			var startTime = performance.now();
		   	
			//getting the end time
			var endTime = 0;
			
			//total time taken
			var timeElapsed = 0;
			
		   //initializing the polyline
		   line = new google.maps.Polyline({
				path: lineCoordinates,
				map: map,
				strokeColor: '#FF0000',
				strokeOpacity: 0.00001,
				strokeWeight: 0
		    });
			var i = 1;
			
			//for testing purpose only
			var test = "(37.80543, -122.41527)";
			
			//for displaying markers at intervals to simulate movement
			clrInterval = window.setInterval(function() {				
				if(i<line.getPath().getArray().length){
					//var flag = false;
					
					//resetting the start for each point on the polyline path
					start = line.getPath().getArray()[i];
					
					//for testing purpose
					/* document.getElementById("points").innerHTML = start;
					document.getElementById("getPoints").value = start; */

					if(start == test){
						document.getElementById('invokeAlgo').click();
						//flag = true;
					}

					end = line.getPath().getArray()[line.getPath().getArray().length-1];
					calcRoute(start,end);
					if(start == end)
					{
						endTime = performance.now();
						timeElapsed = (endTime - startTime)/1000.0;
						document.getElementById('timeTaken').innerHTML = timeElapsed;
					}
					i++;	
				}
			}, 1000);			
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
			$('#invokeAlgo').click(function(){
				$.ajax({
					url: 'InvokeAlgorithm',
					type: 'POST',
					datatype: 'json',
					data: $('#invokeAlgo').serialize(),
					success: function(data){
						//setting the endpoint co-ordinate
						end = data;
						
						//clearing the current excuting timer
						clearInterval(clrInterval);
						
						//routing the user to the new route
						calcNewRoute(start,end);
						
					}
				});
				return false
			});
		});
		
		google.maps.event.addDomListener(window, 'load', initialize);