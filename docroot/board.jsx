import React from 'react';
import { createRoot } from 'react-dom/client';

function Board(props) {
	return <React.Fragment>
		<h1> Rock Paper Scissors </h1>
		<div>
			<p> Connect phone: <img src="/qr" alt="qr code" /> </p>
		</div>
		<div>
			The board...
		</div>
	</React.Fragment>;
}

const board = document.getElementById('board-main');
const root = createRoot(board);
root.render(<Board />);
