package com.ozawa.hextcgdeckbuilder;

import com.ozawa.hextcgdeckbuilder.UI.CardViewer;
import com.ozawa.hextcgdeckbuilder.UI.CustomViewPager;
import com.ozawa.hextcgdeckbuilder.UI.TabPagerAdapter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;

public class DeckUIActivity extends ActionBarActivity implements ActionBar.TabListener, NavigationDrawerFragment.NavigationDrawerCallbacks{
	
    public static CardViewer cardViewer;
    
	private CustomViewPager viewPager;
    private TabPagerAdapter mAdapter;
    public static ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Custom Deck", "Master Deck"};
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.deck_ui_activity);
        
	    // Initilization
	    viewPager = (CustomViewPager) findViewById(R.id.pager);
	    actionBar = getActionBar();
	    mAdapter = new TabPagerAdapter(getSupportFragmentManager());
	 
	    viewPager.setAdapter(mAdapter);
	    //actionBar.setHomeButtonEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
	 
	        // Adding Tabs
	    for (String tab_name : tabs) {
	        actionBar.addTab(actionBar.newTab().setText(tab_name)
	                 .setTabListener(this));
	    }
	    
	    /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
                if(position == 1){
                	viewPager.setPagingEnabled(false);
                }
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Open the list view for the deck
        switch (item.getItemId()) {
            case R.id.action_deck_view: 
            	mAdapter.masterDeckFragment.changeToGridView();
                return true;
            case R.id.action_list_view:
            	mAdapter.masterDeckFragment.changeToListView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
        
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		
	}

}