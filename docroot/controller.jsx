import React from 'react';
import { createRoot } from 'react-dom/client';

function Controller(props) {
	return <React.Fragment>
		<h1> Rock Paper Scissors </h1>
		<p>
			The controller...
		</p>
	</React.Fragment>;
}

const controller = document.getElementById('controller-main');
const root = createRoot(controller);
root.render(<Controller />);
