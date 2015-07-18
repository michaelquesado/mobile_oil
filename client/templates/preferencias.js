Template.preferencias.helpers({


	Combustiveis: function(){
		Meteor.call('getCombustiveis', function(err, result){

			Session.set('combustiveis',JSON.parse(result.content));

		});

		return Session.get('combustiveis');
		
	}

});


var preferencias = new Array();	
var i = 0;


Template.preferencias.events({




	'submit' : function(event){

		event.preventDefault();

		$.each($(":checkbox"), function(){
			if(this.checked){
				preferencias[i] = this.value;
				i++;
			}
		});

		var person = Session.get('person');
		
		Meteor.call('CadastrarPreferenciasDeCombustivel',
			person.id,
			preferencias,
			function(err,result){
				
				(JSON.parse(result.content) === 'sucesso')? Router.go('/home') : alert (result.content);
			}
			);
	},


	

});