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

		$dados['username'] = filter_var($dados['username'], FILTER_SANITIZE_STRING);
		$dados['email'] = filter_var($dados['email'], FILTER_SANITIZE_EMAIL);
		$dados['pass'] = filter_var($dados['pass'], FILTER_SANITIZE_STRING);
		$dados['created'] = filter_var($dados['created'], FILTER_SANITIZE_STRING);

		return parent::insert($dados);

	}

	public function validaUsuario(Array $dados){
		if(count($dados) == 0)
			return 'erro, tente passar dados validos de login';
		
		$dados['email'] = strtolower(filter_var($dados['email'], FILTER_SANITIZE_EMAIL));
		$dados['pass'] = filter_var($dados['pass'], FILTER_SANITIZE_STRING);

		$where  = " email = '". $dados['email']. "'";
		$where .= " and pass  = '". $dados['pass'] . "'";
		$where .= ' limit 1 ';

		$result = parent::read('*', $where);

		return (count($result) > 0)? $result[0] : 'usuario ou senha incorreta.';
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