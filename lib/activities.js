  var getLocationPlace = function(loc) {
    return loc.coords.latitude;
  /*  var url = 'https://api.twitter.com/1.1/geo/reverse_geocode.json'
      + '?granularity=neighborhood'
      + '&max_results=1'
      + '&accuracy=' + loc.coords.accuracy
      + '&lat=' + loc.coords.latitude
      + '&long=' + loc.coords.longitude;
    
    var response = HTTP.get(url,
                            {npmRequestOptions: { oauth: twitterOauth() } });

    if (response.statusCode === 200 && response.data) {
      var place = _.find(response.data.result.places, function(place) {
        return place.place_type === 'neighborhood';
      });
      
      return place && place.full_name;
      
    }*/
  }