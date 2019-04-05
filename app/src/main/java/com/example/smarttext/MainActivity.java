package com.example.smarttext;

import android.animation.Animator;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.example.smarttext.Adapters.MainChatRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerSetup();
    }
    public void init()
    {

    }
    public void recyclerSetup()
    {
        MainChatRecyclerAdapter adapter=new MainChatRecyclerAdapter(getApplicationContext());
        RecyclerView recyclerView=findViewById(R.id.mainChatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    public void backToChatToolbar(View view) {
            final Toolbar mToolbarMainChat,mToolBarMainChatSearch;
            mToolbarMainChat=findViewById(R.id.activityMainChatToolBar);
            mToolBarMainChatSearch=findViewById(R.id.mainChatSearchToolBar);
            int[] loc=new int[2];
            findViewById(R.id.mainChatArrowBackBt).getLocationOnScreen(loc);
            int startRadius=(int) Math.hypot(mToolbarMainChat
                    .getWidth(),mToolbarMainChat.getHeight());;
            int endRadius=0;
            Animator animation= ViewAnimationUtils.createCircularReveal(mToolBarMainChatSearch,loc[0],loc[1],startRadius,endRadius);
            mToolbarMainChat.setVisibility(View.VISIBLE);
            animation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mToolBarMainChatSearch.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animation.start();
    }

    public void backToSearchToolbar(View view) {
            final Toolbar mToolbarMainChat,mToolBarMainChatSearch;
            mToolbarMainChat=findViewById(R.id.activityMainChatToolBar);
            mToolBarMainChatSearch=findViewById(R.id.mainChatSearchToolBar);
            int[] loc=new int[2];
            findViewById(R.id.mainChatSearchIB).getLocationOnScreen(loc);
            int startRadius=0;
            int endRadius= (int) Math.hypot(mToolbarMainChat
                    .getWidth(),mToolbarMainChat.getHeight());
            Animator animation= ViewAnimationUtils
                    .createCircularReveal(mToolBarMainChatSearch,
                            loc[0],loc[1],startRadius,endRadius);
            mToolBarMainChatSearch.setVisibility(View.VISIBLE);
            animation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mToolbarMainChat.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animation.start();
    }
    public void selectActivity(View view) {
        DialogFragment menuDialogFragment= new MenuDialogFragment();
        menuDialogFragment.show(getSupportFragmentManager(),"Tag");
        //Change By Jasraj Fulwariya
     }
}
