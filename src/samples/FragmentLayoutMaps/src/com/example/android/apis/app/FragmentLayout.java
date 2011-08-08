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

import com.example.android.apis.R;
import com.example.android.apis.Shakespeare;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class FragmentLayout extends FragmentActivity {

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
					adapterRowLayoutId, Shakespeare.TITLES));

			if (savedInstanceState != null) {
				// Restore last state for checked position.
				mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
				mShownCheckPosition = savedInstanceState.getInt("shownChoice", -1);
			}

			if (mDualPane) {
				// In dual-pane mode, the list view highlights the selected item.
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				// Make sure our UI is in the correct state.
				showDetails(mCurCheckPosition);
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
			showDetails(position);
		}

		/**
		 * Helper function to show the details of a selected item, either by
		 * displaying a fragment in-place in the current UI, or starting a
		 * whole new activity in which it is displayed.
		 */
		void showDetails(int index) {
			mCurCheckPosition = index;

			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			if (mDualPane) {
				// We can display everything in-place with fragments, so update
				// the list to highlight the selected item and show the data.
				getListView().setItemChecked(index, true);

				if (mShownCheckPosition != mCurCheckPosition) {
					// If we are not currently showing a fragment for the new
					// position, we need to create and install a new one.
					DetailsFragment df = DetailsFragment.newInstance(index);

					// Execute a transaction, replacing any existing fragment
					// with this one inside the frame.
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.details, df, "DetailsFragment");
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
					mShownCheckPosition = index;
				}

			} else {
				DetailsFragment details = DetailsFragment.newInstance( index );
				
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.main, details);
				ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
				ft.addToBackStack( null );
				ft.commit();
			}
		}
	}


	/**
	 * This is the secondary fragment, displaying the details of a particular
	 * item.
	 */

	public static class DetailsFragment extends Fragment {
		/**
		 * Create a new instance of DetailsFragment, initialized to
		 * show the text at 'index'.
		 */
		public static DetailsFragment newInstance(int index) {
			DetailsFragment f = new DetailsFragment();

			// Supply index input as an argument.
			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if (container == null) {
				// We have different layouts, and in one of them this
				// fragment's containing frame doesn't exist.  The fragment
				// may still be created from its saved state, but there is
				// no reason to try to create its view hierarchy because it
				// won't be displayed.  Note this is not needed -- we could
				// just run the code below, where we would create and return
				// the view hierarchy; it would just never be used.
				return null;
			}

			ScrollView scroller = new ScrollView(getActivity());
			TextView text = new TextView(getActivity());
			int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					4, getActivity().getResources().getDisplayMetrics());
			text.setPadding(padding, padding, padding, padding);
			scroller.addView(text);
			text.setText(Shakespeare.DIALOGUE[getArguments().getInt("index", 0)]);
			return scroller;
		}
	}

}
