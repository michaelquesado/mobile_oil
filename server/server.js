Meteor.methods({
	'getPostos':function(lat,lng){
		var url_s = "http://places.api.here.com/places/v1/discover/search?at="+lat+","+lng+"&q=petrol-station&app_id=hG4gnJyrmlbNgGscL7Ki&app_code=h3XG36Nr4RgQOjymUTblJQ&pretty";

		return Meteor.http.call("GET", url_s);

	}
})