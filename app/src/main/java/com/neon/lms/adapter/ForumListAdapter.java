package com.neon.lms.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.lms.R;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.RowForumItemBinding;
import com.neon.lms.model.ForumModel;
import com.neon.lms.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ForumListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<ForumModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public ForumListAdapter(Context context, ArrayList<ForumModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_forum_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.title.setText(arrayList.get(position).getTitle());
        h.binding.des.setText(Html.fromHtml(arrayList.get(position).getPost().get(0).getBody()));
        h.binding.title.setText(arrayList.get(position).getTitle());
        Picasso.with(context)
                .load(arrayList.get(position).getUser().getImage())
                .into(h.binding.imgUser);
        h.binding.setForumModel(arrayList.get(position));
        h.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowForumItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);
            binding.delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llMain:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break; case R.id.delete:
                    onItemClick.onClick(getLayoutPosition(), Constants.DELETE);
                    break;

            }
        }
    }


}
