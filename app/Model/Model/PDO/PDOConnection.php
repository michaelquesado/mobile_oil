<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of PDOConnectionFactory
 *
 * @author Michael Fillip da Silva Quesado
 */
// Classe de conexão com o banco
class PDOConnection {

    protected $con;
    private $host;
    private $dbname;
    private $username;
    private $pass;
    private $persistent = false;

   public function __construct() {
        $this->dbname = "mobile_oil";
        $this->username = "postgres";
        $this->pass = 'h4ck3v1m2';
        $this->host = "localhost";
    }


    private function PDOConnectionFactory($persistent = false) {
        // verifico a persistência da conexao
        if ($persistent != false) {
            $this->persistent = true;
        }
    }

    public function getConnection() {
        try {
            $this->con = new PDO("pgsql:host=" . $this->host . ";dbname=" . $this->dbname, $this->username, $this->pass, array(PDO::ATTR_PERSISTENT => $this->persistent));
            return $this->con;
        } catch (PDOException $e) {
            echo $e->getMessage();
            exit;
        }
    }

}
