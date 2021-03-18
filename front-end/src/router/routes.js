import SignUp from '../view/SignUp';
import DashBoard from '../view/DashBoard';
import MatchStatus from '../view/MatchStatus';
import MatchInfo from '../view/MatchInfo';
import MatchRank from '../view/MatchRank';
import MatchChart from '../view/MatchChart';

export default [
  {
    path: '/register',
    component: SignUp,
  },
  {
    path: '/dashboard',
    component: DashBoard,
  },
  {
    path: '/match/:id',
    component: MatchStatus,
    children: [
      {
        name: 'info',
        path: 'info',
        component: MatchInfo,
      },
      {
        name: 'rank',
        path: 'rank',
        component: MatchRank,
      },
      {
        name: 'chart',
        path: 'chart',
        component: MatchChart,
      },
    ],
  },
];
