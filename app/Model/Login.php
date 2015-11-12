<?php
require_once('Model/AppModel.php'); 
class Login extends AppModel{

	private $table = 'users';

	public function __construct(){
		parent::__construct();
		parent::setTabela($this->table);
	}

	public function cadastrar(Array $dados){
		if(count($dados) == 0)
			return 'erro, tente passar dados validos de usuario';
		try {
			
			$dados['username'] = filter_var($dados['username'], FILTER_SANITIZE_STRING);
			$dados['email'] = filter_var($dados['email'], FILTER_SANITIZE_EMAIL);
			$dados['pass'] = filter_var($dados['pass'], FILTER_SANITIZE_STRING);
			$now = new \DateTime();
			$dados['created'] = $now->format('Y-m-d H:m:s');

			$now = null;

			return parent::insert($dados);

		} catch (Exception $e) {

			throw new Exception("Dados invalidos");
			
		}
		

		

	}

	public function validaUsuario(Array $dados){
		if(count($dados) == 0)
			return 'erro, tente passar dados validos de login';
		try {
			
			$dados['email'] = strtolower(filter_var($dados['email'], FILTER_SANITIZE_EMAIL));
			$dados['pass'] = filter_var($dados['pass'], FILTER_SANITIZE_STRING);

			$where  = " email = '". $dados['email']. "'";
			$where .= " and pass  = '". $dados['pass'] . "'";
			$where .= ' limit 1 ';

			$result = parent::read('*', $where);

			 return  [ 'retorno' =>  (count($result) > 0) ? 'true' : 'false' ] ;

		} catch (Exception $e) {

			die( 'erro ao tentar validar usuario.' );

		}

	}


	public function getUsers(){

		try {

			$result = parent::read('*', null, ' order by id desc');

			return (count($result) > 0) ? $result : [0 => 'nada encontrado.'];

		} catch (Exception $e) {
			
			echo 'Erro ao tentar buscar usuarios.';
		}
		

	}	

}