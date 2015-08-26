<?php

class PrecosController {


	private $preco;
	private $request;

	public function __construct(){

		$this->preco = new Preco();
		$this->request = (Object) $_SERVER;
	}


	public function cadastrar(Array $dados){

		if(count($dados) > 1 || $this->request->REQUEST_METHOD != "post")
			return 'erro, tente passar dados validos';
		return $this->preco->addPreco($dados);


	}


}