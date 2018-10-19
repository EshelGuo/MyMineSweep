package com.eshel.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.eshel.mine.aidl.MineDataProvider;
import com.eshel.mine.base.BaseFragment;
import com.eshel.mine.view.MineView;

/**
 * Created by guoshiwen on 2018/4/13.
 */

public class GameFragment extends BaseFragment{

	private View mRoot;
	private GridLayout mControl;
	private MineManager mManager;
	public TextView mMineNum;
	public TextView mTime;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRoot = View.inflate(getContext(), R.layout.layout_game, null);
		mControl = mRoot.findViewById(R.id.control);
		mMineNum = mRoot.findViewById(R.id.mine_num);
		mTime = mRoot.findViewById(R.id.time);
		int game_level = getActivity().getPreferences(Context.MODE_PRIVATE).getInt("game_level", MineManager.esayMineNum);
		mManager = new MineManager(game_level);
		MineDataProvider.mManager = mManager;
		mManager.setMineNum(mMineNum,mTime,game_level);
		mManager.createMines();
		mControl.setRowCount(mManager.row);
		mControl.setColumnCount(mManager.column);

		mManager.setMineToUI(new MineManager.MineCallback() {
			@Override
			public void callback(Mine mine) {
				mControl.addView(new MineView(mine,getContext()));
			}
		});
		/*mControl.getViewTreeObserver().
				addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			boolean over;
			@Override
			public void onGlobalLayout() {
				if(!over){
					over = true;
					FrameLayout.LayoutParams layoutParams =
							(FrameLayout.LayoutParams) mControl.getLayoutParams();
					layoutParams.width = MineView.dp2px(getContext(),mManager.row * MineView.size);
					layoutParams.height = MineView.dp2px(getContext(),mManager.row * MineView.size);
					mControl.setLayoutParams(layoutParams);
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					mControl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			}
		});*/
		return mRoot;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		changeMatchPartent(mRoot);
		MineView.clickMINE = false;
		MineDataProvider.gameStarted = true;
		MineDataProvider.gameState = 1;
	}

	@Override
	public void onDestroyView() {
		MineDataProvider.gameStarted = false;
		MineDataProvider.gameState = 0;
		MineDataProvider.mManager = null;
		super.onDestroyView();
	}
}
