package com.example.smarttext;
import android.animation.Animator;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.example.smarttext.Adapters.MainChatRecyclerAdapter;
import com.example.smarttext.LogInActivity.LogInManager;
import com.example.smarttext.utils.Config;
import com.example.smarttext.utils.ContactData;
import com.example.smarttext.utils.FireBaseDatabaseManager;
import com.example.smarttext.utils.Permission;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FireBaseDatabaseManager manager;
    private SquliteContactinfo contactinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ActivityCompat.requestPermissions(this, Permission.ALL_PERMISSION_ARRAY, Config.ALL_PERMISSION_VERIFY_CODE);
        recyclerSetup();
      //  ArrayList<ContactData>myAllContacts=new ArrayList<>();
      //  myAllContacts=contactinfo.fetchData();
    }
    public void init()
    {
        manager=new FireBaseDatabaseManager();
        contactinfo=new SquliteContactinfo(new FirstProfileUpdateActivity());
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
     }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent goToLogin =new Intent(MainActivity.this, LogInManager.class);
            startActivity(goToLogin);
            finish();
        }
        else
            manager.sandActiveNow();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            manager.sandNotActive();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
     /*   if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        manager.sandActiveNow();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        manager.sandNotActive();*/
    }
}
