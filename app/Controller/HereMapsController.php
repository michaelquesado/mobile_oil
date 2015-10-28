<?php

class HereMapsController{


	public function getPostos(Array $dados){
		
		try {

			extract($dados,EXTR_PREFIX_SAME, "wddx");

			$here = new HereMaps($lat,$long);
			$here = null;
			
			return $here->getPostos();

		} catch (Exception $e) {

			echo $e->getMessage();
			
		}
		

	}


}