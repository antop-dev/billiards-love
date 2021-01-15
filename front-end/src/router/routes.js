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
        path: 'info',
        component: GameInfo,
      },
      {
        path: 'rank',
        component: GameRank,
      },
    ],
  },
];
