Template.preferencias.helpers({


	Combustiveis: function(){
		Meteor.call('getCombustiveis', function(err, result){

			Session.set('combustiveis',JSON.parse(result.content));

		});

		return Session.get('combustiveis');
		
	}

});

var toggle = 0;
var preferencias = Array();

Template.preferencias.events({
	
	'click  .toggle': function(event){
		var active_class = 'toggle ';

		if(toggle % 2 == 0){
			active_class +=  ' active';
			console.log(event.target);
			preferencias[toggle] = event.current;
		}
		//console.log(preferencias);
 		toggle++;

 		
		event.currentTarget.className = active_class;
		Session.set('preferencias',preferencias);
		return false;
	},

	'click button ': function(event){
		//console.log(Session.get('preferencias'));

	}

	

});