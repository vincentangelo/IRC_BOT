
'use strict';
const HOST = 'irc.freenode.net';
const net = require('net');
const EventEmitter = require('events');

class Message {
	constructor(sender, content) {
		this.sender = sender;
		this.content = content;
	}
}
module.exports = class Client extends EventEmitter {
	constructor(channel, nick) {
		super();
		let self = this;
		this.channel = channel;
		this.nick = nick;
		this.connected = false;
		this.messages = [];

		this.socket = new net.Socket();
		this.socket.connect(6667, HOST, () => {
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
		if(data.toString().indexOf('004') != -1 && !this.connected) {
			this.connected = true;
			this.socket.write('JOIN '+ this.channel +'\r\n');
			this.emit('connected');

		} else if(data.toString().toLowerCase().substr(0, 4) === 'ping') {
			console.log('PING');

			this.socket.write('PONG ' + data.toString().split()[2] +'\r\n'	);
		} else if(data.toString().indexOf('PRIVMSG') != -1) {
			let str = data.toString();
			console.log( str.split(':')[2]);
			var pos = data.toString().substr(1).indexOf(':')+1;
			this.messages.push( new Message(data.toString().split('!')[0],data.toString().substr(pos)));

		} else {
			console.log(data.toString());
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
		console.log('sending message');
		this.socket.write('PRIVMSG ' + this.channel + ' :' + msg + '\r\n');
	}
}

