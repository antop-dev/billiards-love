import SignUp from '../view/SignUp';
import DashBoard from '../view/DashBoard';
import GameStatus from '../view/GameStatus';
import GameInfo from '../view/GameInfo';
import GameRank from '../view/GameRank';
import Login from '../view/Login';

export default [
  {
    path: '/login',
    component: Login,
  },
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
