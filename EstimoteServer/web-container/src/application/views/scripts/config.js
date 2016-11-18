var songs = [];
var backgrounds = [];

function getBeaconConfig (event) {
	console.log ($ (event.currentTarget).children ('option:selected').val (), 'aaaaaaa');
	request ('beacon/' + $ (event.currentTarget).children ('option:selected').val (), function (response) {
		showBeaconConfig (response);
	});
}
function showBeaconConfig (config) {
	var form = $ ('#config-form');
	form.children ('input[type="submit"]').css ('visibility', 'visible');
	form.children ('input[name="beacon"]').val ($ ('#beacon-select > option:selected').val ());
	var select = form.children (':input[name="song"]');
	select.css ('visibility', 'visible').empty ();
	var attrs = null;
	$.each (songs, function (key, song) {
		attrs = {
			value: song.id,
			text: song.artist + ' - ' + song.name
		};
		if (song.id === config.song) {
			attrs.selected = 'selected'
		}
		select.append ($ ('<option/>', attrs));
	});
	select = form.children (':input[name="background"]');
	select.css ('visibility', 'visible').empty ();
	$.each (backgrounds, function (key, background) {
		attrs = {
			value: background.id,
			text: background.name
		};
		if (background.id === config.background) {
			attrs.selected = 'selected'
		}
		select.append ($ ('<option/>', attrs));
	});
}
$(document).ready(function(){
	setInterval (getData, refreshRate);
	request ('beacons', function (response) {
		var select = $ ('#beacon-select');
		$.each (response, function (key, beacon) {
			select.append ($ ('<option/>', { value: beacon.uuid, text: beacon.name }));
		});
		select.on ('change', getBeaconConfig);
	});
	request ('songs', function (response) {
		if (response.error) {
			return;
		}
		songs = response;
	});
	request ('backgrounds', function (response) {
		if (response.error) {
			return;
		}
		backgrounds = response.backgrounds;
	});
});