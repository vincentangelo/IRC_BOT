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
		var req = new XMLHttpRequest();
		req.onreadystatechange = function() {
			if(req.readyState === 4 && req.status === 200) {
				cb(JSON.parse(req.responseText));
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
	this.nickname = "";
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
		self.nickname = self.configForm.elements[0].value;
		self.channel = self.configForm.elements[1].value;
		
		if(
			Utils.validate(
			 	{
				 	nickname: self.nickname,
				 	channel:self.channel
			 	} 
		 	)
		) {

			self.startup('hide');
			Utils.elem('.status-container>h5').innerHTML = 'Channel: '+ self.channel.substring(1, self.channel.length);
			self.connected = true;
		} 		
		else {
			self.configForm.elements[2].style.border = '3px solid red';
		}
	})
	this.msgForm.addEventListener('submit', this.sendMessage.bind(this));


	window.setInterval(function() {
		self.refresh();
	}, 50);

}


App.prototype.startup = function(cmd) {
	switch (cmd) {
		case 'hide':
			Utils.elem('.startup-container').style.display= 'none';
		break;
	}
}
App.prototype.sendMessage = function (e) {
		e.preventDefault();
		console.log(this.connected);
		if(!this.connected || this.msgForm.elements[0].value.trim() === '' )
			return false;
		Utils.elem('.messages-container').innerHTML +=
		 Mustache.render(this.messageTemplate.innerHTML,
		  {name: this.nickname + ':', 
		  	message: this.msgForm.elements[0].value
		  }
		)
		this.msgForm.elements[0].value = "";
}
App.prototype.refresh = function () {
	Utils.http('get', 'http://www.yahoo.com', function(res) {
		console.log(res);
	});
}

