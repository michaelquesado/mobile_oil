<?php

require_once('Model/AppModel.php');

use \Exception;
class Combustivel extends AppModel{ 

	private $table = 'combustiveis';
	private static $instance = null;

	public function __construct(){
		parent::__construct();
		parent::setTabela($this->table);
	}

	public static function getInstance(){

		if(is_null(self::$instance) || empty(self::$instance)){
			self::$instance = new self;
		}

		return self::$instance;
	}


	public function adicionar(Array $dados){
		
		$dados['nome'] = filter_var($dados['nome'], FILTER_SANITIZE_STRING);

		if(!isset($dados['subcategoria_id'])) throw new \Exception('Erro, subcategoria necessaria.');

		$dados['subcategoria_id'] = (@!empty($dados['subcategoria_id']))?
				filter_var($dados['subcategoria_id'], FILTER_SANITIZE_NUMBER_INT ) :
				0;

		return parent::insert($dados);
	}

	public function getTodosCombustiveis(){

		return parent::read();
		
	}
}