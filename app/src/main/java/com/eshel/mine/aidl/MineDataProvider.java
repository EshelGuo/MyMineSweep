package com.eshel.mine.aidl;

import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

import com.eshel.mine.Mine;
import com.eshel.mine.MineManager;
import com.eshel.mine.view.MineView;

import java.util.ArrayList;
import java.util.List;

public class MineDataProvider {

    private static final String TAG = "Mine";
    /**
     * 0 游戏未开始
     * 1 游戏进行中(已开始)
     * -1 游戏结束 , Game Over You Lose
     * 2  游戏结束, You Win
     */
    public static int gameState;
    public static MineManager mManager;
    public static boolean gameStarted;

    public static ArrayList<String> clickMines;
    static boolean clicking;
    /**
     * 返回 false gameover , 返回true没有gameover
     */
    public static List<String> clickMine(int x, int y){
        clicking = true;
        clickMines = new ArrayList<>();
        Mine mine = mManager.getMine(x, y);
        if(mine == null)
            return null;
        final MineView mineView = mine.mMineView;
        if(mineView != null){
            //此处在子线程中执行, 所以需要发消息到主线程
            mineView.post(new Runnable() {
                @Override
                public void run() {
                    mineView.performClick();
                    clicking = false;
                }
            });

            while (clicking)
                Thread.yield();
        }

//        if(mine.type == Mine.TYPE_MINE)
//            return clickMines;
        Log.i(TAG, clickMines.toString());
        return clickMines;
    }

    public static int getUnKnown(int x, int y){
        Mine mine = mManager.getMine(x, y);
        if(mine == null)
            return 0;
        return mine.getUnknownMineNum();
    }

    public static void longClickMine(int x, int y){
        Mine mine = mManager.getMine(x, y);
        if(mine == null)
            return;
        final MineView mineView = mine.mMineView;
        if(mineView != null){
            mineView.post(new Runnable() {
                @Override
                public void run() {
                    mineView.performLongClick();
                }
            });
        }
    }

    /**
     * 0 - 8 代表 周围雷的数量
     * -1代表本身是雷
     * 10 代表 未知
     * 11 代表被标记
     * 12 代表 ? 标记
     * -2 代表错误
     */
    public static int lookMineType(int x, int y){
        Mine mine = mManager.getMine(x, y);
        if(mine == null)
            return -2;
        if(!mine.isShowed){
            return mine.flag;
        }else {
            return mine.getMineNum();
        }
    }

    public static int getMinesWidth(){
        if(mManager.mMines != null){
            return mManager.mMines[0].length;
        }
        return 0;
    }

    public static int getMinesHeight(){
        if(mManager.mMines != null){
            return mManager.mMines.length;
        }
        return 0;
    }
}
