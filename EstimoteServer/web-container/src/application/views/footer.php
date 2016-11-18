<?php defined('BASEPATH') OR exit('No direct script access allowed'); ?>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script type="text/javascript">
		var refreshRate = 2000;
		var current_background = null;
		var current_song = null;
		var datawrapperName = "#beacondataWrapper";
		//Data variables
		var closestbeacon, dataArray, temp, pressure, signal_strength, brightness;
		function printData(){
			dataHTMLContent = "Beacon: " + closestbeacon;
			dataHTMLContent += "\nTemperature: " + temp;
			dataHTMLContent += "\nSignal strength: " + signal_strength;
			dataHTMLContent += "\nBrightness: " + brightness;
				
			$(datawrapperName).html(dataHTMLContent);
		}
		function getMeasurementData(){
			$.ajax({
				url:"<?php echo WEBROOT_INDEX_PATH; ?>/api/closestbeacon/",
				type: "GET",
				dataType: "JSON",
				success:function(response){
					console.log(jQuery.parseJSON(response));
					closestbeacon = response['b.uuid'];
					closestbeacon = closestbeacon.uuid;
				}
			});
			$.ajax({
				url:"<?php echo WEBROOT_INDEX_PATH; ?>/api/beacondata/",
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
		function request (target, success, failure, parameters) {
			$.ajax({
				url: "<?php echo WEBROOT_INDEX_PATH; ?>/api/" + target,
				type: "GET",
				dataType: "JSON",
				success: success,
				failure: failure
			});
		}
		function post (target, success, failure, parameters) {
			$.ajax({
				url: "<?php echo WEBROOT_INDEX_PATH; ?>/api/" + target,
				type: "POST",
				data: parameters,
				dataType: "JSON",
				success: success,
				failure: failure
			});
		}
		function getData(){
			request ('currentconfig',
			function(response){
				if (response.error) {
					return;
				}
				var audio = "<?php echo WEBROOT_ASSETS_PATH; ?>/audio/"; 
				var song = response.song;
				song = audio + song.artist.toLowerCase ().replace (/\W/g, '') + '-' + song.name.toLowerCase ().replace (/\W/g, '') + '.mp3';
				if (current_song !== song) {
					$ ('#player').attr ('src', song);
					current_song = song;
				}
				var background = response.background;
				if (current_background !== (background.path + background.name)) {
					current_background = "<?php echo WEBROOT_ASSETS_PATH; ?>/backgrounds" + background.path + background.name;
					$ ('body').css ('background-image','url(' + current_background + ')');
				}
			});
		}
		function changeContent (event) {
			console.log (event);
			return false;
		}
		$ (document).ready (function () {
			console.log ();
			$ ('nav a').on ('click', changeContent (this));
		});
		<?php if (isset ($script)) { echo $script; } ?>
	</script>
	</body>
</html>


