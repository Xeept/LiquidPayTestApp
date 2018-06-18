package com.reuben.liquidpaytestapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reuben.liquidpaytestapp.AlbumInterface;
import com.reuben.liquidpaytestapp.ConstantsManager;
import com.reuben.liquidpaytestapp.R;
import com.reuben.liquidpaytestapp.fragments.Album_List;

public class AlbumActivity extends AppCompatActivity implements AlbumInterface {
    private int userId;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private LinearLayout informationContainer;
    private TextView toolbarTitle;
    private TextView toolbarSubtitle;
    private TextView albumName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getIntExtra(ConstantsManager.INTENT_USER_ID,-1);
        setContentView(R.layout.activity_album);
        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.appbar);
        informationContainer = findViewById(R.id.information_container);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarSubtitle = findViewById(R.id.toolbar_subtitle);
        albumName = findViewById(R.id.album_name);

        String username = getIntent().getStringExtra(ConstantsManager.INTENT_NAME) + "(" + getIntent().getStringExtra(ConstantsManager.INTENT_USERNAME) + ")";
        toolbarTitle.setText(username);
        ((TextView)findViewById(R.id.user)).setText(username);
        ((TextView)findViewById(R.id.email)).setText(getIntent().getStringExtra(ConstantsManager.INTENT_EMAIL));
        ((TextView)findViewById(R.id.address)).setText(getIntent().getStringExtra(ConstantsManager.INTENT_ADDRESS));

        initListeners();
        initAlbumList();
    }

    private void initAlbumList(){
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantsManager.INTENT_USER_ID, userId);
        Album_List fragment = new Album_List();
        fragment.setArguments(bundle);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }

    private void initListeners(){
        //Workaround for the usage of a subtitle in the toolbar when using collapsing effects
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar.setVisibility(View.VISIBLE);
                    informationContainer.setVisibility(View.INVISIBLE);
                    isShow = true;
                } else if(isShow) {
                    toolbar.setVisibility(View.INVISIBLE);
                    informationContainer.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }

    public void updateAlbum(String name) {
        toolbarSubtitle.setText(name);
        albumName.setText(name);
    }
}
