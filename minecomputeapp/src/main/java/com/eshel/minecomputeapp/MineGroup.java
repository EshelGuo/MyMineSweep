package com.eshel.minecomputeapp;

import java.util.ArrayList;
import java.util.List;

public class MineGroup {

private static MineGroup INSTANCE;
private MineGroup(){}
public static MineGroup getInstance(){
    if(INSTANCE == null){
        synchronized (MineGroup.class){
            if(INSTANCE == null)
                INSTANCE = new MineGroup();
        }
    }
    return INSTANCE;
}

public static void destory(){
    INSTANCE = null;
}

public MineGroup init(List<Mine> unknownMines, int expectMineNumber){
    this.unknownMines = unknownMines;
    this.expectMineNumber = expectMineNumber;
    return this;
}
    private List<Mine> unknownMines;
    private int expectMineNumber;

    public List<Mine> getKnownMines(){
        if(unknownMines != null){
            for (Mine unknownMine : unknownMines) {
                List<Mine> notMines = unknownMine.getNotMine();
                for (Mine notMine : notMines) {
                    List<Mine> unknownMines = notMine.getUnknownMines();
                    if(contains(unknownMines)){
                        List<Mine> knownGroup = unknownMines;
                        int knownGroupNum = notMine.mineNumber - notMine.getKnownMineSize();
                        if(knownGroup.size() < this.unknownMines.size()){
                            if(knownGroupNum == expectMineNumber){
                                return getDiffentMines(knownGroup);
                            }else if(expectMineNumber - knownGroupNum == this.unknownMines.size() - knownGroup.size()){
                                List<Mine> mines = getDiffentMines(knownGroup);
                                for (Mine mine : mines) {
                                    mine.mineNumber = -1;
                                }
                                return mines;
                            }

                        }
                    }
                }
            }
        }
        return null;
    }

    private List<Mine> getDiffentMines(List<Mine> target){
        List<Mine> results = new ArrayList<>();
        for (Mine unknownMine : unknownMines) {
            if(!target.contains(unknownMine))
                results.add(unknownMine);
        }
        return results;
    }

    private boolean contains(List<Mine> mines){
        return unknownMines.containsAll(mines);
    }

}
