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

	/**
	* Metodo responsavel por retornar posto por id
	*
	* @param $id identificador do posto
	* @author Michael Quesado
	*/
	public function getPostoPorIdMaps($id){
		try {

			return (isset( parent::read('id', "maps_id = '{$id}' ")[0]['id'] ) )? true : false ;

		} catch (Exception $e) {

		}
	}


	/**
	* Ao buscar por precos dos postos, deve remover os dados repetidos de precos para um determinado posto
	* deixando apenas o valor mais recente e de valor mais baixo cadastrado.
	*
	* @param  Array $array_id array com os ids do postos.
	* @author Michael Quesado
	* @return Array associativo.
	*/
	public function getAllPostoPorIdMaps($array_id){

		try {	

			//Instanciar os models necessarios
			$pre =  new Preco();
			$com = new Combustivel();
			//buscar por todos os combustiveis.
			$com = $com->getTodosCombustiveis();


			$precos = Array();
			$p = Array();
			//iteração no array dos postos
			foreach($array_id as  $v){
				//iteração em cada tipo de combustivel dentro de cadas posto
				foreach( $com as $c ){
					//pra cada tipo de combustivel busca o valor mais recente para aquele posto.
					$p[] = $pre->algo($v, $c['id']) ;

				}

				//Agora que temos os precos mais recentes para cada tipo de combustivel
				//buscamos eles associados ao posto.
				if(count($p) > 1){
					$precos[] = $this->buscaPostoEPrecos($v , implode(',', $p ) );	
				}

			}

			return $precos;
			
		} catch (Exception $e) {
			
		}		
		

	}

	/**
	* Metodo responsavel por buscar os valores para os postos.
	*
	* @author Michael Quesado
	*/
	private function buscaPostoEPrecos($maps_id, $precos){
		

		parent::setTabela(' postos p inner join precos pc on p.maps_id = pc.posto_id 
			inner join combustiveis c on c.id = pc.combustivel_id '  );

		$where = " maps_id = $maps_id and pc.id in ($precos) ";

		return parent::read(
		   'c.nome as combustivel,
			p.maps_id as id,
			p.nome, 
			p.latitude, 
			p.longitude,  
			pc.valor,
			pc.usuario_id,
			c.subcategoria_id
			', 
			$where);
	}

}