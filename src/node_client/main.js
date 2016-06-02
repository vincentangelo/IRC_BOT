'use strict'
const net = require('net');

let loggedIn = false;

const client  = new net.Socket();
client.connect(6667, 'irc.freenode.net', () => {
	console.log('connected');
	client.write('NICK kkkk9999 \r\n');
	client.write("USER kkkk9999 8 * : Testing Bot Connection\r\n");
});
client.on('data', (data) => {
	console.log(data.toString());	
	if(data.toString().indexOf('004') != -1) {
		loggedIn = true;
		client.write("JOIN #beginner \r\n");

	} else if(data.toString().toLowerCase().substr(0, 4) === 'ping') {
		console.log('PING');

		client.write('PONG ' + data.toString().split()[2] +'\r\n'	);
	}
});
client.on('close', () => {
	console.log('closed');
});
client.on('error', (err) => {
	console.log(err);
})