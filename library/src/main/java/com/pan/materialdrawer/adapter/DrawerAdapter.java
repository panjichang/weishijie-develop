package com.pan.materialdrawer.adapter;

import com.pan.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by mikepenz on 14.07.15.
 */
public class DrawerAdapter extends BaseDrawerAdapter {

    public DrawerAdapter() {
    }

    public DrawerAdapter(ArrayList<IDrawerItem> drawerItems) {
        setDrawerItems(drawerItems);
    }
}
