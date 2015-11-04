<?php

class LoginController{

	private $login;
	private $request;

	public function __construct(){
		$this->login = new Login();
		$this->request = (Object) $_SERVER;
	}

	public function cadastro($Usuario){
		
		if($this->request->REQUEST_METHOD != 'POST')
			return 'erro, ao tentar cadastrar usuario. Apenas post é aceito';
		return $this->login->cadastrar($Usuario);
	}

	public function logar($Usuario){
		
		if($this->request->REQUEST_METHOD != 'POST')
			return 'erro, ao tentar cadastrar usuario. Apenas post é aceito';

		return $this->login->validaUsuario($Usuario);
		
	}

	/**
	*Metodo responsavel por retorna todos os usuarios cadastrados no banco ordenando pelo id desc
	*/
	public function getUsuarios(){

		return $this->login->getUsers();
	}

}