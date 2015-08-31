<?php

class HereMapsController{


	public function getPostos(Array $dados){
		
		extract($dados,EXTR_PREFIX_SAME, "wddx");

		$here = new HereMaps($lat,$long);

		return $here->getPostos();

	}


}