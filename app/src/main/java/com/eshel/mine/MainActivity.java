package com.eshel.mine;

import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	public final static int LEVEL_FRAGMENT = 0;
	public final static int MENU_FRAGMENT = 1;
	public final static int GAME_FRAGMENT = 2;
	private FragmentManager mManager;
	private Fragment currentFragment;
	private MenuFragment mMenuFragment;
	public static MainActivity self;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		self = this;
		getWindow().setBackgroundDrawable(new ColorDrawable(0xff966d45));
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mManager = getSupportFragmentManager();
		currentFragment = new MenuFragment();
		mMenuFragment = (MenuFragment) currentFragment;
		mManager.beginTransaction()
			.add(R.id.main,currentFragment)
			.commit();
	}

	public void showFragment(int id, boolean removeLast){
		Fragment lastFragment = currentFragment;
		switch (id){
			case LEVEL_FRAGMENT:
				currentFragment = new LevelFragment();
				mMenuFragment.hideAll();
				break;
			case MENU_FRAGMENT:
				currentFragment = new MenuFragment();
				mMenuFragment = (MenuFragment) currentFragment;
				break;
			case GAME_FRAGMENT:
				currentFragment = new GameFragment();
				mMenuFragment.hideAll();
				break;
		}
		if(mManager != null) {
			mManager.beginTransaction()
					.add(R.id.main,currentFragment)
					.addToBackStack(null)
					.commit();
			if(removeLast){
				mManager.beginTransaction()
						.remove(lastFragment)
						.commit();
			}
		}
	}

	@Override
	public void onBackPressed() {
		if(currentFragment instanceof GameFragment || currentFragment instanceof LevelFragment)
			mMenuFragment.showAll();
		super.onBackPressed();
	}
}
