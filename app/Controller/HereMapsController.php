<?php

class HereMapsController{


	public function getPostos(Array $dados){
		extract($dados,EXTR_PREFIX_SAME, "wddx");

		new HereMaps($lat,$long);

	}


}