package com.example.smarttext.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smarttext.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    public MainChatRecyclerAdapter(Context context) {
        mContext=context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       if (viewType==0)
           return new ViewHolderActiveList(LayoutInflater
                   .from(viewGroup.getContext())
                   .inflate(R.layout.model_active_list, viewGroup, false));
       else
           return new ViewHolderActiveList(LayoutInflater
                   .from(viewGroup.getContext())
                   .inflate(R.layout.model_chat_list, viewGroup, false));
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(position==0)
        {
            ActiveListRecyclerAdapter adapterActiveList=new ActiveListRecyclerAdapter(mContext);
            ((ViewHolderActiveList)viewHolder).activeList
                    .setLayoutManager(new LinearLayoutManager
                            (mContext,LinearLayoutManager.HORIZONTAL,false));
            ((ViewHolderActiveList)viewHolder).activeList.setAdapter(adapterActiveList);
        }
        else
        {
            ((ViewHolderActiveList)viewHolder).profileImage.setImageResource(R.drawable.user);
            ((ViewHolderActiveList)viewHolder).nameOfUser.setText("jasraj");
            ((ViewHolderActiveList)viewHolder).lastActive.setText("Active 26min ago");
            ((ViewHolderActiveList)viewHolder).setListener(position);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolderActiveList extends RecyclerView.ViewHolder
    {
        CircleImageView profileImage;
        RelativeLayout mainBg;
        TextView nameOfUser;
        TextView lastActive;
        RecyclerView activeList;
        public ViewHolderActiveList(@NonNull View itemView) {
             super(itemView);
             mainBg=itemView.findViewById(R.id.modelChatListRelativeLayout);
             activeList=(RecyclerView) itemView.findViewById(R.id.activeListRecyclerView);
             profileImage=(CircleImageView)itemView.findViewById(R.id.modelChatListProfilePic);
             nameOfUser=(TextView)itemView.findViewById(R.id.modelChatListName);
             lastActive=(TextView)itemView.findViewById(R.id.modelChatListActiveAgo);
        }
        public void setListener(final int i)
        {
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Profile Pic Item "+i,Toast.LENGTH_LONG).show();
                }
            });
            mainBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Main Background Item "+i,Toast.LENGTH_LONG).show();

                }
            });
        }
    }
    class ViewHolderChatList extends RecyclerView.ViewHolder
    {
        public ViewHolderChatList(@NonNull View itemView) {
            super(itemView);
        }
    }
}
