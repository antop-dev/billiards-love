const path = require('path');

module.exports = {
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
};
