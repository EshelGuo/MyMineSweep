package com.eshel.mine;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.eshel.mine.aidl.MineDataProvider;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by guoshiwen on 2018/4/13.
 */

public class MineManager {

	public static final int esayMineNum = 10;
	public static final int mediumMineNum = 40;
	public static final int heightMineNum = 99;

	public static MineManager mMineManager;
	private Handler mHandler = new Handler(Looper.getMainLooper());
	public Mine[][] mMines;
	int row;
	int column;
	private int mineNum = esayMineNum;
	public boolean firstClick = true;

	public Mine getMine(int x, int y){
		try {
			return mMines[y][x];
		}catch (Exception e){
		    e.printStackTrace();
		}
		return null;
	}
	private Runnable timecallback = new Runnable() {
		@Override
		public void run() {
			nowTime++;
			timeView.setText(formatTime(nowTime));
			mHandler.postDelayed(this,1000);
		}
	};

	int nowTime;//单位: 秒

	TextView mineView;
	TextView timeView;
	public MineManager(int mineNumLevel){
		this.mineNum = mineNumLevel;
		switch (this.mineNum){
			case esayMineNum:
				row = 9;
				column = 9;
				break;
			case mediumMineNum:
				row = 16;
				column = 16;
				break;
			case heightMineNum:
				row = 25;
				column = 25;
				break;
		}
		mMines = new Mine[column][row];
		mMineManager = this;
	}

	public void createMines(){
		for (int column = 0; column < mMines.length; column++) {
			for (int row = 0; row < mMines[0].length; row++) {
				mMines[column][row] = new Mine();
			}
		}
		ArrayList<Mine> mines = new ArrayList<>(row * column);
		int i = 0;
		for (int column = 0; column < mMines.length; column++) {
			for (int row = 0; row < mMines[0].length; row++) {
				Mine mine = mMines[column][row];
				mine.x = row;
				mine.y = column;
				mines.add(mine);
				int left = row - 1;
				int right = row + 1;
				int top = column - 1;
				int bottom = column + 1;

				if(left >= 0){
					mine.left = mMines[column][left];
					if(top >= 0)
						mine.leftTop = mMines[top][left];
					if(bottom <= this.column - 1)
						mine.leftBottom = mMines[bottom][left];
				}

				if(top >= 0){
					mine.top = mMines[top][row];
					if(right <= this.row - 1)
						mine.rightTop = mMines[top][right];
				}

				if(right <= this.row - 1)
					mine.right = mMines[column][right];

				if(bottom <= this.column - 1){
					mine.bottom = mMines[bottom][row];
					if(right <= this.row - 1)
						mine.rightBottom = mMines[bottom][right];
				}
			}
		}
		int mineCount = mineNum;
		Random random = new Random();
		while (mineCount>0){
			int index = random.nextInt(mines.size());
			Mine mine = mines.remove(index);
			mine.type = Mine.TYPE_MINE;
			mineCount--;
		}
	}

	public void setMineToUI(MineCallback callback){
		for (int column = 0; column < mMines.length; column++) {
			for (int row = 0; row < mMines[0].length; row++) {
				Mine mine = mMines[column][row];
				if(callback != null){
					callback.callback(mine);
				}
			}
		}
	}

	public void showAll(){
		for (int column = 0; column < mMines.length; column++) {
			for (int row = 0; row < mMines[0].length; row++) {
				Mine mine = mMines[column][row];
				mine.mMineView.setBackgroundRes(mine,false,false);
			}
		}
	}

	public void setMineNum(TextView mineView, TextView timeView, int mineNum){
		this.mineView = mineView;
		this.timeView = timeView;
		this.mineNum = mineNum;
		mineView.setText(String.valueOf(mineNum));
		timeView.setText("00:00");
	}

	/**
	 * 第一次点击开始计时
	 */
	public void startTime(){
		if(firstClick){
			firstClick = false;
			if(timeView != null){
				mHandler.postDelayed(timecallback,1000);
			}
		}
	}

	/**
	 * 停止计时
	 */
	public void stopTime(){
		mHandler.removeCallbacks(timecallback);
	}

	private String formatTime(int nowTime) {
		int m = nowTime / 60;
		int s = nowTime % 60;
		return String.format((m < 10 ? "0" : "") + "%d:" + (s < 10 ? "0" : "") + "%d",m,s);
	}

	public void reduceMineNum(){
		mineNum--;
		if(mineView != null) {
			mineView.setText(String.valueOf(mineNum));
			if(mineNum == 0){
				for (int column = 0; column < mMines.length; column++) {
					for (int row = 0; row < mMines[0].length; row++) {
						Mine mine = mMines[column][row];
						if(mine.flag == Mine.FLAG_1 && mine.type != Mine.TYPE_MINE)
							return;
					}
				}
				stopTime();
				new AlertDialog
						.Builder(mineView.getContext())
						.setTitle("You Win!!!")
						.setCancelable(false)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								MainActivity.self.onBackPressed();
							}
						})
						.show();
				MineDataProvider.gameState = 2;
			}
		}
	}
	public void addMineNum(){
		mineNum++;
		if(mineView != null)
			mineView.setText(String.valueOf(mineNum));
	}
	public static interface MineCallback{
		public void callback(Mine mine);
	}
}
