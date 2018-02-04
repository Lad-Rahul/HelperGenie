package com.example.lad.helpergenie;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pro on 1/19/2018.
 */

public class about_us extends Fragment {

    View mView;
    private HashMap<String,List<String>> expHeader;
    private List<String> expChild;
    private ExpandableListView expList;

    expAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.about_us_new,container,false);
        getActivity().setTitle("About us");

//        expList = (ExpandableListView)mView.findViewById(R.id.expList_aboutus);
//        expHeader = expAdapter.DataProvider.getInfo();
//        expChild = new ArrayList<String>(expHeader.keySet());
//        //java.util.Collections.sort(expChild);   //for right order of list
//        adapter = new expAdapter(LayoutInflater.from(getActivity()),expHeader,expChild);
//        expList.setAdapter(adapter);
//
//
        return mView;
    }
}


class expAdapter extends BaseExpandableListAdapter{


    private final LayoutInflater ctx;
    private HashMap<String,List<String>> childTitles;
    private List<String> headerTitles;


    expAdapter(LayoutInflater ctx, HashMap<String,List<String>> childTitles, List<String> headerTitles ){

        this.ctx = ctx;
        this.childTitles = childTitles;
        this.headerTitles = headerTitles;
    }

    @Override
    public int getGroupCount() {
        return headerTitles.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childTitles.get(headerTitles.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return headerTitles.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childTitles.get(headerTitles.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String title = (String)this.getGroup(i);

        if (view == null) {
            //LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = ctx.inflate(R.layout.exp_header, null);
        }

        TextView txt = (TextView) view.findViewById(R.id.txt_header);
        txt.setTypeface(null, Typeface.BOLD);
        txt.setText(title);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        String title = (String) this.getChild(i, i1);

        if (view == null) {
            //LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = ctx.inflate(R.layout.exp_child, null);
        }
        TextView txt = (TextView) view.findViewById(R.id.txt_child);
        txt.setText(title);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    public static class DataProvider {

        public static HashMap<String, List<String>> getInfo() {

            HashMap<String, List<String>> HeaderDetails = new HashMap<String, List<String>>();



            List<String> ChildDetails1 = new ArrayList<String>();
            ChildDetails1.add("Our Mission is to provide you services like Plumber, Electrician, Carpenter etc at any time and any location instantly in Today's busy high-tech world ");

            List<String> ChildDetails2 = new ArrayList<String>();
            ChildDetails2.add("helper_genie@gmail.com");

            List<String> ChildDetails3 = new ArrayList<String>();
            ChildDetails3.add("Rahul Lad");
            ChildDetails3.add("Pranav Vyas");
            ChildDetails3.add("Akash Patel");

            HeaderDetails.put("Contact us", ChildDetails2);
            HeaderDetails.put("Mission", ChildDetails1);
            HeaderDetails.put("Developed by ERROR 404", ChildDetails3);

            return HeaderDetails;
        }

    }



}