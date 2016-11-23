-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 18, 2016 at 08:04 AM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `estimote`
--
CREATE DATABASE IF NOT EXISTS `estimote` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `estimote`;

-- --------------------------------------------------------

--
-- Table structure for table `estimote_backgrounds`
--

CREATE TABLE `estimote_backgrounds` (
  `id` int(10) UNSIGNED NOT NULL,
  `path` varchar(128) NOT NULL,
  `name` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `estimote_beacons`
--

CREATE TABLE `estimote_beacons` (
  `uuid` char(32) NOT NULL,
  `mac` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `estimote_beacon_conf`
--

CREATE TABLE `estimote_beacon_conf` (
  `beacon` char(32) NOT NULL,
  `song` int(10) UNSIGNED NOT NULL,
  `background` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `estimote_measurement_data`
--

CREATE TABLE `estimote_measurement_data` (
  `id` int(10) UNSIGNED NOT NULL,
  `beacon` char(32) NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  `signal_strength` int(11) DEFAULT NULL,
  `temperature` tinyint(4) DEFAULT NULL,
  `pressure` int(11) DEFAULT NULL,
  `brigthness` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `estimote_songs`
--

CREATE TABLE `estimote_songs` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(64) NOT NULL,
  `artist` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `estimote_backgrounds`
--
ALTER TABLE `estimote_backgrounds`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `estimote_beacons`
--
ALTER TABLE `estimote_beacons`
  ADD PRIMARY KEY (`uuid`);

--
-- Indexes for table `estimote_beacon_conf`
--
ALTER TABLE `estimote_beacon_conf`
  ADD PRIMARY KEY (`beacon`),
  ADD KEY `song` (`song`) USING BTREE,
  ADD KEY `background` (`background`) USING BTREE;

--
-- Indexes for table `estimote_measurement_data`
--
ALTER TABLE `estimote_measurement_data`
  ADD PRIMARY KEY (`id`),
  ADD KEY `beacon` (`beacon`) USING BTREE;

--
-- Indexes for table `estimote_songs`
--
ALTER TABLE `estimote_songs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `estimote_backgrounds`
--
ALTER TABLE `estimote_backgrounds`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `estimote_measurement_data`
--
ALTER TABLE `estimote_measurement_data`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `estimote_songs`
--
ALTER TABLE `estimote_songs`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `estimote_beacon_conf`
--
ALTER TABLE `estimote_beacon_conf`
  ADD CONSTRAINT `conf_background_id_fk` FOREIGN KEY (`background`) REFERENCES `estimote_backgrounds` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `conf_song_id_fk` FOREIGN KEY (`song`) REFERENCES `estimote_songs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `estimote_beacon_conf_ibfk_1` FOREIGN KEY (`beacon`) REFERENCES `estimote_beacons` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `estimote_measurement_data`
--
ALTER TABLE `estimote_measurement_data`
  ADD CONSTRAINT `estimote_measurement_data_ibfk_1` FOREIGN KEY (`beacon`) REFERENCES `estimote_beacons` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;
