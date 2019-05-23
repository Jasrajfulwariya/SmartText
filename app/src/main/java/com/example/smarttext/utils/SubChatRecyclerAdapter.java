package com.example.smarttext.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.smarttext.R;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.ArrayList;

public class SubChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<LiveChatData> data;
    public SubChatRecyclerAdapter(Context mContext , ArrayList<LiveChatData>data) {
        this.mContext=mContext;
        this.data=data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getSedRec();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i)
        {
            case 2:
                return new ImageSandViewHolder(LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.sender_image, viewGroup, false));
            case 3:
                return new ImageReceiveViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.receiver_image,viewGroup,false));
            case 1:
                return new MessageSandViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.sender_chat,viewGroup,false));
            case 0:
                return new MessageReceiveViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.receiver_chat,viewGroup,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ImageReceiveViewHolder)
        {
            ((ImageReceiveViewHolder) viewHolder).setImageReceiverListener(i);
        }
        if(viewHolder instanceof ImageSandViewHolder)
        {
            ((ImageSandViewHolder) viewHolder).setImageSandListener(i);
        }
        if(viewHolder instanceof MessageSandViewHolder)
        {
            ((MessageSandViewHolder) viewHolder).mTextSend.setText(data.get(i).getData());
        }
        if(viewHolder instanceof MessageReceiveViewHolder)
        {
            ((MessageReceiveViewHolder) viewHolder).mTextRec.setText(data.get(i).getData());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ImageSandViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mImageSender;
        ImageSandViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageSender =itemView.findViewById(R.id.subChatSendImage);
        }
        void setImageSandListener(final int i)
        {
            mImageSender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    class ImageReceiveViewHolder extends  RecyclerView.ViewHolder
    {
        BlurImageView mImageReceiver;
        ImageButton mDownloadImageButton;
        ImageReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageReceiver=itemView.findViewById(R.id.subChatRecViewReceiveImage);
            mDownloadImageButton=itemView.findViewById(R.id.subChatDownloadButton);
        }
        public void setImageReceiverListener(final int i)
        {
            mImageReceiver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mDownloadImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    class MessageSandViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextSend;
        MessageSandViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextSend=itemView.findViewById(R.id.subChatSendMessage);
        }
    }
    class MessageReceiveViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextRec;
        MessageReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextRec=itemView.findViewById(R.id.receiverMessage);
        }
    }
}
