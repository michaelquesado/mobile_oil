<?php

require_once('Model/AppModel.php');

class Preco extends AppModel {

	private $table = 'precos';

	public function __construct(){
		parent::__construct();
		parent::setTabela($this->table);
	}

	public function addPreco(Array $dados){

		
		$insert = parent::insert($dados);
		return  ['msg' => ($insert == true)? 'ok'  : 'erro' ];


	}


}