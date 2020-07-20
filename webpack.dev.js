const webpack = require('webpack');
const path = require('path');

module.exports = {
  devtool: 'inline-source-map',
  devServer: {
    historyApiFallback: true,
    inline: true,
    hot: true,
    contentBase: path.resolve(__dirname, 'src/main/resources/static'),
    filename: '[name].js',
    publicPath: '/static/',
    host: 'localhost',
    port: 8081,
    /* proxy: {
      '**': 'http://localhost:8080/'
    }, */
  },
};
