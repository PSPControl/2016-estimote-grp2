<?php defined('BASEPATH') OR exit('No direct script access allowed'); ?><!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="assets/css/styles.css">
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
	<script src="assets/ext/jquery.js"></script>
		<script type="text/javascript">
			var bg1 = "#FF0012"; //document.body.style.background
			var bg2 = "#000000";
			var dataurl = "php/data.php";
			var refreshRate = 2000;
			var datawrapperName = "#beacondataWrapper";
			var player;
			function getData(){
				$.ajax({
					url:"index.php/api/currentconfig",
					type: "GET",
					dataType: "JSON",
					success: function(response){
						console.log (response);
						var audio = "assets/audio/"; 
						var song = response ['song'];
						var src = $ ('<source/>', {
							id: "audiosrc",
							type: "audio/mpeg",
							src: audio + song.artist.toLowerCase () + '-' + song.name.toLowerCase () + '.mp3'
						});
						$ ('#player').empty ().append (src);
					}
				});
			}
			function playpause(){
				var player = $ ('#player');
				if (player.prop ('paused')){
					player[0].play();
					document.body.style.background = "#FFFFFF";
				}else{
					player[0].pause();
					document.body.style.background = bg2;
				}
			}
			$(document).ready(function(){
				$ ('.switchButton').on ('click', playpause);
				setInterval(getData, refreshRate);
			});	
		</script>
	</body>
</html>
