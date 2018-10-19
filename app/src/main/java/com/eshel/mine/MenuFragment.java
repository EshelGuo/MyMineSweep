package com.eshel.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.eshel.mine.base.BaseFragment;

/**
 * Created by guoshiwen on 2018/4/13.
 */

public class MenuFragment extends BaseFragment implements View.OnClickListener {

	private FrameLayout mRoot;
	private Button mNewGame;
	private Button mContinueGame;
	private Button mOption;
	private Button mExitGame;
	private LinearLayout mButtons;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		mRoot = (FrameLayout) View.inflate(getActivity(),R.layout.layout_home,null);
		mNewGame = mRoot.findViewById(R.id.new_game);
		mButtons = mRoot.findViewById(R.id.buttons);
		mContinueGame = mRoot.findViewById(R.id.continue_game);
		mOption = mRoot.findViewById(R.id.option);
		mExitGame = mRoot.findViewById(R.id.exit_game);
		return mRoot;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		changeMatchPartent(mRoot);
		mNewGame.setOnClickListener(this);
		mExitGame.setOnClickListener(this);
		mContinueGame.setOnClickListener(this);
		mOption.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.new_game:
				getSelfActivity().showFragment(MainActivity.LEVEL_FRAGMENT,false);
				break;
			case R.id.exit_game:
				System.exit(0);
				break;
		}
	}

	public void hideAll(){
		mButtons.setVisibility(View.GONE);
	}
	public void showAll(){
		mButtons.setVisibility(View.VISIBLE);
	}
}
