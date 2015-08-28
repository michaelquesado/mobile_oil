# ws_mobile_oil
Web Server Mobile Oil

WS contruido com o intuito de servir aplicação movel Mobile Oil.

## Configuraçoes
<ul>
    <li>MOD_REWRITE</li>
    <li>PDO</li>
    <li>PDO_PGSQL</li>
    <li>DISPLAY_ERROS</li>
</ul>

## Exemplo de requisição get

```javascript

>Buscando tipos de combustiveis.

GET /ws_mobile_oil/Combustiveis/getCombustiveis HTTP/1.1
Host: localhost

[{"id":1,"nome":"Gasolina","subcategoria_id":0},{"id":2,"nome":"Gasolina Aditivada","subcategoria_id":1},{"id":3,"nome":"Diesel","subcategoria_id":0},{"id":4,"nome":"Etanol","subcategoria_id":0}]

>Buscar preferencia de combustivel usuario


GET /ws_mobile_oil/Preferencias/getPreferenciasUsuarioId/1 HTTP/1.1
Host: localhost

[{"id":1,"combustivel_id":1,"user_id":1},{"id":2,"combustivel_id":2,"user_id":1}]

```



## Exemplo de requisição post

```javascript
>cadastrar usuario

POST /ws_mobile_oil/Login/cadastro HTTP/1.1
Host: localhost
Content-Type: application/x-www-form-urlencoded
Content-Length: length

username=coldpegasusTemUmFa&pass=123456&email=aguaiMaxima%40mail.com&created=2015-10-15+11%3A40%3A16
.242511-03


>logar usuario

POST /ws_mobile_oil/Login/logar HTTP/1.1
Host: localhost
Content-Type: application/x-www-form-urlencoded
Content-Length: length

pass=123456&email=aguaiMaxima%40mail.com


>adicionar preferencia

POST /ws_mobile_oil/Preferencias/cadastrar HTTP/1.1
Host: localhost
Content-Type: application/x-www-form-urlencoded
Content-Length: length

user_id=1&combustivel_id=2

>adicionar combustivel

POST /ws_mobile_oil/Combustiveis/cadastrar HTTP/1.1
Host: localhost
Content-Type: application/x-www-form-urlencoded
Content-Length: length

nome=Etanol&subcategoria_id=0


>adicionar posto de combustivel

POST /ws_mobile_oil/Postos/cadastrar HTTP/1.1
Host: localhost
Content-Type: application/x-www-form-urlencoded
Content-Length: length

nome=AutoPosto la em casa de coldpegasus&latitude=19.909090909&longitude=78.0000000


>adicionar preco a um posto

POST /ws_mobile_oil/Precos/cadastrar HTTP/1.1
Host: localhost
Content-Type: application/x-www-form-urlencoded
Content-Length: length

posto_id=1&valor=3.50&combustivel_id=1&usuario_id=1

```

## Padrões

<ul>
    <li>MVC</li>
    <li>REST</li>
</ul>

