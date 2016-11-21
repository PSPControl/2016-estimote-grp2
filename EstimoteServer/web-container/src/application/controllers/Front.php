<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Front extends CI_Controller {

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
		$script = @file_get_contents (APPPATH.'views'.DIRECTORY_SEPARATOR.'scripts'.DIRECTORY_SEPARATOR.'front.js');
		if (!$script) {
			$script = '';
		}
		$this->load->view('header');
		$this->load->view('front');
		$this->load->view('footer', [ 'script' => $script ]);
	}
}
