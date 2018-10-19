package com.eshel.mine;

import com.eshel.mine.view.MineView;

/**
 * Created by guoshiwen on 2018/4/13.
 */

public class Mine {
	public int x;
	public int y;
	public static final int TYPE_MINE = -1;
	public static final int FLAG_1 = 11;//旗子
	public static final int FLAG_2 = 12;//?
	public static final int FLAG_NORMOL = 10;//normal
	public boolean isShowed;// 为 true 代表格子已知, 为 false 代表格子未知
	public Mine left;
	public Mine right;
	public Mine top;
	public Mine bottom;
	public Mine leftTop;
	public Mine rightTop;
	public Mine leftBottom;
	public Mine rightBottom;
	public MineView mMineView;

	public int type;
	public int flag = FLAG_NORMOL;
	private void addType(Mine otherMine){
		if(otherMine != null && otherMine.type == TYPE_MINE)
			type++;
	}

	public int getMineNum(){
		if(type == TYPE_MINE)
			return type;
		type = 0;
		addType(leftTop);
		addType(left);
		addType(right);
		addType(top);
		addType(bottom);
		addType(rightTop);
		addType(leftBottom);
		addType(rightBottom);
		return type;
	}

	public int getUnknownMineNum(){
		int num = 0;

		if(isUnknownMine(left))
			num++;
		if(isUnknownMine(right))
			num++;
		if(isUnknownMine(top))
			num++;
		if(isUnknownMine(bottom))
			num++;
		if(isUnknownMine(leftTop))
			num++;
		if(isUnknownMine(rightTop))
			num++;
		if(isUnknownMine(leftBottom))
			num++;
		if(isUnknownMine(rightBottom))
			num++;

		return num;
	}

	private static boolean isUnknownMine(Mine mine){
		return mine != null && !mine.isShowed && mine.flag != FLAG_1;
	}
}
