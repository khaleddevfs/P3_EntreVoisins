package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

public class NeighbourDetailsActivity extends AppCompatActivity {

    private ImageView mAvatar;
    private TextView mName1, mName2, mCity, mPhone, mAbout, mFbUrl;
    private FloatingActionButton mFloatingActionButton;

    private NeighbourApiService mNeighbourApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details);
        mAvatar=findViewById(R.id.neighbour_profile_image);
        mName1=findViewById(R.id.neighbour_profile_name1);
        mName2=findViewById(R.id.neighbour_profile_name2);
        mCity=findViewById(R.id.neighbour_profile_city);
        mPhone=findViewById(R.id.neighbour_profile_phone);
        mAbout=findViewById(R.id.neighbour_profile_about);
       mFbUrl=findViewById(R.id.neighbour_profile_fbUrl);
        mFloatingActionButton=findViewById(R.id.floatingActionButton);

        mNeighbourApiService = DI.getNeighbourApiService();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIncomingIntent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("neighbour")){
            Neighbour intentNeighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");
            displayData(intentNeighbour);
        }

    }

    private void displayData(Neighbour intentNeighbour) {
        Glide.with(this).asBitmap().load(intentNeighbour.getAvatarUrl()).fitCenter().into(mAvatar);
        mName1.setText(intentNeighbour.getName());
        mName2.setText(intentNeighbour.getName());
        mCity.setText(intentNeighbour.getAddress());
        mPhone.setText(intentNeighbour.getPhoneNumber());
        mAbout.setText(intentNeighbour.getAboutMe());
        mFbUrl.setText(intentNeighbour.getFbUrl() + intentNeighbour.getName());

        if (intentNeighbour.isFavorite()) {
            mFloatingActionButton.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else {
            mFloatingActionButton.setImageResource(R.drawable.ic_baseline_star_24);
        }

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!intentNeighbour.isFavorite()) {
                    mFloatingActionButton.setImageResource(android.R.drawable.btn_star_big_on);
                }

                else {
                    mFloatingActionButton.setImageResource(R.drawable.ic_baseline_star_24);
                }

                intentNeighbour.setFavorite(!intentNeighbour.isFavorite());
                mNeighbourApiService.createFavoriteNeighbour(intentNeighbour);

            }
        });
    }


}