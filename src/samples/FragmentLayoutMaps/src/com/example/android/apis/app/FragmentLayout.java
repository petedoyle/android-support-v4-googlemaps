/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.app;

import com.example.android.apis.Locations;
import com.example.android.apis.Locations.Entry;
import com.example.android.apis.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class FragmentLayout extends FragmentActivity {

	// Only one MapView instance is allowed per MapActivity,
	// so we inflate them in the MapActivity and tie their 
	// lifetime here to the MapActivity.  Package scope
	// so we can grab them from different instances of map 
	// fragments.
	//
	// The other option was to make them static, but that causes
	// memory leaks on screen rotation.
	View mMapViewContainer;
	MapView mMapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragment_layout);
		
		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI.
		View detailsFrame = findViewById(R.id.details);
		boolean dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

		// Decide where to place TitlesFragment.
		// See fragment_layout.xml in res/layout/ and res/layout-land/ to see the difference
		int targetLayout = dualPane ? R.id.titles : R.id.main;
		
		// do not add to backstack, or user will be able to press back and
		// view the blank layout with nothing in it (a blank screen).
		// In this case, we want the back button to exit the app.
		getSupportFragmentManager()
			.beginTransaction()
			.add( targetLayout, TitlesFragment.newInstance( dualPane ) )
			.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
			.commit();
		
		mMapViewContainer = LayoutInflater.from( this ).inflate( R.layout.mapview, null );
		mMapView = (MapView)mMapViewContainer.findViewById( R.id.map );
		
		//TODO: There's still a bug.  To reproduce:
		// 1) Start app in portrait mode.
		// 2) Flip to landscape.
		// 3) Flip back to portrait.
		// 4) Select an item (item displays fine)
		// 5) Press back
		// 6) Select another item
		//    RESULT:   Both the details and list view display on top of each other
		//    EXPECTED: The details should show without the list
	}

	/**
	 * This is the "top-level" fragment, showing a list of items that the
	 * user can pick.  Upon picking an item, it takes care of displaying the
	 * data to the user as appropriate based on the currrent UI layout.
	 */
	public static class TitlesFragment extends ListFragment {
		private static final String ARG_DUAL_PANE = "isDualPane";
		
		boolean mDualPane;
		int mCurCheckPosition = 0;
		int mShownCheckPosition = -1;

		public static TitlesFragment newInstance(boolean isDualPane) {
			TitlesFragment fragment = new TitlesFragment();
			
			Bundle args = new Bundle();
			args.putBoolean( ARG_DUAL_PANE, isDualPane );
			fragment.setArguments( args );
			
			return fragment;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate( savedInstanceState );
			mDualPane = getArguments().getBoolean( ARG_DUAL_PANE );
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			int adapterRowLayoutId = -1;
			if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB ) {
				// Crashes below honeycomb: "IllegalStateException: ArrayAdapter requires the resource ID to be a TextView"
				adapterRowLayoutId = android.R.layout.simple_list_item_activated_1;
			} else {
				adapterRowLayoutId = android.R.layout.simple_list_item_1;
			}

			// Populate list with our static array of titles.
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					adapterRowLayoutId, Locations.NAMES));

			if (savedInstanceState != null) {
				// Restore last state for checked position.
				mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
				mShownCheckPosition = savedInstanceState.getInt("shownChoice", -1);
			}

			if (mDualPane) {
				// In dual-pane mode, the list view highlights the selected item.
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				// Make sure our UI is in the correct state.
				showMap(mCurCheckPosition);
			}
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt("curChoice", mCurCheckPosition);
			outState.putInt("shownChoice", mShownCheckPosition);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			showMap(position);
		}

		/**
		 * Helper function to show the map of a selected item, either by
		 * displaying a fragment in-place in the current UI, or starting a
		 * whole new activity in which it is displayed.
		 */
		void showMap(int index) {
			mCurCheckPosition = index;

			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			if (mDualPane) {
				// We can display everything in-place with fragments, so update
				// the list to highlight the selected item and show the data.
				getListView().setItemChecked(index, true);

				if (mShownCheckPosition != mCurCheckPosition) {
					// If we are not currently showing a fragment for the new
					// position, we need to create and install a new one.
					MapFragment df = MapFragment.newInstance(index);

					// Execute a transaction, replacing any existing fragment
					// with this one inside the frame.
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.details, df);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
					mShownCheckPosition = index;
				}

			} else {
				MapFragment details = MapFragment.newInstance( index );
				
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.main, details);
				ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
				ft.addToBackStack( null );
				ft.commit();
			}
		}
	}

	public static class MapFragment extends Fragment {
		private View mMapViewContainer;
		private MapView mMapView;
		
		private MapController mMapController;
		
		/**
		 * Create a new instance of MapFragment, initialized to
		 * show the location Entry at 'index'.
		 */
		public static MapFragment newInstance(int index) {
			MapFragment f = new MapFragment();

			// Supply index input as an argument.
			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView( inflater, container, savedInstanceState );

			/*	We've tied the MapView's lifecycle to the MapActivity (FragmentLayout
				extends FragmentActivity which extends MapActivity (in the
				android-support-v4-googlemaps library)).
				
				Unfortunately we have to grab a reference to it here, which means
				we have to have a reference to FragmentLayout (a _specific_ 
				implementation of FragmentActivity).  This is a hack.
				
				We could "inject" (or pass in) these via MapFragment's constructor,
				but it seems to break the general usage pattern of Fragments (i.e.
				pass in a Bundle of arguments, each of which is Parcelable / Serializable).
			
				I chose to do call up to FragmentLayout to do this, but injecting
				the references into the constructor might be better.  Its up to you.
			 * 
			 */
			FragmentLayout mapActivity = (FragmentLayout) getActivity();
			mMapViewContainer = mapActivity.mMapViewContainer;
			mMapView = mapActivity.mMapView;
			if( null != mMapView ) {
				int index = getArguments().getInt( "index" );
				Entry e = Locations.ENTRIES[index];
				
				mMapView.setBuiltInZoomControls( true );
				mMapView.setSatellite( true );
				
				mMapController = mMapView.getController();
				mMapController.animateTo( new GeoPoint( (int)(e.lat * 1.0e6), (int)(e.lng * 1.0e6) ) );
				mMapController.setZoom( e.zoomLevel );
			}
			
			return mMapViewContainer;
		}
		
		@Override
		public void onDestroyView() {
			super.onDestroyView();

			// The way MainActivity creates this fragment, it will call onCreateView()
			// each time we start (or navigate back to) this map.  To prevent the
			// "You are only allowed to have a single MapView in a MapActivity" message,
			// we only inflate the map's XML layout once.  When we try to add it a second
			// time, we get "IllegalStateException: The specified child already has a 
			// parent. You must call removeView() on the child's parent first."
			// So, here we remove the view from MainActivity's parent layout
			// so we can re-add it later when onCreateView() is called.
			// TODO: change this once the map doesn't go away (i.e. on Tablets)
			ViewGroup parentViewGroup = (ViewGroup) mMapViewContainer.getParent();
			if( null != parentViewGroup ) {
				parentViewGroup.removeView( mMapViewContainer );
			}
		}
	}
}
