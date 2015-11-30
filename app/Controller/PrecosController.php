<?php

class PrecosController {


	private $preco;
	private $request;

	public function __construct(){

		$this->preco = new Preco();
		$this->request = (Object) $_SERVER;
	}


	public function cadastrar(Array $dados){


		
		if($this->request->REQUEST_METHOD != 'POST')
			return 'erro, tente passar dados validos';

		return $this->preco->addPreco($dados);


	}


}