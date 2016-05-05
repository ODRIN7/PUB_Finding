/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// This code has some modifications to the original
// See http://developer.android.com/guide/components/fragments.html
// for a detailed discussion on the app
// I don't recommend toast as debug for flow but why not do that to get started.
// Better to use Log.d() which we introduced before. Toast is fleeting and logs
// will always in in the LogCat -- hence they are more useful and better practice;
// but you can't see them on the phone. It is sort cool to see onCreate() toast
// as you flip the phone's orientation. It reinforces the lifecycle and the
// automatic adjustment of the UI.
//
// ATC 2013

package com.example.daniel.pub_finder;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.entities.Pub;
import com.example.daniel.entities.User;
import com.example.daniel.facades.DataBaseHelper;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.j256.ormlite.stmt.Where;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// Demonstration of using fragments to implement different activity layouts.
// This sample provides a different layout (and activity flow) when run in
// landscape.

public class FragmentLayout extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "FragmentLayout: OnCreate()", Toast.LENGTH_SHORT)
                .show();

        // Sets the view. Depending on orientation it will select either
        // res/layout/fragment_layout.xml (portrait mode) or
        // res/layout-land/fragment_layout.xml (landscape mode). This is done
        // automatically by the system.


        setContentView(R.layout.activity_activity_fragment_layout);


    }




    public static class DetailsActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Toast.makeText(this, "DetailsActivity", Toast.LENGTH_SHORT).show();

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // If the screen is now in landscape mode, we can show the
                // dialog in-line with the list so we don't need this activity.
                finish();
                return;
            }

            if (savedInstanceState == null) {
                // During initial setup, plug in the details fragment.

                // create fragment
                DetailsFragment details = new DetailsFragment();

                // get and set the position input by user (i.e., "index")
                // which is the construction arguments for this fragment
                details.setArguments(getIntent().getExtras());

                //


                getFragmentManager().beginTransaction()
                        .add(android.R.id.content, details).commit();
            }

        }
    }



    public static class TitlesFragment extends ListFragment {
        boolean mDualPane;
        int mCurCheckPosition = 0;

        DataBaseHelper<Pub> pubDataBaseHelper;
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {


            super.onActivityCreated(savedInstanceState);


            Toast.makeText(getActivity(), "TitlesFragment:onActivityCreated",
                    Toast.LENGTH_LONG).show();


            try {
               pubDataBaseHelper = new DataBaseHelper<Pub>(getActivity(), Pub.class);
                pubDataBaseHelper.onCreate(pubDataBaseHelper.getWritableDatabase(), pubDataBaseHelper.getConnectionSource())
                ;

                List<Pub> pubs = pubDataBaseHelper.getGenericDao().queryForAll();

                String[] pubstoString = new String[pubs.size()];
                for (int i = 0; i < pubs.size(); i++) {
                    pubstoString[i] = pubs.get(i).getName();
                }
                setListAdapter(new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_activated_1, pubstoString));


                View detailsFrame = getActivity().findViewById(R.id.details);

                Toast.makeText(getActivity(), "detailsFrame " + detailsFrame,
                        Toast.LENGTH_LONG).show();

                mDualPane = detailsFrame != null
                        && detailsFrame.getVisibility() == View.VISIBLE;

                Toast.makeText(getActivity(), "mDualPane " + mDualPane,
                        Toast.LENGTH_LONG).show();

                if (savedInstanceState != null) {
                    // Restore last state for checked position.
                    mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
                }

                if (mDualPane) {

                    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    showDetails(mCurCheckPosition);
                } else {
                    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    getListView().setItemChecked(mCurCheckPosition, true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            Toast.makeText(getActivity(), "onSaveInstanceState",
                    Toast.LENGTH_LONG).show();

            outState.putInt("curChoice", mCurCheckPosition);
        }


        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {

            Toast.makeText(getActivity(),
                    "onListItemClick position is" + position, Toast.LENGTH_LONG)
                    .show();

            showDetails(position);
        }


        void showDetails(int index) {
            mCurCheckPosition = index;


            if (mDualPane) {

                getListView().setItemChecked(index, true);


                DetailsFragment details = (DetailsFragment) getFragmentManager()
                        .findFragmentById(R.id.details);
                if (details == null || details.getShownIndex() != index) {


                    details = DetailsFragment.newInstance(index);

                    Toast.makeText(getActivity(),
                            "showDetails dual-pane: create and relplace fragment",
                            Toast.LENGTH_LONG).show();

                    FragmentTransaction ft = getFragmentManager()
                            .beginTransaction();
                    ft.replace(R.id.details, details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }

            } else {

                Intent intent = new Intent();

                intent.setClass(getActivity(), DetailsActivity.class);

                intent.putExtra("index", index);

                startActivity(intent);
            }
        }
    }

    public static class DetailsFragment extends Fragment {
        private AppCompatRatingBar ratingBar;
        private AppCompatButton gpsAppCompatButton;
        private AppCompatRatingBar bestrate;
        private AppCompatSpinner appCompatSpinner;
        private List<Pub> pubs;
        private User user;
        DataBaseHelper<User> userDataBaseHelper;
        // Create a new instance of DetailsFragment, initialized to show the
        // text at 'index'.

        public static DetailsFragment newInstance(int index) {
            DetailsFragment f = new DetailsFragment();

            // Supply index input as an argument.
            Bundle args = new Bundle();
            args.putInt("index", index);
            f.setArguments(args);

            return f;
        }

        public int getShownIndex() {
            return getArguments().getInt("index", 0);
        }


        private static final int PLACE_PICKER_REQUEST = 1;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            DataBaseHelper<Pub> pubDataBaseHelper = new DataBaseHelper<Pub>(getActivity(), Pub.class);
            pubDataBaseHelper.onCreate(pubDataBaseHelper.getWritableDatabase(), pubDataBaseHelper.getConnectionSource())
            ;
            this.userDataBaseHelper = new DataBaseHelper<User>(getActivity(),User.class);
            Intent myIntent = getActivity().getIntent();
            int iD =myIntent.getIntExtra("user_id",0);

            try {

                Where<User, Integer> userFind= userDataBaseHelper.getGenericDao().queryBuilder().where().eq("user_id",iD);
                user =userFind.queryForFirst();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pubs = new ArrayList<>();

            try {
                pubs = pubDataBaseHelper.getGenericDao().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            downloadListener();
            locationListener();
            callListener();
            addListenerOnRatingBar();
            bestRatelistner();
            spinnerListner();


            String[] pubstoString = new String[pubs.size()];
            for (int i = 0; i < pubs.size(); i++) {
                pubstoString[i] = pubs.get(i).getDescription();
            }

            Toast.makeText(getActivity(), "DetailsFragment:onCreateView",
                    Toast.LENGTH_LONG).show();

            ScrollView scroller = new ScrollView(getActivity());
            TextView text = new TextView(getActivity());
            int padding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 4, getActivity()
                            .getResources().getDisplayMetrics());
            text.setPadding(padding, padding, padding, padding);
            scroller.addView(text);
            AppCompatTextView appCompatTextView = (AppCompatTextView) getActivity().findViewById(R.id.description);
            appCompatTextView.setText(pubs.get(getShownIndex()).getDescription());
            return scroller;

        }



        public void downloadListener() {
            AppCompatButton downloadAppCompatButton = (AppCompatButton) getActivity().findViewById(R.id.download);
            downloadAppCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownloadManager manager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                    Uri Download_Uri = Uri.parse(pubs.get(getShownIndex()).getDrinkURI());
                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle("Drinks");
                    request.setDescription("See the drinks");
                    request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS, "xxx.png");
                    manager.enqueue(request);
                    long downloadReference = manager.enqueue(request);
                    try {
                        ParcelFileDescriptor file = manager.openDownloadedFile(downloadReference);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
                new LatLng(47.497622, 19.069209), new LatLng(47.497622, 19.069209));
        public void locationListener() {
            gpsAppCompatButton = (AppCompatButton) getActivity().findViewById(R.id.gpsPlace);
            gpsAppCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        PlacePicker.IntentBuilder intentBuilder =
                                new PlacePicker.IntentBuilder();
                      LatLngBounds publoc =  new LatLngBounds( new LatLng(pubs.get(getShownIndex()).getLat(),pubs.get(getShownIndex()).getLon()),
                              new LatLng(pubs.get(getShownIndex()).getLat(),pubs.get(getShownIndex()).getLon()));
                        intentBuilder.setLatLngBounds(publoc);
                        Intent intent = intentBuilder.build(getActivity());
                        startActivityForResult(intent, PLACE_PICKER_REQUEST);

                    } catch (GooglePlayServicesRepairableException
                            | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            });

        }



        private void callListener() {
            AppCompatButton callAppCompatButton = (AppCompatButton) getActivity().findViewById(R.id.bookTable);
            callAppCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(getActivity(), new String[]{
                                //  Manifest.permission.CALL_PHONE;
                                Manifest.permission.CALL_PHONE
                        }, 10);
                        return;
                    }
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(pubs.get(getShownIndex()).getPhonenumber()))));
                }
            });
        }

        private void addListenerOnRatingBar() {

            ratingBar = (AppCompatRatingBar) getActivity().findViewById(R.id.ratingBar);

            ratingBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float a = ratingBar.getRating();
                }
            });

        }

        private void bestRatelistner() {
            bestrate = (AppCompatRatingBar) getActivity().findViewById(R.id.bestrate);
            bestrate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    user.getBest_pub().add(pubs.get(getShownIndex()));
                }
            });
        }

        private void rateBarListner() {
            ratingBar = (AppCompatRatingBar) getActivity().findViewById(R.id.ratingBar);
            ratingBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //pubs.get(getShownIndex()).setRating(ratingBar.getRating());
                }
            });
        }

        private void spinnerListner() {
            appCompatSpinner = (AppCompatSpinner) getActivity().findViewById(R.id.spr_select);
            appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (appCompatSpinner.getSelectedItem().toString().equals("Logout")) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else if (appCompatSpinner.getSelectedItem().toString().equals("Pubs")) {


                    } else if (appCompatSpinner.getSelectedItem().toString().equals("Favourite")) {
                      //  pubs = pubDataBaseHelper.getGenericDao().queryForAll();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }
}


