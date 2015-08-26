<?php
//definindo api como publica. Para requisições cross-domain.
header("Access-Control-Allow-Origin: *");

require_once('config.php');

$system = new System();
echo $system->Run();