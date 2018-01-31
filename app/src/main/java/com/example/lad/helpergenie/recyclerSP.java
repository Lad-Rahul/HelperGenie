package com.example.lad.helpergenie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Pro on 1/31/2018.
 */




public class recyclerSP extends RecyclerView.Adapter<recyclerSP.HolderClass> {

    Context ct;
    String name[],mobile[],email[],id[];

    public recyclerSP(Context ctx,String dataname[],String dataemail[],String datamobile[],String dataid[]) {
        ct = ctx;
        name = dataname;
        mobile = datamobile;
        email = dataemail;
        id = dataid;
    }

        @Override
    public HolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflator = LayoutInflater.from(ct);
        View mView = mInflator.inflate(R.layout.sp_row,parent,false);
        return new HolderClass(mView);
    }

    @Override
    public void onBindViewHolder(HolderClass holder, int position) {
        holder.name.setText(name[position]);
        holder.emailID.setText(email[position]);
        holder.mobile.setText(mobile[position]);
        holder.ID.setText(id[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class HolderClass extends RecyclerView.ViewHolder {

        TextView name,emailID,mobile,ID;

        public HolderClass(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.spName);
            emailID = (TextView) itemView.findViewById(R.id.spEmail);
            mobile = (TextView) itemView.findViewById(R.id.spMobile);
            ID = (TextView) itemView.findViewById(R.id.spID);
        }
    }

}
