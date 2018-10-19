package com.eshel.mine.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.eshel.mine.Mine;
import com.eshel.mine.MineManager;
import com.eshel.mine.R;
import com.eshel.mine.aidl.MineDataProvider;

/**
 * Created by guoshiwen on 2018/4/13.
 */

public class MineView extends android.support.v7.widget.AppCompatButton implements View.OnClickListener, View.OnLongClickListener {
	public static int size = 20;//dp
	private Mine mMine;

	public MineView(Mine mine, Context context){
		this(context);
		mine.isShowed = false;
		mMine = mine;
		mMine.mMineView = this;
	}
	public MineView(Context context) {
		this(context,null);
	}

	public MineView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public MineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		setBackgroundResource(R.drawable.mine_btn_selector);
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(dp2px(getContext(), size), dp2px(getContext(), size));
		setLayoutParams(layoutParams);
		setOnClickListener(this);
		setOnLongClickListener(this);
	}

	public static int dp2px(Context context, int dp){
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (density * dp + .5f);
	}

	@Override
	public void onClick(View v) {
		if(mMine.isShowed)
			return;
		if(mMine.flag == Mine.FLAG_NORMOL){
			setBackgroundRes(mMine,true,true);
			if(mMine.type == Mine.TYPE_MINE){
				MineManager.mMineManager.showAll();
				MineManager.mMineManager.stopTime();
				new AlertDialog.Builder(getContext())
						.setTitle("Game Over")
						.setPositiveButton("确定",null)
						.show();
				MineDataProvider.gameState = -1;
			}else {
				MineManager.mMineManager.startTime();
			}
		}else {
			if(mMine.flag == Mine.FLAG_1){
				mMine.flag = Mine.FLAG_2;
				MineManager.mMineManager.addMineNum();
				setBackgroundResource(R.mipmap.mine_flag2);
			}else if(mMine.flag == Mine.FLAG_2){
				mMine.flag = Mine.FLAG_1;
				MineManager.mMineManager.reduceMineNum();
				setBackgroundResource(R.mipmap.mine_flag);
			}
		}
	}
	public static boolean clickMINE;
	public void setBackgroundRes(Mine mine, boolean loop, boolean isclick){
		if(mine != null){
			if(mine.isShowed)
				return;
			int mineNum = mine.getMineNum();

			if(isclick && mMine.type == Mine.TYPE_MINE){
				clickMINE = true;
				;//do nothing
				if (MineDataProvider.clickMines != null)
					MineDataProvider.clickMines.add(com.eshel.mine.aidl.Mine.toJson(mine.x, mine.y, mineNum));
			}else {
				if (!clickMINE && MineDataProvider.clickMines != null)
					MineDataProvider.clickMines.add(com.eshel.mine.aidl.Mine.toJson(mine.x, mine.y, mineNum));
			}

			if(mineNum == Mine.TYPE_MINE){
				this.setBackgroundResource(getIconRes(isclick ? TYPE_MINE_RED : TYPE_MINE));
				mine.mMineView.setClickable(false);
				mine.isShowed = true;
			}else {
				int type = 16;
				switch (mineNum){
					case 0:
						type = TYPE_0;
						break;
					case 1:
						type = TYPE_1;
						break;
					case 2:
						type = TYPE_2;
						break;
					case 3:
						type = TYPE_3;
						break;
					case 4:
						type = TYPE_4;
						break;
					case 5:
						type = TYPE_5;
						break;
					case 6:
						type = TYPE_6;
						break;
					case 7:
						type = TYPE_7;
						break;
					case 8:
						type = TYPE_8;
						break;
				}
				mine.mMineView.setBackgroundResource(getIconRes(type));
				mine.mMineView.setClickable(false);
				mine.isShowed = true;
				if(type == TYPE_0 && loop)
					showAdjoin(mine);
			}
		}
	}
	private void setMineBG(Mine mine){
		if(mine != null
				&& mine.type != Mine.TYPE_MINE
				&& !mine.isShowed){
			boolean isLoop = mine.getMineNum() == 0;
			setBackgroundRes(mine,isLoop,false);
		}
	}

	private void showAdjoin(Mine mine) {
		setMineBG(mine.leftTop);
		setMineBG(mine.left);
		setMineBG(mine.leftBottom);
		setMineBG(mine.top);
		setMineBG(mine.rightTop);
		setMineBG(mine.right);
		setMineBG(mine.bottom);
		setMineBG(mine.rightBottom);
	}

	public int getIconRes(int type){
		int id = 0;
		switch (type){
			case TYPE_MINE_RED:
				id = R.drawable.mine_red;
				break;
			case TYPE_MINE:
				id = R.mipmap.mine;
				break;
			case TYPE_0:
				id = R.mipmap.mine_0;
				break;
			case TYPE_1:
				id = R.mipmap.mine_1;
				break;
			case TYPE_2:
				id = R.mipmap.mine_2;
				break;
			case TYPE_3:
				id = R.mipmap.mine_3;
				break;
			case TYPE_4:
				id = R.mipmap.mine_4;
				break;
			case TYPE_5:
				id = R.mipmap.mine_5;
				break;
			case TYPE_6:
				id = R.mipmap.mine_6;
				break;
			case TYPE_7:
				id = R.mipmap.mine_7;
				break;
			case TYPE_8:
				id = R.mipmap.mine_8;
				break;
		}
		return id;
	}
	public Drawable getIcon(int type){
		Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.mine_410);
		int width = bitmap.getWidth();
//		int height = bitmap.getHeight();
		int y = (type - 1) * width;
		if(type == 16){
			y = bitmap.getHeight() - width;
		}
		Bitmap iconBitmap = bitmap.createBitmap(bitmap, 0, y, width, width);
		return new BitmapDrawable(getResources(),iconBitmap);
	}
	public static final int TYPE_FLAG = 2;
	public static final int TYPE_QUESTION_MARK = 3;
	public static final int TYPE_MINE = 6;
	public static final int TYPE_MINE_RED = 17;
	public static final int TYPE_8 = 8;
	public static final int TYPE_7 = 9;
	public static final int TYPE_6 = 10;
	public static final int TYPE_5 = 11;
	public static final int TYPE_4 = 12;
	public static final int TYPE_3 = 13;
	public static final int TYPE_2 = 14;
	public static final int TYPE_1 = 15;
	public static final int TYPE_0 = 16;

	@Override
	public boolean onLongClick(View v) {
		if(!mMine.isShowed){
			if(mMine.flag == Mine.FLAG_NORMOL){
				mMine.flag = Mine.FLAG_1;
				MineManager.mMineManager.reduceMineNum();
				setBackgroundResource(R.mipmap.mine_flag);
			}else {
				mMine.flag = Mine.FLAG_NORMOL;
				MineManager.mMineManager.addMineNum();
				setBackgroundResource(R.drawable.mine_btn_selector);
			}
		}
		return false;
	}
}
