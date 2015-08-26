<?php

class PreferenciasController{

	private $preferencia;
	private $request;

	public function __construct(){

		$this->preferencia = new Preferencia();
		$this->request = (Object) $_SERVER;

	}


	public function cadastrar($dados){

	if($this->request->REQUEST_METHOD != 'POST')
			return 'erro, ao tentar cadastrar usuario. Apenas post é aceito';
		
		return $this->preferencia->adicionar($dados);
	}

	public function getPreferenciasUsuarioId($id){

		if($this->request->REQUEST_METHOD != 'GET')
			return 'erro, ao tentar solicitar preferencias do usuario.';

		return $this->preferencia->getPreferenciasUsuario($id[2]);
	}

}