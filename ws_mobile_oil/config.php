<?php

header("Content_Type:application/json");
require_once('system/System.php');

define('DS', '/');
define('CONTROLLER','app/Controller/');
define('MODEL', 'app/Model/');


function __autoload($file) {
    $file .= ".php";
    if (file_exists(MODEL . $file)) {
        require_once(MODEL . $file);
    } else {
        die('Erro ao tentar carregar arquivo ' . $file);
    }
}
