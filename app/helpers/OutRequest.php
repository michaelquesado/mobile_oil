<?php 
class OutRequest{

	private $link;


	public function __construct( $link){

		$this->link = $link;
		error_reporting(0);

	}

	public function setRequisicao() {

		try {

			$context = stream_context_create(
				[
				'http' => [
				'method' => 'GET',
				'header' => [ 'Content-Type: application/x-www-form-urlencoded' ] ,
				]

				]
				);

			return $this->getRetornoRequisicao($context);
			
		} catch (Exception $e) {
			
			exit('Erro, ao buscar postos, tente novamente');
		}

		

	}



	private function getRetornoRequisicao($context) {

		try {

			$result = file_get_contents($this->link, false, $context) ;

		    if(!$result) throw new Exception("Error Processing Request", 1);
			
			return $result;
		} catch (Exception $e) {
			
			throw new Exception($e->getMessage());
			
		}
	}


}