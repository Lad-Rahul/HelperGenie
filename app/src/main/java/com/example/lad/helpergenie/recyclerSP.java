package com.example.lad.helpergenie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

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
        final HolderClass finalHolder = holder;
        holder.name.setText(name[position]);
        holder.emailID.setText(email[position]);
        holder.mobile.setText(mobile[position]);
        holder.ID.setText(id[position]);
        holder.phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + finalHolder.mobile.getText().toString()));
                ct.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class HolderClass extends RecyclerView.ViewHolder {

        TextView name,emailID,mobile,ID;
        ImageView phoneCall;

        public HolderClass(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.spName);
            emailID = (TextView) itemView.findViewById(R.id.spEmail);
            mobile = (TextView) itemView.findViewById(R.id.spMobile);
            ID = (TextView) itemView.findViewById(R.id.spID);
            phoneCall = (ImageView) itemView.findViewById(R.id.phoneCall);
        }
    }

}
