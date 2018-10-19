package com.eshel.mine.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

public class MineAidlService extends Service {

    private IBinder mineBinder = new IMineAidlInterface.Stub() {

        @Override
        public boolean gameStarted() throws RemoteException {
            boolean gameStarted = MineDataProvider.gameState == 1;
            Log.i("Mine",gameStarted+"");
            return gameStarted;
        }

        @Override
        public int getMinesWidth() throws RemoteException {
            return MineDataProvider.getMinesWidth();
        }

        @Override
        public int getMinesHeight() throws RemoteException {
            return MineDataProvider.getMinesHeight();
        }

        @Override
        public int getUnKnown(int x, int y) throws RemoteException {
            return MineDataProvider.getUnKnown(x, y);
        }

        @Override
        public List<String> clickMine(int x, int y) throws RemoteException {
            return MineDataProvider.clickMine(x,y);
        }

        @Override
        public void longClickMine(int x, int y) throws RemoteException {
            MineDataProvider.longClickMine(x,y);
        }

        @Override
        public int lookMineType(int x, int y) throws RemoteException {
            return MineDataProvider.lookMineType(x, y);
        }

        @Override
        public int seeGameState() throws RemoteException {
            return MineDataProvider.gameState;
        }

        @Override
        public String test(){
            return "扫雷 Demo";
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mineBinder;
    }
}
