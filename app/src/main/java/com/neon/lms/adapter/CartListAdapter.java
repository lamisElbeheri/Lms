package com.neon.lms.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.lms.R;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.RowCartItemBinding;
import com.neon.lms.model.CartModel;
import com.neon.lms.util.Constants;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<CartModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public CartListAdapter(Context context, ArrayList<CartModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.setCartModel(arrayList.get(position));
        h.binding.executePendingBindings();


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowCartItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);
            binding.remove.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llMain:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;
                case R.id.remove:
                    onItemClick.onClick(getLayoutPosition(), Constants.DELETE);
                    break;

            }


        }
    }


}
