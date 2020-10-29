package com.app.eye.widgets.itemdecoration;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by thekhaeng on 4/30/2017 AD.
 */

public interface OnClickLayoutMarginItemListener{
    void onClick( Context context, View v, int position, int spanIndex, RecyclerView.State state );
}
