package com.eshel.minecomputeapp;

import java.util.ArrayList;
import java.util.List;

public class Mine {

    private static final int TYPE_UNKNOWN = 0;
    private static final int TYPE_KNOWN = 1;
    private static final int TYPE_MINE = -1;
//    int type;

    int x;
    int y;
    int mineNumber = -2;
    public void fromJson(String json){
        String[] split = json.split("=");
        x = Integer.parseInt(split[0]);
        y = Integer.parseInt(split[1]);
        mineNumber = Integer.parseInt(split[2]);
    }

    Mine TOP;
    Mine LEFT;
    Mine RIGHT;
    Mine BOTTOM;

    Mine LEFT_TOP;
    Mine RIGHT_TOP;
    Mine LEFT_BOTTOM;
    Mine RIGHT_BOTTOM;

    public int getUnknownMineNum(){
        int num = 0;

        if(isUnknownMine(LEFT))
            num++;
        if(isUnknownMine(RIGHT))
            num++;
        if(isUnknownMine(TOP))
            num++;
        if(isUnknownMine(BOTTOM))
            num++;
        if(isUnknownMine(LEFT_TOP))
            num++;
        if(isUnknownMine(RIGHT_TOP))
            num++;
        if(isUnknownMine(LEFT_BOTTOM))
            num++;
        if(isUnknownMine(RIGHT_BOTTOM))
            num++;

        return num;
    }

    public List<Mine> getUnknownMines(){
        ArrayList<Mine> mines = new ArrayList<>(4);
        if(isUnknownMine(LEFT))
            mines.add(LEFT);
        if(isUnknownMine(RIGHT))
            mines.add(RIGHT);
        if(isUnknownMine(TOP))
            mines.add(TOP);
        if(isUnknownMine(BOTTOM))
            mines.add(BOTTOM);
        if(isUnknownMine(LEFT_TOP))
            mines.add(LEFT_TOP);
        if(isUnknownMine(RIGHT_TOP))
            mines.add(RIGHT_TOP);
        if(isUnknownMine(LEFT_BOTTOM))
            mines.add(LEFT_BOTTOM);
        if(isUnknownMine(RIGHT_BOTTOM))
            mines.add(RIGHT_BOTTOM);
        return mines;
    }

    public int getKnownMineSize(){
        int num = 0;

        if(isKnownMine(LEFT))
            num++;
        if(isKnownMine(RIGHT))
            num++;
        if(isKnownMine(TOP))
            num++;
        if(isKnownMine(BOTTOM))
            num++;
        if(isKnownMine(LEFT_TOP))
            num++;
        if(isKnownMine(RIGHT_TOP))
            num++;
        if(isKnownMine(LEFT_BOTTOM))
            num++;
        if(isKnownMine(RIGHT_BOTTOM))
            num++;

        return num;
    }

    /**
     * 获取周围非雷已知的 Mine
     */
    public List<Mine> getNotMine(){
        ArrayList<Mine> mines = new ArrayList<>(4);
        if(notMine(LEFT))
            mines.add(LEFT);
        if(notMine(RIGHT))
            mines.add(RIGHT);
        if(notMine(TOP))
            mines.add(TOP);
        if(notMine(BOTTOM))
            mines.add(BOTTOM);
        if(notMine(LEFT_TOP))
            mines.add(LEFT_TOP);
        if(notMine(RIGHT_TOP))
            mines.add(RIGHT_TOP);
        if(notMine(LEFT_BOTTOM))
            mines.add(LEFT_BOTTOM);
        if(notMine(RIGHT_BOTTOM))
            mines.add(RIGHT_BOTTOM);
        return mines;
    }

    private boolean notMine(Mine mine){
        if(mine == null)
            return false;
        return mine.mineNumber > 0;
    }

    private static boolean isUnknownMine(Mine mine){
        return mine != null && mine.mineNumber == -2;
    }
    private static boolean isKnownMine(Mine mine){
        return mine != null && mine.mineNumber == -1;
    }
}
