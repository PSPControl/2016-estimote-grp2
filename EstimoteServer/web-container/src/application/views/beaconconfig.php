<?php defined('BASEPATH') OR exit('No direct script access allowed'); ?>
<form>
	<select id="beacon-select">
		<option selected>None</option>
	</select>
</form>
<form id="config-form" method="POST" target="<?php echo WEBROOT_INDEX_PATH?>/api/beaconconfig">
	<input type="hidden" name="beacon"/>
	<select style="visibility: hidden;" name="song"></select>
	<select style="visibility: hidden;" name="background"></select>
	<input style="visibility: hidden;" type="submit">
</form>