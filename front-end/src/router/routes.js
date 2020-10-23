import SignUp from '../view/SignUp';
import DashBoard from '../view/DashBoard';
import GameStatus from '../view/GameStatus';
import GameInfo from '../view/GameInfo';
import GameRank from '../view/GameRank';

export default [
  {
    path: '/sign-up',
    component: SignUp,
  },
  {
    path: '/dashboard',
    component: DashBoard,
  },
  {
    path: '/game-status',
    component: GameStatus,
    children: [
      {
        path: '/game-info',
        components: GameInfo,
      },
      {
        path: '/game-rank',
        components: GameRank,
      },
    ],
  },
];
