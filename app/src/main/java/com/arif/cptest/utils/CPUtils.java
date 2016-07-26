package com.arif.cptest.utils;

import android.content.Context;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import com.arif.cptest.models.Search;

import java.util.List;

/**
 * Created by arifnadeem on 11/4/15.
 */
public class CPUtils {

    public static final String[] COLUMNS = new String[]{"_id", "YEAR", "TYPE", "POSTER", "TITLE"};

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static MatrixCursor convertResultsToCursor(List<Search> results) {
        MatrixCursor matrixCursor = new MatrixCursor(COLUMNS);
        if (results != null) {
            int i = 0;
            for (Search search : results) {
                String[] temp = new String[5];
                temp[0] = String.valueOf(i);
                temp[1] = search.getYear();
                temp[2] = search.getType();
                temp[3] = search.getPoster();
                temp[4] = search.getTitle();
                matrixCursor.addRow(temp);
                i = i + 1;
            }
        }
        return matrixCursor;
    }

    public static void hideSoftKeyboard(AppCompatActivity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

}
