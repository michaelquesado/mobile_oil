# ws_mobile_oil
Web Server Mobile Oil

WS contruido com o intuito de servir aplicação movel Mobile Oil.

## Configuraçoes
<ul>
<li>MOD_REWRITE</li>
<li>PDO</li>
<li>PDO_PGSQL</li>
<li>DISPLAY_ERROS</li>
<li>JQUERY</li>
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

	$.ajax({
		type: 'post',
		url: 'http://localhost/ws_mobile_oil/Login/cadastro',
		data: {
			username: 'coldpegasus',
			pass: '123456',
			email: 'aguaiMaxima@mail.com', 
			created: '2015-10-15 11:40:16.242511-03' 
		}
	});


	>logar usuario

	$.ajax({ 
        type: 'post',
        url: 'http://localhost/ws_mobile_oil/Login/logar',
        data:{
        	
        	pass: '123456',
			email: 'aguaiMaxima@mail.com', 
        }

	});

	>adicionar preferencia
	
	 $.ajax({ 
        type: 'post',
        url: 'http://localhost/ws_mobile_oil/Preferencias/cadastrar',
        data:{
            user_id: 1,
            combustivel_id: 2 
        }
    });

    >adicionar combustivel

      $.ajax({ 
        type: 'post',
        url: 'http://localhost/ws_mobile_oil/Combustiveis/cadastrar',
        data:{
            nome: 'Etanol',
            subcategoria_id: 0 
        }
    });

    >adicionar posto de combustivel

      $.ajax({ 
        type: 'POST',
        url: 'http://localhost/ws_mobile_oil/Postos/cadastrar',
        data:{
            nome : 'AutoPosto La em Casa',
            latitude: '19.9000999000',
            longitude: '78.0000999000'
        }
    });


     >adicionar preco a um posto

      $.ajax({ 
        type: 'POST',
        url: 'http://localhost/ws_mobile_oil/Precos/cadastrar',
        data:{
            posto_id: 1,
            valor: 3.50,
            combustivel_id: 1,
            usuario_id: 1
        }
    });
    
```
### SQL Banco

```sql
create table users(
	id serial not null primary key,
	username varchar(50),
	pass varchar(60),
	email varchar(30),
	created timestamp with time zone DEFAULT now()
);

create table combustiveis(
	 id serial not null primary key,
	 nome varchar(20),
 	 subcategoria_id integer
);

create table preferencias(
 	id serial not null,
 	combustivel_id integer not null,
 	user_id integer not null,
 	foreign key (combustivel_id) references combustiveis (id),
 	foreign key (user_id) references users(id)
);

create table postos(
	id serial not null,
	nome varchar(80),
	latitude varchar(25),
	longitude varchar(25),
	endereco varchar(255)
);

```

## Padrões

<ul>
<li>MVC</li>
<li>REST</li>
</ul>

