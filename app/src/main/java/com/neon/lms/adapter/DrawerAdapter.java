package com.neon.lms.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.neon.lms.R;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.DrawerRowBinding;
import com.neon.lms.databinding.DrawerTitleBinding;
import com.neon.lms.databinding.RowDrawerImageBinding;
import com.neon.lms.model.Drawer;
import com.neon.lms.util.Constants;

import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Drawer> drawerArrayList;
    Context context;


    private Deprecated deprecated;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_TOOLS = 2;
    public static final int TYPE_Title = 2;

    public int toolsSelectedPos = -1;
    private OnRecyclerItemClick itemClick;

    public DrawerAdapter(Context context, ArrayList<Drawer> drawerArrayList, OnRecyclerItemClick itemClick) {
        this.context = context;
        this.drawerArrayList = drawerArrayList;
        this.itemClick = itemClick;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row, parent, false));
        } else if (viewType == TYPE_Title) {
            return new MyViewTitle(LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_title, parent, false));
        } else if (viewType == TYPE_HEADER) {
            return new MyHeaderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_drawer_image, parent, false));
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
//        Log.d("onBindViewHolder", "onBindViewHolder: " + position);
        if (vh instanceof MyViewHolder) {
            ((MyViewHolder) vh).binding.txtName.setText(drawerArrayList.get(position).getName());
            ((MyViewHolder) vh).binding.imgDrawer.setImageResource(drawerArrayList.get(position).getDrawableID());

            if (drawerArrayList.get(position).isSelected()) {
                ((MyViewHolder) vh).binding.linMain.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            } else {
                ((MyViewHolder) vh).binding.linMain.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            }

        } else if (vh instanceof MyViewTitle) {
            ((MyViewTitle) vh).binding.txtName.setText(drawerArrayList.get(position).getName());
        } else if (vh instanceof MyHeaderView) {
            ((MyHeaderView) vh).binding.imgProfile.setImageResource(drawerArrayList.get(position).getDrawableID());

        }
    }


    @Override
    public int getItemCount() {
        return drawerArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return drawerArrayList.get(position).getIntViewType();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        DrawerRowBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.linMain.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linMain:
                    itemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;

            }

        }
    }

    public class MyViewTitle extends RecyclerView.ViewHolder implements View.OnClickListener {

        DrawerTitleBinding binding;

        public MyViewTitle(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }


    public class MyHeaderView extends RecyclerView.ViewHolder implements View.OnClickListener {

        RowDrawerImageBinding binding;

        public MyHeaderView(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.profileLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.profileLayout:
                    itemClick.onClick(getAdapterPosition(), Constants.ROW_CLICK);
                    break;



            }
        }
    }


}
