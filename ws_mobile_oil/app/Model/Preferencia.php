<?php

require_once('Model/AppModel.php'); 

class Preferencia extends AppModel{

	private $table = 'preferencias';

	public function __construct(){
		parent::__construct();
		parent::setTabela($this->table);
	}

	public function adicionar(Array $dados){
		if(count($dados) == 0)
			return 'erro, tente passar preferencias validas';
		
		$dados['user_id'] = filter_var($dados['user_id'], FILTER_SANITIZE_NUMBER_INT);
		$combustivel_id = explode( ',', $dados['combustiveis_id']);
		foreach($combustivel_id as $v){
			if(is_null($v) || empty($v))
				return false;
			
			$id = filter_var($v, FILTER_SANITIZE_NUMBER_INT);
			$dado = ['user_id' => $dados['user_id'],'combustivel_id' => $id];

			try {
				$cb = parent::insert($dado);	
			} catch (Exception $e) {
				return 'erro ao tentar cadastrar';
			}

		}
		
		return 'sucesso';
	}

	public function getPreferenciasUsuario($id){
		
		if(empty($id))
			return 'Tente passar um usuario, valido.';

		$id = (int) $id;

		$id = filter_var($id, FILTER_SANITIZE_NUMBER_INT);
		$where = " user_id = $id ";
		return parent::read('*', $where);
	}

}