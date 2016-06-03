'use strict';

const Client = require('./irc');
const fs = require('fs');
const express = require('express');
let app = express();

let users = [];

app.post('/join', (req, res) => {
	
	let channel = req.query.channel;
	let nick = req.query.nick;
	debugger;
	users.push(new Client (channel, nick ) );
	console.log("connected " + users[0] + " :  "+users.length);
	console.log(users[0].nick + users[0].channel+ " channe is < ");
	res.sendStatus(200);
});

app.post('/send', (req, res) => {
	
	let message = req.query.message;
	let nick = req.query.nick;
	if(!nick || !message)
		res.sendStatus(400);
	debugger;
	findUser(nick).writeData(message);
	res.sendStatus(200);
});
app.get('/pull', (req, res) => {
	let nick = req.query.nick;
	let messages = findUser(nick).getMessages();

	let obj = {
		messages
	}
	res.json(obj);

});
app.use(express.static('public'));
	

function findUser( nick ) {
	users.forEach(function(elem) {
		console.log(elem.nick + " nick");
		if(elem.nick === nick ) {
			return elem;
		}
	});
}
app.listen(80, '0.0.0.0');
