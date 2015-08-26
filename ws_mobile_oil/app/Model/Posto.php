<?php
require_once('Model/AppModel.php'); 
class Posto extends AppModel{

	private $table = 'postos';

	public function __construct(){
		parent::__construct();
		parent::setTabela($this->table);
	}

	public function cadastrar(Array $dados){
		if(count($dados) == 0)
			return 'erro, tente passar dados validos de cadastro de posto';

		$dados['nome'] = filter_var($dados['nome'], FILTER_SANITIZE_STRING);
		$dados['latitude'] = filter_var($dados['latitude'], FILTER_SANITIZE_STRING);
		$dados['longitude'] = filter_var($dados['longitude'], FILTER_SANITIZE_STRING);
		$dados['endereco'] = filter_var($dados['endereco'], FILTER_SANITIZE_STRING);

		return parent::insert($dados);

	}

}