const webpack = require('webpack');
const path = require('path');

module.exports = {
  devtool: 'inline-source-map',
  devServer: {
    historyApiFallback: true,
    inline: true,
    hot: true,
    filename: '[name].js',
    host: 'localhost',
    port: 9000,
    proxy: {
      '**': 'http://localhost:8080/',
    },
  },
};
