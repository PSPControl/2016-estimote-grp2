<?php defined('BASEPATH') OR exit('No direct script access allowed'); ?><!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="<?php echo WEBROOT_ASSETS_PATH; ?>/css/styles.css">
</head>
<body>
	<audio id="player" autoplay="autoplay" preload="none">
		Your browser does not support the audio element.
	</audio>
	<h1 class="header">Estimote beacon project</h1>
	<nav>
		<a href="<?php echo WEBROOT_INDEX_PATH; ?>">Front page</a> |
		<a href="<?php echo WEBROOT_INDEX_PATH; ?>/beaconconfig/">Beacon configuration</a> |
		<a href="<?php echo WEBROOT_INDEX_PATH; ?>/about/">About</a>
	</nav> 
	<div id="main">