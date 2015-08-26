<?php
/**
 * Class responsavel por limpar sqls
 */
class LimpaSql{
    
    /**
     * Metodo responsavel por limpar uma string
     * 
     * @return String  Limpa de sql
     */
     static public function filtra($dados) {
        $dados = strip_tags($dados);
        $dados = addslashes($dados);
        $dados = htmlspecialchars($dados);
        
        
        $dados = @preg_replace(sql_regcase('/(from|select|insert|delete|where|drop table|show tables|#|\*| |\\\\)/'), ' ', $dados);
        
        return $dados;
    }

}