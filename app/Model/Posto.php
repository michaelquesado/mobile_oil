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

		return parent::insert($dados);

	}

	public function getPostoPorIdMaps($id){
		try {

			return (isset( parent::read('id', "maps_id = '{$id}' ")[0]['id'] ) )? true : false ;

		} catch (Exception $e) {

		}
	}


	public function getAllPostoPorIdMaps($array_id){

		try {

			parent::setTabela(' postos p inner join precos pc on p.maps_id = pc.posto_id 
				inner join combustiveis c on c.id = pc.combustivel_id '  );

			$where = " p.maps_id IN ( " . $array_id ." )";

			return parent::read(
				'p.id,
				p.nome, 
				p.latitude, 
				p.longitude, 
				c.nome as combustivel, 
				pc.valor,
				pc.usuario_id,
				c.subcategoria_id
				', 
				$where);

			
		} catch (Exception $e) {
			
		}		
		

	}

}