<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class BeaconConfig extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see https://codeigniter.com/user_guide/general/urls.html
	 */
	public function index()
	{
		if ($this->input->post ()) {
			$id = $this->input->post ('beacon');
			$song = $this->input->post ('song');
			$background = $this->input->post ('background');
			/*
			<input type="hidden" name="beacon"/>
			<select style="visibility: hidden;" name="song"></select>
			<select style="visibility: hidden;" name="background"></select>
			<input style="visibility: hidden;" type="submit">
			*/
			if (!$this->beaconExists ($id) || !$this->songExists ($song) || !$this->backgroundExists ($background)) {
				return;
			}
			$sql = "INSERT INTO estimote_beacon_conf (beacon, song, background) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE song = VALUES(song), background = VALUES(background)";
			$params = [];
			$args = [ $id, $song, $background ];
			for ($i = 0; $i < 3; ++$i) {
				if (isset ($args [$i]) && !empty ($args [$i])) {
					$params [] = $this->db->escape_str ($args [$i]);
				}
			}
			if (count ($params) === 3) {
				$this->db->query($sql, $params);
			}
		}
		$script = @file_get_contents (APPPATH.'views'.DIRECTORY_SEPARATOR.'scripts'.DIRECTORY_SEPARATOR.'config.js');
		if (!$script) {
			$script = '';
		}
		$this->load->view('header');
		$this->load->view('beaconconfig');
		$this->load->view('footer', [ 'script' => $script ]);
	}

	private function beaconExists ($id) {
		$sql = "SELECT COUNT(*) AS bc FROM estimote_beacons WHERE uuid LIKE ?";
		$this->load->database ();
		return ($query = $this->db->query($sql, [ $this->db->escape_str ($id) ])) !== false && (int)$query->row ()->bc > 0;
	}

	private function songExists ($id) {
		$sql = "SELECT COUNT(*) AS bc FROM estimote_songs WHERE id = ?";
		$this->load->database ();
		return ($query = $this->db->query ($sql, [ $this->db->escape ((int)$id) ])) !== false && (int)$query->row ()->bc > 0;
	}

	private function backgroundExists ($id) {
		$sql = "SELECT COUNT(*) AS bc FROM estimote_backgrounds WHERE id = ?";
		$this->load->database ();
		return ($query = $this->db->query ($sql, [ $this->db->escape ((int)$id) ])) !== false && (int)$query->row ()->bc > 0;
	}
}
