<?php defined('BASEPATH') OR exit('No direct script access allowed'); ?><!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="../../assets/css/styles.css">
</head>
<body>
	<audio id="player" autoplay="autoplay">
	Your browser does not support the audio element.
	</audio>
	<h1 class="header">Estimote beacon project</h1>
	<h2 class="header2">Group 2<br/>Tatu Piippo, Jiska Parrila, Petri Virkkunen, Pauli Huttunen</h2>

	<div class="beacondataWrapper" id="beacondataWrapper">
	</div>
	<div class="switchButton">
	Play/Pause
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript">
			var refreshRate = 2000;
			var datawrapperName = "#beacondataWrapper";
			var player, backgroundPath, dataHTMLContent;
			//Data variables
			var closestbeacon, dataArray, temp, pressure, signal_strength, brightness;
			function printData(){
				dataHTMLContent = "Beacon: " + closestbeacon;
				dataHTMLContent += "\nTemperature: " + temp;
				dataHTMLContent += "\nSignal strength: " + signal_strength;
				dataHTMLContent += "\nBrightness: " + brightness;
				
				$(datawrapperName).html(dataHTMLContent);
			}
			function getData(){
				$.ajax({
					url:"http://www.students.oamk.fi/~t3paji00/estimote/index.php/api/currentconfig/",
					type: "GET",
					dataType: "JSON",
					success: function(response){
						console.log (response);
						var audio = "../../assets/audio/";
						var song = response ['song'];
						var src = $ ('<source/>', {
							id: "audiosrc",
							type: "audio/mpeg",
							src: audio + song.artist.toLowerCase () + '-' + song.name.toLowerCase () + '.mp3'
						});
						var background = response['background'];
						if(backgroundPath != (background.path + background.name)){
							backgroundPath = "../../assets/backgrounds" + background.path + background.name;
							$('body').css('background-image','url(' + backgroundPath + ')');
						}
						console.log(backgroundPath);
						$ ('#player').empty ().append (src);
						}
				});
				getMeasurementData();
			}
			function getMeasurementData(){
				$.ajax({
					url:"http://www.students.oamk.fi/~t3paji00/estimote/index.php/api/closestbeacon/",
					type: "GET",
					dataType: "JSON",
					success:function(response){
						console.log(jQuery.parseJSON(response));
						closestbeacon = response['b.uuid'];
						closestbeacon = closestbeacon.uuid;
					}
				});
				$.ajax({
					url:"http://www.students.oamk.fi/~t3paji00/estimote/index.php/api/beacondata/",
					type: "GET",
					dataType: "JSON",
					success:function(response){
						console.log(jQuery.parseJSON(response));
					dataArray = response[''];
						jQuery.each(dataArray, function(index, value){
							if(dataArray(index).beacon == closestbeacon){
								temp = dataArray(index).temperature;
								pressure = dataArray(index).pressure;
								signal_strength = dataArray(index).signal_strength;
								brightness = dataArray(index).brigthness;
							}
						});
					}
				})
				printData();
			}
			function playpause(){
				var player = $ ('#player');
				if (player.prop ('paused')){
					player[0].play();
				}else{
					player[0].pause();
				}
			}
			$(document).ready(function(){
				getData();
				$ ('.switchButton').on ('click', playpause);
				setInterval(getData, refreshRate);
			});
		</script>
	</body>
</html>
