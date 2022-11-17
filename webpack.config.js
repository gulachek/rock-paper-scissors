const path = require('path');

module.exports = {
	experiments: {
		outputModule: true
	},
	entry: {
		board: path.join(__dirname, 'docroot/board.jsx'),
		controller: path.join(__dirname, 'docroot/controller.jsx'),
	},
	output: {
		module: true,
		filename: '[name].js',
		path: path.join(__dirname, 'src/main/resources/files/dist')
	},
	module: {
		rules: [
			{
				test: /\.jsx?$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader',
					options: {
						presets: ['@babel/preset-env', '@babel/preset-react']
					}
				}
			}
		]
	}
};
