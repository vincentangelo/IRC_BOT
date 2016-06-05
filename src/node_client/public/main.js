document.addEventListener('DOMContentLoaded', function() {
	var app1 = new App();
	app1.init();
});

var Utils = {
	elem:function(e) {
		var elem = document.querySelectorAll(e);
		return elem.length > 1 ? elem : elem[0];
	},
	http: function (method, url, cb) {
		console.log(url);
		var req = new XMLHttpRequest();
		req.onreadystatechange = function() {
			if(req.readyState === 4 && req.status === 200) {
				if(req.responseText.length)
					cb(req.responseText);
			} else {
				return cb(false);
			}
		}
		req.open(method.toUpperCase(), url);
		req.send();
	},
	validate: function(obj) {
		if(obj.nickname.length < 2)
			return false;
		if(obj.channel[0] !== '#')
			return false;
		if(obj.channel.length < 3)
			return false;
		return true;
	}
}


function App () {
	this.messages = [];
	this.channel = "";
	this.messageStatus = 0;
	this.connected = false;
	this.nick = "";
}

App.prototype.init = function() {
	var self = this;
	this.messageTemplate = Utils.elem('#message-template');
	this.startUpTemplate = Utils.elem('#startup-template');

	document.body.innerHTML+= Mustache.render(this.startUpTemplate.innerHTML)

	this.msgForm = Utils.elem('#message-form');
	this.configForm = Utils.elem('#config-form');
  	

	Utils.elem('.status-container>h5').innerHTML = "Not Connected";

	this.configForm.addEventListener('submit', function(e) {
		e.preventDefault();		
		Utils.elem('#join-btn').style.border = '3px solid #f1c40f';
		var form = e.target;
		
		if(
			true
		) {
			
			self.nick = form.elements[0].value;
			self.channel = form.elements[1].value.substr(1);
			console.log(self.channel);
			console.log(form.elements[0].value);
			var count = 0;
			Utils.http('POST', '/join?'+'channel=' + self.channel + '&nick='  + self.nick, function(res) {
				if(++count > 2) {

					console.log('joined');
					console.log(self.nick + "    and " + self.channel);
					Utils.elem('.status-container>h5').innerHTML = 'Channel: '+ self.channel;
					self.connected = true;
					self.startup('hide');
					self.refresh();
				}
			} );

		} 		
		else {
			self.configForm.elements[2].style.border = '3px solid red';
		}
	})
	this.msgForm.addEventListener('submit', this.sendMessage.bind(this));



}


App.prototype.startup = function(cmd) {
	switch (cmd) {
		case 'hide':
			Utils.elem('.startup-container').style.display= 'none';
		break;
	}
}
App.prototype.sendMessage = function (e) {
	var self = this;
		e.preventDefault();
		console.log(this.connected);
		if(!this.connected || this.msgForm.elements[0].value.trim() === '' )
			return false;
		
		 Utils.http('POST', '/send?nick=' + self.nick + '&message=' + self.msgForm.elements[0].value, function(res) {
		 	console.log('sent');
		});
		Utils.elem('.messages-container').innerHTML +=
		 Mustache.render(self.messageTemplate.innerHTML,
		  {name: self.nick + ':',
		  	type: 'sent', 
		  	message: self.msgForm.elements[0].value
		 }
	 	)

		this.msgForm.elements[0].value = "";
}
App.prototype.refresh = function () {
	var self = this;
	Utils.http('GET', '/pull?nick=' + self.nick, function(res) {
		var obj;
		if(res.length > 2) {
			obj = JSON.parse(res);
			obj.messages.forEach( function(elem) {
				console.log(elem.sender);
					Utils.elem('.messages-container').innerHTML +=
					Mustache.render(self.messageTemplate.innerHTML,
					{
						type:'from',
						name: elem.sender.substr(1) + ':', 
						message: elem.content
					}
				)	
			});
		}
	})
	// Utils.elem('.messages-container').innerHTML +=
	// 	Mustache.render(self.messageTemplate.innerHTML,
	// 	{
	// 		sent:false,
	// 		name: 'CASH9' + ':', 
	// 		message: 'Whats up????'
	// 	}
	// )
	setTimeout(this.refresh.bind(this), 3500);
	// Utils.http('get', '/pull?nick=' + self.nick, function(res) {
	// console.log(res);
	// 	res.messages.forEach( function(elem) {
	// 		Utils.elem('.messages-container').innerHTML +=
	// 		Mustache.render(this.messageTemplate.innerHTML,
	// 		{
	// 			name: elem.sender + ':', 
	// 			message: elem.content
	// 		}
	// 	)	
	// });

	// setTimeout(poll, 5000);
	// });
}
