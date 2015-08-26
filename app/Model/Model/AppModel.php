<?php

/**
 * Description of Model
 *
 * @author Michael Fillip da Silva Quesado  */
include_once('PDO/PDOConnection.php');

class AppModel {

    private $db;
    private $tabela;

    /* Construtor da Classe */

    public function __construct() {
        $this->db = new PDOConnection();
        $this->db = $this->db->getConnection();
        $this->db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_WARNING);
    }

    /* Função pra setar a tabela */

    protected function setTabela($tabela) {
        if (!is_string($tabela) || is_null($tabela)) {
            return false;
        }
        $this->tabela = $tabela;
    }

    /* get tabela */

    private function getTabela() {
        return $this->tabela = strtolower($this->tabela);
    }

    // Classe pra lê os campos
    public function read($campos = null, $where = null) {

        // if(empty($this->getTabela())) return false;
        try {

        $campos = ($campos != '') ? $campos : " * ";

        $where = (!is_null($where)) ? "WHERE " . $where : " ";

        $sql = "SELECT {$campos} FROM  {$this->getTabela()}   {$where}";
        $this->db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 

        $query = $this->db->query($sql);
        $query->setFetchMode(PDO::FETCH_ASSOC);

        return $query->fetchALL();
        } catch (PDOException $e) {

         throw new Exception('Erro ao tentar executar instrucao - PDO. '. $e->getMessage());

        } catch (Exception $e) {
          throw new Exception('Erro ao tentar executar instrucao - Exception');            
        }
    }

     protected function insert(Array $dados) {
     
        
        $valores = array();
        $campos_v = array();
        foreach ($dados as $key => $values) {
            $campos[] = $key;
            $campos_v[] = $key;
            $valores[] = utf8_decode($values);
        }
        $campos_v = ":" . implode(', :', $campos_v);
        return $this->setDados($campos, $valores, $campos_v);
    }
    private function setDados($campos, $valores, $campos_v) {
        $camp_sql = implode(',', $campos);
        $sql = "INSERT INTO {$this->getTabela()} ({$camp_sql}) VALUES ({$campos_v})";
        $stmt = $this->db->prepare($sql);
        foreach ($campos as $c => $v) {
            $stmt->bindValue(":" . $v, $valores[$c]);
        }
        try {
            $stmt->execute();
            $id_inserido = $this->db->lastInsertId();
            return (int) $id_inserido;
        } catch (PDOException $ex) {
            print $ex->getTraceAsString();
        }
    }
}
