package com.example.smarttext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttext.Adapters.ActiveListRecyclerAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class callListRecyclerView extends RecyclerView.Adapter<callListRecyclerView
        .ViewHolderSetActiveList> {
    Context mContext;

    public callListRecyclerView(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderSetActiveList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.call_basic_layout,viewGroup, false);

        return new ViewHolderSetActiveList(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSetActiveList viewHolderSetActiveList, int i) {
        viewHolderSetActiveList.profilePic.setImageResource(R.drawable.applogin1);
        viewHolderSetActiveList.shortNameOfUser.setText("Karan");
        viewHolderSetActiveList.calllastsenn.setText("20 min");
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
        TextView calllastsenn;
        ImageButton callbutton;
        public ViewHolderSetActiveList(@NonNull View itemView) {
            super(itemView);
            mainView=itemView;
            profilePic=itemView.findViewById(R.id.callImage);
            shortNameOfUser=itemView.findViewById(R.id.callname);
            calllastsenn=itemView.findViewById(R.id.calllastseen);
            callbutton=itemView.findViewById(R.id.callbutton);
        }
        public void setListener(final int i)
        {

            callbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Calling "+i,Toast.LENGTH_LONG).show();
                }
            });
            profilePic.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Profile pic "+i,Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}

