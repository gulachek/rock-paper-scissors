import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';

function CurrentNumber(props) {
	const { connection } = props;
	const [n, setN] = useState(NaN);

	useEffect(() => {
		const onmessage = (e) => {
			setN(parseInt(e.data));
		};

		connection.addEventListener('message', onmessage);
		return () => {
			connection.removeEventListener('message', onmessage);
		};

	}, []);

	const nStr = isNaN(n) ? 'Loading...' : n.toString();

	return <span> {nStr} </span>;
}

function Board(props) {
	const { connection } = props;

	return <React.Fragment>
		<h1> Rock Paper Scissors </h1>
		<div>
			<p> Connect phone: <img src="/qr" alt="qr code" /> </p>
		</div>
		<div>
			<p> Number is: <CurrentNumber connection={connection} /> </p>
		</div>
	</React.Fragment>;
}

function updates() {
	const url = new URL(window.location.href);
	url.protocol = 'ws:';
	url.pathname = '/updates';
	return new WebSocket(url.href);
}

const board = document.getElementById('board-main');
const root = createRoot(board);
root.render(<Board connection={updates()} />);
