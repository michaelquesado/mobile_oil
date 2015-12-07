<?php 
class HereMaps {
	
	private $link = "http://places.api.here.com/places/v1/discover/search?at=latitude,longitude&q=petrol-station&app_id=hG4gnJyrmlbNgGscL7Ki&app_code=h3XG36Nr4RgQOjymUTblJQ&pretty";
	private $postos;
	private $precosPostos = array();
	private $tipo = null;

	public function __construct($lat, $long, $tipo){

		$this->tipo = $tipo;
		$this->changeLink($lat, $long);
		$this->buscaPostos();
		$this->verificaPostos();

	}

	private function changeLink($l, $g){

		$this->link =  str_replace(['latitude', 'longitude'], [$l,$g], $this->link);

	}

	private function buscaPostos(){

		$out = new OutRequest($this->link);
		$this->postos = json_decode( $out->setRequisicao() );
		$out   = null;

	}

	/**
	* Quanto solicitado postos de um determinado local, verifica se os postos daquela localidade
	* ja existe na base cadastrada, caso não exista, cadastra e junto com ele, os valores padrões
	* 
	* @author Michael Quesado 
	*/
	private function verificaPostos(){

		$posto = new Posto();
		$preco = new Preco();
		$combustiveis = Combustivel::getInstance();
		
		$tipoCombustivel = $combustiveis->getTodosCombustiveis();


		foreach($this->postos->results->items as $p){

			$this->precosPostos[] = "'". $p->id . "'";

			try {

				if( !$posto->getPostoPorIdMaps($p->id) ){

					$posto->cadastrar(
						[  
						'nome' 		 =>  $p->title,
						'latitude'   =>	 $p->position[0],
						'longitude'  =>	 $p->position[1],
						'maps_id'	 =>  $p->id
						]
					);



					foreach($tipoCombustivel as $c){
						$preco->addPreco(
							[	
							'valor'            => -1,
							'combustivel_id'   => $c['id'],
							'usuario_id'	   => 0,
							'posto_id'		   => $p->id
							]
							);
					}
				}


			} catch (PDOException $e) {

				echo $e->getMessage();

			}
			
		}

		$combustiveis = null;
		$posto = null;
		$tipoCombustivel = null;
		$preco = null;
	}


	public function getPostos(){
		
		$p = new Posto();
	
		return $p->getAllPostoPorIdMaps( $this->precosPostos, $this->tipo  );

	}

}