<?php 
class OutRequest{

	private $link;


	public function __construct( $link){

		$this->link = $link;
	}

	public function setRequisicao() {

		$context = stream_context_create(
			[
			'http' => [
			'method' => 'GET',
			'header' => [ 'Content-Type: application/x-www-form-urlencoded' ] ,
			]

			]
			);

		return $this->getRetornoRequisicao($context);

	}



	private function getRetornoRequisicao($context) {

		return $result = file_get_contents($this->link, false, $context);
	}


}