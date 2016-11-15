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

	public function beacon ($id) {
		$sql = "SELECT id, uuid FROM estimote_beacons";
		$this->output->set_content_type ('application/json');
		$this->load->database ();
		if (($query = $this->db->query($sql)) !== false) {	
			$this->output->set_output (json_encode ($query->result_array()));
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
	}

	public function beacons () {
		$sql = "SELECT id, uuid FROM estimote_beacons";
		$this->output->set_content_type ('application/json');
		$this->load->database ();
		if (($query = $this->db->query($sql)) !== false) {	
			$this->output->set_output (json_encode ($query->result_array()));
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
	}

	public function closestbeacon () {
		$sql = "SELECT id, uuid FROM estimote_beacons";
		$this->output->set_content_type ('application/json');
		$this->load->database ();
		if (($query = $this->db->query($sql)) !== false) {	
			$this->output->set_output (json_encode ($query->result_array()));
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
	}

	public function beaconvalues ($id, $temperature = null, $brightness = null, $pressure = null) {
		
	}

	public function beaconconfig ($id, $song = 0, $background = 0) {
		
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
		if (($query = $this->db->query($sql)) !== false) {	
			$this->output->set_output (json_encode ([ 'common'=> [ 'path' => '/assets/backgrounds' ], 'backgrounds' => $query->result_array() ]));
		} else {
			$this->output->set_output (json_encode ([ 'error' => 'No workie.' ]));
		}
	}
}













