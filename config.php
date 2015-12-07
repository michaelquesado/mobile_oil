<?php

header("Content_Type:application/json");
require_once('system/System.php');

define('DS', '/');
define('CONTROLLER','app/Controller/');
define('MODEL', 'app/Model/');
define('HELPERS', 'app/helpers/');

date_default_timezone_set('America/Fortaleza');

//error_reporting(0);

function __autoload($file) {
    $file .= ".php";
    if (file_exists(MODEL . $file)) {
    	
        require_once(MODEL . $file);

    }elseif(file_exists(HELPERS . $file)){

		require_once(HELPERS.$file);

	} else {
        die('Erro ao tentar carregar arquivo ' . $file);
    }
}
