<?php

require_once('Model/AppModel.php');

class Combustivel extends AppModel{ 

	private $table = 'combustiveis';

	public function __construct(){
		parent::__construct();
		parent::setTabela($this->table);
	}

	public function adicionar(Array $dados){
		
		$dados['nome'] = filter_var($dados['nome'], FILTER_SANITIZE_STRING);
		$dados['subcategoria_id'] = (@!empty($dados['subcategoria_id']))?
				filter_var($dados['subcategoria_id'], FILTER_SANITIZE_NUMBER_INT ) :
				0;

		return parent::insert($dados);
	}

	public function getTodosCombustiveis(){

		return parent::read();
		
	}
}