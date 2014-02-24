package course.labs.fragmentslab;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements
		FriendsFragment.SelectionListener {

	private static final String TAG = "Lab-Fragments";
	private static final String DEBUG = "Lab-Fragments";

	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// If the layout is single-pane, create the FriendsFragment 
		// and add it to the Activity

		if (!isInTwoPaneMode()) {
			Log.d(DEBUG, "portrait mode is: "+!isInTwoPaneMode());
			mFriendsFragment = new FriendsFragment();

			//TODO 1 - add the FriendsFragment to the fragment_container
			
			//At any time while your activity is running, you can add 
			//fragments to your activity layout. You simply need to specify 
			//a ViewGroup in which to place the fragment.
			//To make fragment transactions in your activity (such as add, 
			//remove, or replace a fragment), you must use APIs from FragmentTransaction.
			//You can get an instance of FragmentTransaction from your Activity like this:
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			
			//You can then add a fragment using the add() method, specifying the fragment 
			//to add and the view in which to insert it.
			fragmentTransaction.add(R.id.fragment_container, mFriendsFragment);
			//fragmentManager.executePendingTransactions();
			fragmentTransaction.commit();
			Log.d(DEBUG, "I have just commit() the mFriendsFragment");
			
		} else {

			// Otherwise, save a reference to the FeedFragment for later use

			mFeedFragment = (FeedFragment) getFragmentManager().findFragmentById(R.id.feed_frag);
		}

	}

	// If there is no fragment_container ID, then the application is in
	// two-pane mode -->checked in res/layout/main_activity.xml

	private boolean isInTwoPaneMode() {

		return findViewById(R.id.fragment_container) == null;
	
	}

	// Display selected Twitter feed

	public void onItemSelected(int position) {

		Log.i(TAG, "Entered onItemSelected(" + position + ")");

		// If there is no FeedFragment instance, then create one
		//CREATING NEW FRAGMENT
		if (mFeedFragment == null)
			mFeedFragment = new FeedFragment();

		// If in single-pane mode, replace single visible Fragment

		if (!isInTwoPaneMode()) {

			//TODO 2 - replace the fragment_container with the FeedFragment
			//CREATING NEW TRANSACTION
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack
			transaction.replace(R.id.fragment_container, mFeedFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			

			// execute transaction now
			getFragmentManager().executePendingTransactions();

		}

		// Update Twitter feed display on FriendFragment
		mFeedFragment.updateFeedDisplay(position);

	}

}
