package edu.rs.budgetmanager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.rs.budgetmanager.edu.rs.budgetmanager.util.Constants;


public class MainActivity extends ActionBarActivity implements Callback{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    /**
     * this will contain any extra data being passed to the fragment .
     */
    public Map<String, String > data =  new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                // collecting data for the last fragment to submit into the database.
                if(i  == 2){

                   HowAndWhyFragment howAndWhyFragment = (HowAndWhyFragment)mSectionsPagerAdapter.fragMap.get(0);
                    getData().put(Constants.AMOUNT, howAndWhyFragment.amount.getText().toString());
                    getData().put(Constants.LABEL, howAndWhyFragment.getmAutoCompleteTextView().getText().toString());
                    getData().put(Constants.DESC, howAndWhyFragment.description.getText().toString());
                    WhenFragment whenFragment = (WhenFragment)mSectionsPagerAdapter.fragMap.get(1);
                    getData().put(Constants.TIME, whenFragment.time.getText().toString());
                    getData().put(Constants.DATE, whenFragment.date.getText().toString());
                    for (String object : getData().values()){
                        Log.d(this.toString(),object);
                    }
                }
                Log.d(this.toString(), "data collected is inside onPageSelected : " +getData().size());


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * implementing call back to get the Activity data in fragments.
     * @return
     */
    @Override
    public Map<String, String> getData() {
        return data;
    }

    @Override
    public void setData(String key,String value) {

    this.getData().put(key,value);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public HashMap<Integer, Fragment> fragMap =
                new HashMap<Integer, Fragment>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
           switch(position+1){
               case 1:
                   HowAndWhyFragment howAndWhyFragment = HowAndWhyFragment.newInstance(null, null);
                   fragMap.put(0,howAndWhyFragment);
                   return howAndWhyFragment;
               case 2:
                   WhenFragment whenFragment = WhenFragment.newInstance(null, null);
                   fragMap.put(1,whenFragment);
                   return whenFragment;
               case 3:
                   SureFragment sureFragment = SureFragment.newInstance(null, null);
                   fragMap.put(2,sureFragment);
                   return sureFragment;
           }
         return  null;
            /*return PlaceholderFragment.newInstance(position + 1);*/
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     *//*
    public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
*/
}
