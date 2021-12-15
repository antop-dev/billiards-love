import SignUp from '../view/SignUp';
import DashBoard from '../view/DashBoard';
import MatchStatus from '../view/MatchStatus';
import MatchInfo from '../view/MatchInfo';
import MatchRank from '../view/MatchRank';
import MatchChart from '../view/MatchChart';
import MatchChartDetail from '../view/MatchChartDetail';

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
    path: '/contest/:id',
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
        name: 'match',
        path: 'match',
        component: MatchChart,
      },
      {
        name: 'matchDetail',
        path: 'match/:matchId',
        component: MatchChartDetail,
      },
    ],
  },
];
