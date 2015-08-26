<?php
class PostosController {

	private $posto;
	private $request;

	public function __construct(){
		$this->posto = new Posto();
		$this->request = (Object) $_SERVER;
	}

	public function cadastrar(Array $dados){

		if($this->request->REQUEST_METHOD != 'POST')
			return 'erro, ao tentar cadastrar posto. Apenas post é aceito';
		
		return $this->posto->cadastrar($dados);
	}

	

}