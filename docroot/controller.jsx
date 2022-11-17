import React, { useCallback } from 'react';
import { createRoot } from 'react-dom/client';

function Incrementor(props) {
	const inc = useCallback(() => {
		const xhr = new XMLHttpRequest();
		xhr.open('GET', '/increment');
		xhr.send();
	}, []);

	return <button onClick={inc}> Increment </button>;
}

function Controller(props) {
	return <React.Fragment>
		<h1> Rock Paper Scissors </h1>
		<p> Increment number: <Incrementor /> </p>
	</React.Fragment>;
}

const controller = document.getElementById('controller-main');
const root = createRoot(controller);
root.render(<Controller />);
