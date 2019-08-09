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
import com.neon.lms.databinding.RowNewsItemBinding;
import com.neon.lms.model.NewsModel;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<NewsModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public NewsListAdapter(Context context, ArrayList<NewsModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.setNewsModel(arrayList.get(position));
        Picasso.with(context)
                .load(arrayList.get(position).getBlog_image())
                .into(h.binding.courseBg);
        h.binding.title.setText(arrayList.get(position).getTitle());
        h.binding.date.setText(AppConstant.getDate(arrayList.get(position).getCategory().getCreated_at(), AppConstant.sdfFormateDate));
        h.binding.executePendingBindings();


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowNewsItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llGame.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llGame:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;

            }


        }
    }


}
