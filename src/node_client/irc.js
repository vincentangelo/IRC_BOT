
'use strict';
const HOST = 'irc.freenode.net';
const net = require('net');


class Message {
	constructor(sender, content) {
		this.sender = sender;
		this.content = content;
	}
}
module.exports = class Client {
	constructor(channel, nick) {
		let self = this;
		this.channel = channel;
		this.nick = nick;
		this.connected = false;
		this.messages = [];

		this.socket = new net.Socket();
		this.socket.connect(6667, HOST, () => {
			console.log('connected');
			self.socket.write('NICK '+ self.nick +' \r\n');
			self.socket.write('USER '+ self.nick +' 8 * : Testing Bot Connection\r\n');
		});

		self.socket.on('data', self.readData.bind(this));

		self.socket.on('close', () => {
			console.log('closed');
			return false;
		});
		self.socket.on('error', (err) => {
			console.log('error');
			return false;
		})
	}
	readData(data) {

		if(data.toString().indexOf('004') != -1) {
			this.connected = true;
			this.socket.write('JOIN '+ this.channel +'\r\n');

		} else if(data.toString().toLowerCase().substr(0, 4) === 'ping') {
			console.log('PING');

			this.socket.write('PONG ' + data.toString().split()[2] +'\r\n'	);
		} else if(data.toString().indexOf('PRIVMSG') != -1) {
			this.messages.push( new Message(data.toString().split('!')[0],  data.toString() ) );
		}
	}
	getMessages() {
		let messages = this.messages;
		//empty buffered messages
		this.messages = [];
		return messages;
	}
	writeData(msg) {
		if(!this.connected)
			return false;
		this.socket.write('PRIVMSG ' + this.channel + ' :'+ msg +'\r\n');
	}
}

