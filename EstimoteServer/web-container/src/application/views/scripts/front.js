function playpause(){
	var player = $ ('#player');
	if (player.prop ('paused')){
		player[0].play();
		document.body.style.background = "#FFFFFF";
	}else{
		player[0].pause();
		document.body.style.background = "#000000";
	}
}
$(document).ready(function(){
	$ ('.switchButton').on ('click', playpause);
	setInterval(getData, refreshRate);
});
