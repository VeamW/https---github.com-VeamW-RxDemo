package com.arif.cptest.fragments;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by arifnadeem on 11/3/15.
 */
public class BaseFragment extends Fragment {

    protected void showShortToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}
