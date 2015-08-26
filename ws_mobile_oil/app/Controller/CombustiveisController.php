<?php
class CombustiveisController{


	private $combustivel;
	private $request;

	public function __construct(){

		$this->combustivel = new Combustivel();
			$this->request = (Object) $_SERVER;
	}

	public function cadastrar($Combustivel){

		if($this->request->REQUEST_METHOD != 'POST')
			return 'erro, ao tentar cadastrar combustivel. Apenas post é aceito';

		try {
			return $this->combustivel->adicionar($Combustivel);	
		} catch (Exception $e) {
			echo $e->getMessage();
		}
		
		
	}

	public function getCombustiveis(){
		return $this->combustivel->getTodosCombustiveis();
	}
}