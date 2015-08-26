<?php

Class System {

    private $url;
    private $explode;
    private $action;
    private $params;

    /**
     * Construtor da classe
     */
    public function __construct() {
        $this->setURL();
        $this->setExplode();
        $this->setController();
        $this->setAction();
        $this->setParams();
    }

    /**
     * Metodo responsavel por setar a url
     */
    private function setURL() {
        //setando a url passada
        $this->url = (isset($_GET['url']) && count($_GET['url']) > 0) ? $_GET['url'] : NULL;
    }

    /**
     * dentro do _explode colocando todos os dados passado pela url
     */
    private function setExplode() {
        $this->explode = explode('/', $this->url);
    }

    /**
     * Setando o controller
     */
    private function setController() {
        $cnt = (!isset($this->explode[0]) || $this->explode[0] == null || $this->explode[0] == 'index') ? 'index' : $this->explode[0];
        $this->controller = ucfirst($cnt) . 'Controller';
    }

    /**
     * Setando a action
     */
    private function setAction() {
        $ac = (!isset($this->explode[1]) || $this->explode[1] == null || $this->explode[1] == 'index') ? 'index' : $this->explode[1];
        $this->action = $ac;
    }

       /**
     * Os parametros passados seram setados dentro de _params
     */
    private function setParams() {
        if(count($_POST) > 0)
         return $this->params = $_POST;

        $explode_aux = $this->explode;
        /* removendo o controller e a action, aqui so trabalhamos com os parametros */
        unset($explode_aux[0], $explode_aux[1]);

        if (end($explode_aux) == null) {
            array_pop($explode_aux);
        }
        
       return $this->params = $explode_aux;
    }

    private function getURL() {
        return $this->url;
    }

    /**
     * Metodo responsavel por iniciar o ws.
     */
    public function Run() {

        
        $controller_path = CONTROLLER . $this->controller . '.php';

        if(!file_exists($controller_path)){
            die('Controller ' . $this->controller . ' nao existe.');
        }

        require($controller_path);        
        $app = new $this->controller(); 

        $action = $this->action;

        if(!method_exists($app,$action)){
            die($this->controller);
        }
        try {
            return json_encode($app->$action((!empty($this->params)? $this->params : NULL)));    
        } catch (Exception $e) {
            echo $e->getMessage();
        }
    }

    /**
     * Metodo reponsavel por debug uma variavel.
     */
    public function pre($pre) {
        print"<pre>";
        print_r($pre);
        print"</pre>";
    }

}
