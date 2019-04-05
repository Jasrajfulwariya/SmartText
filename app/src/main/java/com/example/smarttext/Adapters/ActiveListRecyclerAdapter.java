package com.example.smarttext.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttext.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveListRecyclerAdapter extends RecyclerView.Adapter<ActiveListRecyclerAdapter
        .ViewHolderSetActiveList> {
    Context mContext;

    public ActiveListRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderSetActiveList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderSetActiveList(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_active_list_profile_re,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSetActiveList viewHolderSetActiveList, int i) {
        viewHolderSetActiveList.profilePic.setImageResource(R.drawable.user);
        viewHolderSetActiveList.shortNameOfUser.setText("Jasraj");
        viewHolderSetActiveList.setListener(i);
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class ViewHolderSetActiveList extends RecyclerView.ViewHolder
    {
        View mainView;
        CircleImageView profilePic;
        TextView shortNameOfUser;
        public ViewHolderSetActiveList(@NonNull View itemView) {
            super(itemView);
            mainView=itemView;
            profilePic=itemView.findViewById(R.id.modelActiveRecProfilePic);
            shortNameOfUser=itemView.findViewById(R.id.modelActiveRecName);
        }
        public void setListener(final int i)
        {
            mainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Active List Item "+i,Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
