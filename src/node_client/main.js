'use strict';

const Client = require('./irc');
const fs = require('fs');
const express = require('express');
let app = express();

let users = [];

app.post('/join', (req, res) => {
	let channel = req.query.channel;
	let nick = req.query.nick;
	users.push(new Client (channel, nick ) );
	res.sendStatus(200);
});

app.post('/send', (req, res) => {
	let message = req.query.message;
	let nick = req.query.nick;
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
		if(elem.nick === nick ) {
			return elem;
		}
	});
}
app.listen(80, '0.0.0.0');
