const webpack = require('webpack');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const { VueLoaderPlugin } = require('vue-loader');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpackMerge = require('webpack-merge');
const path = require('path');

const config = {
  entry: {
    app: path.resolve(__dirname, 'src/main/front/main.js')
  },
  output: {
    path: path.resolve(__dirname, 'src/main/resources/static'),
    filename: 'js/[name].js'
  },
  resolve: {
    extensions: ['.js', '.vue'],
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
        include: [ path.resolve(__dirname, 'src') ],
      },
      {
        test: /\.css$/,
        use: [MiniCssExtractPlugin.loader, 'css-loader'],

      },
      {
        test: /\.scss$/,
        use: [MiniCssExtractPlugin.loader, 'css-loader', 'sass-loader'],
      },
      {
        test: /\.(png|jpg|jpeg|gif|svg|ico)$/,
        loader: 'file-loader',
        options: {
          name: '[name].[ext]',
          outputPath: './src/main/resources/static/images/',
        },
      },
      {
        test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
        loader: 'url-loader',
        options: {
          name: '[name].[ext]',
          outputPath: './src/main/resources/static/fonts/',
        },
      },
    ],
  },
  plugins: [
    new webpack.ProvidePlugin({
      _ : "lodash"
    }),
    new VueLoaderPlugin(),
    new MiniCssExtractPlugin({
      filename: '[name].css',
    }),
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, 'src/main/front/main.html')
    })
  ],
};

module.exports = function(env) {
  return webpackMerge(config, require(`./webpack.${env}.js`));
};
