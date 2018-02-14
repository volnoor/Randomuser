package com.volnoor.randomuser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Eugene on 13.02.2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<RandomuserResponse.Result> users;
    private OnItemClickListener listener;

    public UserAdapter(Context context, List<RandomuserResponse.Result> users) {
        this.context = context;
        this.users = users;
        listener = (OnItemClickListener) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RandomuserResponse.Result user = users.get(position);

        Picasso.with(context)
                .load(user.getPicture().getThumbnail())
                .into(holder.ivThumbnail);

        holder.tvName.setText(user.getName().getFirst());

        // Set on click listener for whole view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClicked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivThumbnail;
        private TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public interface OnItemClickListener {
        void itemClicked(RandomuserResponse.Result user);
    }
}
