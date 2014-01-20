package com.ozawa.hextcgdeckbuilder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ozawa.hextcgdeckbuilder.UI.CardViewer;
import com.ozawa.hextcgdeckbuilder.UI.FilterButton;
import com.ozawa.hextcgdeckbuilder.enums.CardType;
import com.ozawa.hextcgdeckbuilder.enums.ColorFlag;

;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

	/**
	 * Remember the position of the selected item.
	 */
	private static final String			STATE_SELECTED_POSITION		= "selected_navigation_drawer_position";

	/**
	 * Per the design guidelines, you should show the drawer on launch until the
	 * user manually expands it. This shared preference tracks this.
	 */
	private static final String			PREF_USER_LEARNED_DRAWER	= "navigation_drawer_learned";

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private NavigationDrawerCallbacks	mCallbacks;

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private ActionBarDrawerToggle		mDrawerToggle;

	private DrawerLayout				mDrawerLayout;
	// private ListView mDrawerListView;
	private ScrollView					scrollView;
	private View						mFragmentContainerView;
	CardViewer							cardViewer;
	private Context						context;

	private int							mCurrentSelectedPosition	= 0;
	private boolean						mFromSavedInstanceState;
	private boolean						mUserLearnedDrawer;

	public NavigationDrawerFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Read in the flag indicating whether or not the user has demonstrated
		// awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
			mFromSavedInstanceState = true;
		}

		// Select either the default item (0) or the last selected item.
		selectItem(mCurrentSelectedPosition);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		scrollView = (ScrollView) inflater.inflate(R.layout.filter_layout, container, false);

		return scrollView;
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	private void setUpButtons() {
		Resources res = context.getResources();
		FilterButton button;

		button = (FilterButton) scrollView.findViewById(R.id.blood);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.blood_on), BitmapFactory.decodeResource(res, R.drawable.blood_off),
				ColorFlag.BLOOD, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.wild);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.wild_on), BitmapFactory.decodeResource(res, R.drawable.wild_off),
				ColorFlag.WILD, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.ruby);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.ruby_on), BitmapFactory.decodeResource(res, R.drawable.ruby_off),
				ColorFlag.RUBY, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.sapphire);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.sapphire_on), BitmapFactory.decodeResource(res, R.drawable.sapphire_off),
				ColorFlag.SAPPHIRE, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.diamond);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.diamond_on), BitmapFactory.decodeResource(res, R.drawable.diamond_off),
				ColorFlag.DIAMOND, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.colorless);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.colorless_on),
				BitmapFactory.decodeResource(res, R.drawable.colorless_off), ColorFlag.COLORLESS, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.troop);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.troop_on), BitmapFactory.decodeResource(res, R.drawable.troop_off),
				CardType.TROOP, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.basicaction);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.basic_on), BitmapFactory.decodeResource(res, R.drawable.basic_off),
				CardType.BASICACTION, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.quickaction);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.quick_on), BitmapFactory.decodeResource(res, R.drawable.quick_off),
				CardType.QUICKACTION, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.constant);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.constant_on), BitmapFactory.decodeResource(res, R.drawable.constant_off),
				CardType.CONSTANT, cardViewer);
		button = (FilterButton) scrollView.findViewById(R.id.resource);
		cardViewer.addAssociatedButton(button);
		button.setUp(BitmapFactory.decodeResource(res, R.drawable.resource_on), BitmapFactory.decodeResource(res, R.drawable.resource_off),
				CardType.RESOURCE, cardViewer);
		EditText text = (EditText) scrollView.findViewById(R.id.SearchTextField);
		text.addTextChangedListener(cardViewer);
		cardViewer.addAssociatedTextView(text);
		text.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

					if (inputManager.isActive()) {
						inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					}
				}
			}
		});
	}

	public void setUpCustomDeckViews() {
		Button newDeck = (Button) scrollView.findViewById(R.id.buttonNewDeck);
		newDeck.setHapticFeedbackEnabled(true);
		newDeck.setVisibility(View.VISIBLE);

		Button loadDeck = (Button) scrollView.findViewById(R.id.buttonLoadDeck);
		loadDeck.setHapticFeedbackEnabled(true);
		loadDeck.setVisibility(View.VISIBLE);

		Button saveDeck = (Button) scrollView.findViewById(R.id.buttonSaveDeck);
		saveDeck.setHapticFeedbackEnabled(true);
		saveDeck.setVisibility(View.VISIBLE);

		Button deleteDeck = (Button) scrollView.findViewById(R.id.buttonDeleteDeck);
		deleteDeck.setHapticFeedbackEnabled(true);
		deleteDeck.setVisibility(View.VISIBLE);

		Button selectChampion = (Button) scrollView.findViewById(R.id.buttonSelectChampion);
		selectChampion.setHapticFeedbackEnabled(true);
		selectChampion.setVisibility(View.VISIBLE);

		ImageView championPortrait = (ImageView) scrollView.findViewById(R.id.imageChampionPortrait);
		championPortrait.setVisibility(View.VISIBLE);

		TextView championName = (TextView) scrollView.findViewById(R.id.tvChampionName);
		championName.setVisibility(View.VISIBLE);

		TextView deckCardCount = (TextView) scrollView.findViewById(R.id.tvDeckCardCount);
		deckCardCount.setVisibility(View.VISIBLE);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 * 
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(CardViewer iCardViewer, Context iContext, int fragmentId, DrawerLayout drawerLayout) {
		context = iContext;
		cardViewer = iCardViewer;
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.drawable.ic_drawer, R.string.navigation_drawer_open,
				R.string.navigation_drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {
					// The user manually opened the drawer; store this flag to
					// prevent auto-showing
					// the navigation drawer automatically in the future.
					mUserLearnedDrawer = true;
					SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
				}

				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}
		};

		// If the user hasn't 'learned' about the drawer, open it to introduce
		// them to the drawer,
		// per the navigation drawer design guidelines.
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	public void setUp(View frag, CardViewer iCardViewer, Context iContext, int fragmentId, DrawerLayout drawerLayout) {
		context = iContext;
		cardViewer = iCardViewer;
		mFragmentContainerView = frag.findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		setUpButtons();
	}

	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		// if (mDrawerListView != null) {
		// mDrawerListView.setItemChecked(position, true);
		// }
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// If the drawer is open, show the global app actions in the action bar.
		// See also
		// showGlobalContextActionBar, which controls the top-left area of the
		// action bar.
		/*
		 * if (mDrawerLayout != null && isDrawerOpen()) {
		 * //inflater.inflate(R.menu.global, menu);
		 * inflater.inflate(R.menu.action_bar_menu, menu);
		 * showGlobalContextActionBar(); }
		 */
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		if (item.getItemId() == R.id.action_example) {
			Toast.makeText(getActivity(), "I said don\'t press.", Toast.LENGTH_SHORT).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Per the navigation drawer design guidelines, updates the action bar to
	 * show the global app 'context', rather than just what's in the current
	 * screen.
	 */
	private void showGlobalContextActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(R.string.app_name);
	}

	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(int position);
	}

	/*
	 * private void setPagingEnabled(boolean enabled){ CustomViewPager pager =
	 * (CustomViewPager) super.getActivity().findViewById(R.id.pager);
	 * pager.setPagingEnabled(enabled); }
	 */

}
