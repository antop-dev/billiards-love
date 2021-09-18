const path = require('path');

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:9000',
      },
    },
  },

  chainWebpack: config => {
    config.externals({
      Kakao: 'Kakao',
    });
  },

  configureWebpack: {
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src/components'),
      },
    },
  },
  transpileDependencies: ['vuetify'],
};
