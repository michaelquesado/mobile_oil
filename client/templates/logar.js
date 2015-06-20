Template.logar.events({

	'submit': function(event, template){

		event.preventDefault();

		var email = $(event.target).find('[name=email]').val();
		var pass = $(event.target).find('[name=pass]').val();

		

		Meteor.call('Logar', 
			email,
			pass,
			function(err,result){
				if(err){
					alert(err.reason);
				}else{
					Session.set('person',JSON.parse(result.content));
				
				}
			});


	}


});