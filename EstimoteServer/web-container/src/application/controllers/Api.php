<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Api extends CI_Controller {

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

	public function beacon ($id = 0) {
		if ($this->beaconExists ($id)) {
			$sql = "SELECT song, background FROM estimote_beacon_conf WHERE beacon = ?";
			$this->output->set_content_type ('application/json');
			$this->load->database ();
			if (($query = $this->db->query($sql, [ $this->db->escape_str ($id) ])) !== false && $query->row ()) {	
				$this->output->set_output (json_encode ((array)$query->row ()));
				return;
			}
		} $this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
	}

	public function beacons () {
		$sql = "SELECT uuid, name FROM estimote_beacons";
		$this->output->set_content_type ('application/json');
		$this->load->database ();
		if (($query = $this->db->query($sql)) !== false) {	
			$this->output->set_output (json_encode ($query->result_array ()));
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
	}

	public function closestbeacon () {
		$sql = "SELECT b.uuid FROM estimote_beacons AS b JOIN estimote_measurement_data AS d ON d.beacon = b.uuid WHERE price = (SELECT MIN(signal_strength) FROM estimote_measurement_data)";
		$this->output->set_content_type ('application/json');
		$this->load->database ();
		if (($query = $this->db->query ($sql)) !== false) {	
			$this->output->set_output (json_encode ($query->result_array ()));
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
	}
	public function beacondata(){
		$sql = "SELECT beacon, timestamp, signal_strength, temperature, pressure, brigthness 
			FROM estimote_measurement_data 
			WHERE timestamp = (SELECT MAX(timestamp) 
			FROM estimote_measurement_data) GROUP BY beacon";
			
		$this->output->set_content_type('application/json');
		$this->load->database();
		if(($query = $this->db->query($sql)) !== false){
			$this->output->set_output(json_encode ($query->result_array(())));
		}else{
			$this->output->set_output(json_encode(["Error:" => "beaconvalues error"]));
		}
	}

	public function beaconvalues ($id = 0, $signal_strength = 0, $temperature = null, $brightness = null, $pressure = null) {
		if (!$this->beaconExists ($id)) {
			return;
		}
		$sql = "INSERT INTO estimote_measurement_data (beacon, timestamp, signal_strength, temperature, pressure, brigthness) VALUES (?, UNIX_TIMESTAMP(), ?, ?, ?, ?)";
		$params = [];
		$args = func_get_args ();
		for ($i = 0; $i < 5; ++$i) {
			if (isset ($args [$i]) && !empty ($args [$i])) {
				$params [] = $this->db->escape_str ($args [$i]);
			}
		}
		if (count ($params) === 5) {
			$this->db->query($sql, $params);
		}
	}

	public function beaconconfig ($id = 0, $song = 0, $background = 0) {
		if (!$this->beaconExists ($id) || !$this->songExists ($song) || !$this->backgroundExists ($background)) {
			return;
		}
		$sql = "INSERT INTO estimote_beacon_conf (beacon, song, background) VALUES (?, ?, ?)";
		$params = [];
		$args = func_get_args ();
		for ($i = 0; $i < 3; ++$i) {
			if (isset ($args [$i]) && !empty ($args [$i])) {
				$params [] = $this->db->escape_str ($args [$i]);
			}
		}
		if (count ($params) === 3) {
			$this->db->query($sql, $params);
		}
	}

	public function songs () {
		$sql = "SELECT id, name, artist FROM estimote_songs";
		$this->output->set_content_type ('application/json');
		$this->load->database ();
		if (($query = $this->db->query($sql)) !== false) {	
			$this->output->set_output (json_encode ($query->result_array()));
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
	}

	public function backgrounds () {
		$sql = "SELECT id, name, path FROM estimote_backgrounds";
		$this->output->set_content_type ('application/json');
		$this->load->database ();
		if (($query = $this->db->query ($sql)) !== false) {	
			$this->output->set_output (json_encode ([ 'common'=> [ 'path' => '/assets/backgrounds' ], 'backgrounds' => $query->result_array() ]));
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
	}

	public function currentconfig () {
		$sql = "SELECT b.uuid FROM estimote_beacons AS b JOIN estimote_measurement_data AS d ON d.beacon = b.uuid AND signal_strength = (SELECT MAX(signal_strength) FROM estimote_measurement_data WHERE timestamp > UNIX_TIMESTAMP() - 5) ORDER BY d.timestamp DESC LIMIT 1";
		$this->load->database ();
		if (($query = $this->db->query ($sql)) !== false && $query->row ()) {
			$sql = "SELECT song, background FROM estimote_beacon_conf WHERE beacon = ?";
			if (($query = $this->db->query ($sql, [ $this->db->escape_str ($query->row ()->uuid) ]))) {
				$this->output->set_output (json_encode ([
					'song' => $this->getSong ($query->row ()->song),
					'background' => $this->getBackground ($query->row ()->background)
				]));
			} else {
				$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
				return;
			}
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
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

	private function getSong ($id) {
		$sql = "SELECT name, artist FROM estimote_songs WHERE id = ?";
		if (($query = $this->db->query ($sql, [ $this->db->escape ((int)$id) ])) !== false && $query->row ()) {
			return [ 'name' => $query->row ()->name, 'artist' => $query->row ()->artist ];
		} return [ 'name' => '', 'artist' => '' ];
	}

	private function getBackground ($id) {
		$sql = "SELECT name, path FROM estimote_backgrounds WHERE id = ?";
		if (($query = $this->db->query ($sql, [ $this->db->escape ((int)$id) ])) !== false && $query->row ()) {
			return [ 'name' => $query->row ()->name, 'path' => $query->row ()->path ];
		} return [ 'name' => '', 'path' => '' ];
	}
}