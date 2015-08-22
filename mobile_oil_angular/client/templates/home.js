
Template.home.helpers({
 
  lat: function() { return Geolocation.latLng().lat; },
  lng: function(){ return Geolocation.latLng().lng;  },

    ListaTodosPostos: function(){
      Meteor.call('getPostos',Geolocation.latLng().lat,Geolocation.latLng().lng, function(err, result){

          Session.set('places', JSON.parse(result.content).results.items);  

        });

        return Session.get('places');
    }
	 
});

//mobileoilh5m1@gmail.com	
//oilmobileh4m1