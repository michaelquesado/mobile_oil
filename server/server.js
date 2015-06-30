Meteor.methods({
	'getPostos':function(lat,lng){
		var url_s = "http://places.api.here.com/places/v1/discover/search?at="+lat+","+lng+"&q=petrol-station&app_id=hG4gnJyrmlbNgGscL7Ki&app_code=h3XG36Nr4RgQOjymUTblJQ&pretty";

		return Meteor.http.call("GET", url_s);

	},

	'Logar': function(email, pass){
		return HTTP.call(
			"POST","http://localhost/ws_mobile_oil/Login/logar",
			{params: {email: email, pass: pass}} 
		);
	},

	'CadastrarPreferenciasDeCombustivel' : function(user_id){
		var url = "http://localhost/ws_mobile_oil/Preferencias/getPreferenciasUsuarioId/" + user_id;
		return Meteor.http.call('GET', url);
	},

	'getCombustiveis': function(){
		return Meteor.http.call("GET", "http://localhost/ws_mobile_oil/Combustiveis/getCombustiveis");
	}
	
});