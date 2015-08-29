<?php 
class HereMaps {
	
	private $link = "http://places.api.here.com/places/v1/discover/search?at=latitude,longitude&q=petrol-station&app_id=hG4gnJyrmlbNgGscL7Ki&app_code=h3XG36Nr4RgQOjymUTblJQ&pretty";
	private $postos;
	private $precosPostos = array();

	public function __construct($lat, $long){

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


	private function verificaPostos(){

		$posto = new Posto();
		$preco = new Preco();
		$combustiveis = new Combustivel();

		foreach($this->postos->results->items as $p){

			try {

				$posto->cadastrar(
					[  
					'nome' 		 =>  $p->title,
					'latitude'   =>	 $p->position[0],
					'longitude'  =>	 $p->position[1],
					'maps_id'	 =>  $p->id
					]
					);

				foreach($combustiveis->getTodosCombustiveis() as $c){
					$preco->addPreco(
						[	
						'valor'            => -1,
						'combustivel_id'   => $c['id'],
						'usuario_id'	   => 0,
						'posto_id'		   => $p->id
						]
						);
				}

			} catch (Exception $e) {
				continue;
			}

			
		}

		$posto = null;
	}

}