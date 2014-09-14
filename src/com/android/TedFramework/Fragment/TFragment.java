package com.android.TedFramework.Fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Ted on 14-8-6.
 */
public class TFragment extends Fragment {



    public String getTString(int id){
         return getActivity().getResources().getString(id);
    }
}
