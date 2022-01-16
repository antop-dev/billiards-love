import SignUp from '../view/UserRegister';
import DashBoard from '../view/DashBoard';
import MatchStatus from '../view/ContestApp';
import ContestInfo from '../view/ContestInfo';
import ContestRank from '../view/ContestRank';
import ContestMatchList from '../view/ContestMatchList';
import ContestMatchDetail from '../view/ContestMatchDetail';
import ContestJoin from '../view/ContestJoin';

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
        path: '/',
        component: ContestInfo,
      },
      {
        name: 'join',
        path: 'join',
        component: ContestJoin,
      },
      {
        name: 'rank',
        path: 'rank',
        component: ContestRank,
      },
      {
        name: 'match',
        path: 'match',
        component: ContestMatchList,
      },
      {
        name: 'matchDetail',
        path: 'match/:matchId',
        component: ContestMatchDetail,
      },
    ],
  },
];
