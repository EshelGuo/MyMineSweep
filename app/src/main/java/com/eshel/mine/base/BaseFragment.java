package com.eshel.mine.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eshel.mine.MainActivity;

/**
 * Created by guoshiwen on 2018/4/13.
 */

public abstract class BaseFragment extends Fragment{

	public MainActivity getSelfActivity(){
		FragmentActivity activity = getActivity();
		if(activity instanceof MainActivity)
			return (MainActivity) activity;
		else
			return null;
	}
	public final void changeMatchPartent(View view){
		if(view != null){
			ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			}else {
				layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
				layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
			}
			view.setLayoutParams(layoutParams);
		}

	}
}
