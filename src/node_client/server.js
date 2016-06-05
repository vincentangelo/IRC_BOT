'use strict';

const Client = require('./irc');
const fs = require('fs');
const express = require('express');
let app = express();

let users = [];
app.use( (req, res, next) => {
	console.log(req.url + '\n');
	next();
})
app.post('/join', (req, res) => {
	console.log(req.query.channel);
	let channel = '#'+req.query.channel;
	let nick = req.query.nick;
	console.log(channel);
	users.push(new Client (channel, nick ) );

	findUser(nick).on('connected', ()=> {
		console.log('connected to channel ');
		res.sendStatus(200);
	});
});

app.post('/send', (req, res) => {
	let message = req.query.message;
	let nick = req.query.nick;

	if(!nick || !message || users.length === 0)
		res.sendStatus(400);
	
	findUser(nick).writeData(message);
	res.sendStatus(200);
});
app.get('/pull', (req, res) => {
	let nick = req.query.nick;
	if(users.length === 0) {
		res.sendStatus(400);
	}
	let messages = findUser(nick).getMessages();
	
	let obj = {
		messages
	}
	console.log(obj);
	res.json(obj);

});
app.use(express.static('public'));
	

function findUser( nick ) {
	let user = null;
	users.forEach( ( e) => {
		if(e.nick === nick)
			user =  e;
	})
	return user ? user : "not found";
}
app.listen(80, '0.0.0.0');
