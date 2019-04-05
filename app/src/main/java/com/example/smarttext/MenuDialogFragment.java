package com.example.smarttext;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MenuDialogFragment extends DialogFragment implements View.OnClickListener{
    RelativeLayout mRootLayout;
    RelativeLayout mLayoutContacts;
    RelativeLayout mLayoutCalls;
    RelativeLayout mLayoutProfile;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.MainDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView=inflater.inflate(R.layout.dialog_main_menu,container,false);
        mLayoutContacts=dialogView.findViewById(R.id.dialogLayoutContacts);
        mLayoutCalls=dialogView.findViewById(R.id.dialogLayoutCallLog);
        mRootLayout=dialogView.findViewById(R.id.dialogLayoutRoot);
        mLayoutProfile=dialogView.findViewById(R.id.dialogLayoutProfile);
        return dialogView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        mLayoutContacts.setOnClickListener(this);
        mLayoutCalls.setOnClickListener(this);
        mLayoutProfile.setOnClickListener(this);
        mRootLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.dialogLayoutContacts:
                 Intent contacts=new Intent(getActivity(), ContactsActivity.class);
                 startActivity(contacts);
                 dismiss();
                 break;
            case R.id.dialogLayoutCallLog:
                 Intent calls=new Intent(getActivity(),CallActivity.class);
                 startActivity(calls);
                 dismiss();
                 break;
            case R.id.dialogLayoutProfile:
                 Intent profile=new Intent(getActivity(),ProfileActivity.class);
                 startActivity(profile);
                 dismiss();
                 break;
            case R.id.dialogLayoutRoot:
                 dismiss();
                 break;
        }
    }
}
