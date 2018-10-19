package com.eshel.minecomputeapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.eshel.mine.aidl.IMineAidlInterface;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MineComputeApp";
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mineProvider = IMineAidlInterface.Stub.asInterface(service);
            Log.i(TAG, "onServiceConnected: success");
            connected();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mineProvider = null;
        }
    };
    Handler mHandler = new Handler(Looper.getMainLooper());

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            connected();
        }
    };
    private void connected() {
        try {
            boolean gameStarted = mineProvider.gameStarted();
            Log.i(TAG, gameStarted+"");
            if(!gameStarted){
                mHandler.postDelayed(task,1000);
                Log.i(TAG, "connected: posted");
                return;
            }
            Toast.makeText(this,"gameStart: "+gameStarted,Toast.LENGTH_LONG).show();
            Log.i(TAG, "gameStarted!!!");
            Log.i(TAG, mineProvider.test());
            startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGame() throws Exception {
        MineAlgorithm.start(mineProvider);
        connected();
    }

    private IMineAidlInterface mineProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //扫雷游戏 Service 中配置的 Action
        Intent intent1 = new Intent("com.eshel.mine.MineAidlService");
        //扫雷游戏包名
        intent1.setPackage("com.eshel.mine");
        //调用此行后如果目标App存在将会执行ServiceConnection中的onServiceConnected方法, 如果没有执行需检查配置是否有误
        bindService(intent1, mConnection, BIND_AUTO_CREATE);
    }
}
