package com.eshel.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.eshel.mine.base.BaseFragment;

/**
 * Created by guoshiwen on 2018/4/13.
 */

public class LevelFragment extends BaseFragment implements View.OnClickListener {
	protected RelativeLayout mRoot;
	private Button mLowLevel;
	private Button mMediumLevel;
	private Button mHeightLevel;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		mRoot = (RelativeLayout) View.inflate(getContext(),R.layout.layout_level, null);
		mLowLevel = mRoot.findViewById(R.id.low_level);
		mMediumLevel = mRoot.findViewById(R.id.medium_level);
		mHeightLevel = mRoot.findViewById(R.id.height_level);
		return mRoot;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		changeMatchPartent(mRoot);
		mLowLevel.setOnClickListener(this);
		mMediumLevel.setOnClickListener(this);
		mHeightLevel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.custom_level){
			return;
		}
		SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
		int level = MineManager.esayMineNum;
		switch (v.getId()){
			case R.id.low_level:
				level = MineManager.esayMineNum;
				break;
			case R.id.medium_level:
				level = MineManager.mediumMineNum;
				break;
			case R.id.height_level:
				level = MineManager.heightMineNum;
				break;
		}
		sp.edit()
			.putBoolean("haveOldGame",true)
			.putInt("game_level",level)
			.apply();
		getSelfActivity().showFragment(
				MainActivity.GAME_FRAGMENT,true);
	}
}
