package com.neon.lms.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetMessageDataThreadsMessages;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.RowMessagedetailItemBinding;
import com.neon.lms.util.Constants;

import java.util.ArrayList;

public class MessageDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<NetMessageDataThreadsMessages> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public MessageDetailAdapter(Context context, ArrayList<NetMessageDataThreadsMessages> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messagedetail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.txtMessage.setText(arrayList.get(position).getBody());
        h.binding.setNetMessageDataThreadsMessages(arrayList.get(position));
        h.binding.executePendingBindings();


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowMessagedetailItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.linMain.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.linMain:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;

            }


        }
    }


}
