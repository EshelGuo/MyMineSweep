package com.eshel.minecomputeapp;

import android.os.RemoteException;
import android.util.Log;

import com.eshel.mine.aidl.IMineAidlInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MineAlgorithm {
    public static final int STATE_NOGAME = 0;//游戏未开始
    public static final int STATE_GAMEING = 1;//游戏进行中(已开始)
    public static final int STATE_GAMEOVER = -1;// 游戏结束 , Game Over You Lose
    public static final int STATE_GAMEWIN = 2;// 游戏结束, You Win

    static List<Mine> unKnownMineList;
    static ArrayList<Mine> knownMineList;
    static Mine[][] mines;
    static IMineAidlInterface mineProvider;
    private static String TAG = MineAlgorithm.class.getSimpleName();

    public static boolean start(IMineAidlInterface mineProvider)throws Exception{
        MineAlgorithm.mineProvider = mineProvider;
        int gameState = mineProvider.seeGameState();
        if(gameState == STATE_GAMEING){
            int minesWidth = mineProvider.getMinesWidth();
            int minesHeight = mineProvider.getMinesHeight();
            initMines(minesWidth, minesHeight);

            while (true){
                if(clearMineFromUnknown() == 1){
                    Thread.sleep(1000);
                    clearMineFromKnown();
                }else {
                    return false;
                }
            }

        }
        return false;
    }


    private static int clearMineFromKnown() throws RemoteException {
        int lastKnownMineSize = knownMineList.size();
        for (int i = 0; i < knownMineList.size(); i++) {
            if(i < 0)
                continue;
            Mine mine = knownMineList.get(i);
            List<Mine> unknownMines = mine.getUnknownMines();
            int unKnown = unknownMines.size();
            int knownMine = mine.getKnownMineSize();

            {
//                boolean _continue = true;
                for (int j = 1; j <= 8; j++) {
                    if(unKnown+knownMine == j && mine.mineNumber == j){
                        for (Mine mineMine : unknownMines) {
                            if(mineMine.mineNumber == -2) {
                                mineMine.mineNumber = -1;//-1 代表雷
                                mineProvider.longClickMine(mineMine.x, mineMine.y);
                                unKnownMineList.remove(mineMine);
                                i--;
                            }
                        }
                        knownMineList.remove(mine);
//                        _continue = false;
                        break;
                    }
                }

//                if(!_continue)
//                    break;

                if(knownMine == mine.mineNumber){
                    for (Mine unknownMine : unknownMines) {
                        clickMine(unknownMine);
                    }
                }else {
                    List<Mine> knownMines = MineGroup.getInstance().init(unknownMines, mine.mineNumber - mine.getKnownMineSize()).getKnownMines();
                    if(knownMines != null ){
                        for (Mine knownMine1 : knownMines) {
                            if(knownMine1.mineNumber == -1){
                                boolean remove = unKnownMineList.remove(knownMine1);
                                if(remove)
                                    mineProvider.longClickMine(knownMine1.x, knownMine1.y);
                            }else {
                                clickMine(knownMine1);
                            }
                        }
                    }
                }
            }

        }

        if(lastKnownMineSize != knownMineList.size())
            return clearMineFromKnown();

        return 0;
    }

    private static void clickMine(Mine noMine) throws RemoteException {
        List<String> mineSList = mineProvider.clickMine(noMine.x, noMine.y);
        for (String s : mineSList) {
            String[] split = s.split("=");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            int mineNumber = Integer.valueOf(split[2]);
            Mine mine = mines[y][x];
            mine.mineNumber = mineNumber;
            unKnownMineList.remove(mine);
            if(mineNumber > 0)
                knownMineList.add(mine);
        }
    }

    /**
     * 随机扫雷
     * @return -1 gameover  ,  1 可以根据已知判断扫雷
     */
    private static int clearMineFromUnknown() throws RemoteException {
        List<String> results = clickRandomMine();
        if(results == null || results.size() == 0)
            return -1;
        Log.i(TAG, results.toString());
        boolean fristNotNull = results.get(0) != null;
        Log.i(TAG, String.valueOf(fristNotNull));
        if((results != null && results.size() > 0 && fristNotNull && Integer.parseInt(results.get(0).split("=")[2]) != -1)){
            for (String mineS : results) {
                String[] mine = mineS.split("=");
                int mineX = Integer.valueOf(mine[0]);
                int mineY = Integer.valueOf(mine[1]);
                int mineNum = Integer.valueOf(mine[2]);
                Mine _mine = mines[mineY][mineX];
                _mine.mineNumber = mineNum;
                unKnownMineList.remove(_mine);
                if(mineNum != 0){
                    knownMineList.add(_mine);
                }
            }
            return 1;
        }
        return -1;
    }

    static Random mRandom = new Random();

    private static List<String> clickRandomMine() throws RemoteException {
        Mine randomMine = getRandomMine();
        if(randomMine == null)
            return null;
        return mineProvider.clickMine(randomMine.x,randomMine.y);
    }
    private static Mine getRandomMine(){
        if(unKnownMineList == null || unKnownMineList.size() == 0)
            return null;
        int next = mRandom.nextInt(unKnownMineList.size());
        return unKnownMineList.remove(next);
    }

    private static void initMines(int minesWidth, int minesHeight) {
        unKnownMineList = new LinkedList<>();
        knownMineList = new ArrayList<>(minesHeight*minesWidth/4);
        mines = new Mine[minesHeight][minesWidth];
        for (int i = 0; i < minesHeight; i++) {
            for (int j = 0; j < minesWidth; j++) {
                Mine mine = new Mine();

                mine.x = j;
                mine.y = i;

                if(j > 0) {
                    mine.LEFT = mines[i][j - 1];
                    mines[i][j - 1].RIGHT = mine;
                    if(i > 0){
                        mine.LEFT_TOP = mines[i-1][j-1];
                        mines[i-1][j-1].RIGHT_BOTTOM = mine;
                    }
                }

//                    if(j < minesWidth - 1)
//                        mine.RIGHT = mines[i][j+1];

                if(i > 0) {
                    mine.TOP = mines[i - 1][j];
                    mines[i - 1][j].BOTTOM = mine;

                    if(j < minesWidth - 1) {
                        mine.RIGHT_TOP = mines[i - 1][j + 1];
                        mines[i - 1][j + 1].LEFT_BOTTOM = mine;
                    }
                }

                mines[i][j] = mine;
                unKnownMineList.add(mine);
            }
        }
    }
}
