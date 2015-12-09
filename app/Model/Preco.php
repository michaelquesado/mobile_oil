<?php

require_once('Model/AppModel.php');

class Preco extends AppModel {

	private $table = 'precos';

	public function __construct(){
		parent::__construct();
		parent::setTabela($this->table);
	}

	public function addPreco(Array $dados){

		$date = new \DateTime();
		$dados['created'] = $date->format('d-m-Y H:i:s');

		try {

			$insert = parent::insert($dados);

			return  ['msg' => ($insert == true)? 'ok'  : 'erro ao tentar cadastrar.' ];

		} catch (Exception $e) {
			

			
		}
		

		


	}


	public function getTodosOsPrecos(){

		return  parent::read();
	}


	public function algo($id, $com){
		

		$result = parent::read('id', " posto_id = $id and combustivel_id = $com order by created desc limit 1");

		
		return  (isset($result[0]))? $result[0]['id'] : '' ;
	}


}