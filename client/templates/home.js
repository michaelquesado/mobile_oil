Template.home.helpers({
 
  lat: function() { return Geolocation.latLng().lat; },
  lng: function(){ return Geolocation.latLng().lng;  },
  listaDePostos: function() {
  	
  	var url_s = "http://places.api.here.com/places/v1/discover/search?at="+Geolocation.latLng().lat+","+Geolocation.latLng().lng+"&q=petrol-station&app_id=hG4gnJyrmlbNgGscL7Ki&app_code=h3XG36Nr4RgQOjymUTblJQ&pretty&callback=PostoPlaces&Accept-Language=de";
  	console.log(url_s);
  	 HTTP.call("GET", url_s,function (err,result){

 	 		console.log(result.content);
  	 		Session.set('places', result.content);
  	 		
  	 });
 
  	 		return Session.get('places');
		
    },
});

//mobileoilh5m1@gmail.com	
//oilmobileh4m1