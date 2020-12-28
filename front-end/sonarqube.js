const scanner = require('sonarqube-scanner');

scanner(
  {
    serverUrl: 'https://sonarcloud.io',
    options: {
      'sonar.organization': 'antop-dev-github',
      'sonar.projectKey': 'antop-dev_billiards-love:front-end',
      'sonar.projectName': 'billiards-love:front-end',
      'sonar.sourceEncoding': 'UTF-8',
      'sonar.sources': 'src',
    },
  },
  () => process.exit(),
);
