import SignUp from '../view/SignUp';
import DashBoard from '../view/DashBoard';
import GameStatus from '../view/GameStatus';
import GameInfo from '../view/GameInfo';
import GameRank from '../view/GameRank';

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
    path: '/game',
    component: GameStatus,
    children: [
      {
        name: 'info',
        path: ':id/info',
        component: GameInfo,
      },
      {
        name: 'rank',
        path: ':id/rank',
        component: GameRank,
      },
    ],
  },
];
