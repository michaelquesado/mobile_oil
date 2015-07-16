Template.preferencias.helpers({


	Combustiveis: function(){
		Meteor.call('getCombustiveis', function(err, result){

			Session.set('combustiveis',JSON.parse(result.content));

		});

		return Session.get('combustiveis');
		
	}

});

var toggle = 1;
var i = 0;
var preferencias = new Array();

Template.preferencias.events({

	'click  li': function(event){

		var active_class = ' active ';
		
		toggle++;
		var id =  event.target.id;

		if(toggle % 2 == 0){

			$(event.target).find('#toggle'+ event.target.id).addClass(active_class);					
			
			
			if(preferencias.indexOf(id) == -1){
				preferencias[i] = id;
				i++;
			}

		}else{

			$(event.target).find('#toggle'+ event.target.id).removeClass(active_class);
			$.each(preferencias, function(k,v){
				if(v == id){
					delete preferencias[k];
				}
			});
		}


		console.log(preferencias);
		Session.set('preferencias', preferencias);
		return false;
	},

	'click button ': function(event){

		var preferencias = Session.get('preferencias');
		var person = Session.get('person');


		Meteor.call('CadastrarPreferenciasDeCombustivel',
			person.id,
			preferencias,
			function(err,result){
				console.log(result.content);
			}
		);
	}

	

});