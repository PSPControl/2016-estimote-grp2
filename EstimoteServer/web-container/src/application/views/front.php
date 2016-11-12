<?php defined('BASEPATH') OR exit('No direct script access allowed'); ?><!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Shit</title>
	<style type="text/css">
	</style>
</head>
<body>


<script type="text/javascript" src="../assets/ext/jquery.js"></script>
<script type="text/javascript">
	$.ajax({
	   url: 'api/songs',
	   type: 'GET',
	   error: function() {
		  console.error ('Fuck this.');
	   },
	   success: function(response) {
		  console.log (response);
	   }
	});
</script>
</body>
</html>