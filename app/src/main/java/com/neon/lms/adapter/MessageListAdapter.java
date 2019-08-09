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
import com.neon.lms.databinding.RowMessageItemBinding;
import com.neon.lms.model.MessageModel;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<MessageModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public MessageListAdapter(Context context, ArrayList<MessageModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.name.setText(arrayList.get(position).getSender().getFull_name());
        h.binding.msg.setText(arrayList.get(position).getBody());
        h.binding.date.setText(AppConstant.getDate(arrayList.get(position).getCreated_at(), AppConstant.dd_MMM));
        Picasso.with(context)
                .load(arrayList.get(position).getSender().getImage())
                .into(h.binding.img);
        h.binding.setMessageModel(arrayList.get(position));
        h.binding.executePendingBindings();


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowMessageItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llMain:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;

            }


        }
    }


}
