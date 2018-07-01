package com.arturofilio.contact_app_sqlite;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class ViewContactsFragment extends Fragment {

    private static final String TAG = "ViewContactsFragment";

    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarrState;

    private AppBarLayout viewContactsBar, searchBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcontacts, container, false);
        viewContactsBar = (AppBarLayout) view.findViewById(R.id.viewContactsToolbar);
        searchBar = (AppBarLayout) view.findViewById(R.id.searchToolbar);
        Log.d(TAG, "onCreateView: started");

        setAppBarState(STANDARD_APPBAR);

        // navigate to add contacts fragment
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked fab");
            }
        });

        ImageView ivSearchContact = (ImageView) view.findViewById(R.id.ivSearchIcon);
        ivSearchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked search icon.");
                toggleToolBarState();
            }
        });

        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                toggleToolBarState();
            }
        });

        return view;
    }

    /**
     * Initiates the appbar state toggle
     */
    private void toggleToolBarState() {
        Log.d(TAG, "toggleToolBarState: toggling AppBarState");
        
        if(mAppBarrState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR);
        } else {
            setAppBarState(STANDARD_APPBAR);
        }
    }

    /**
     * Sets the appbar state for the search 'mode' or 'standard' mode
     * @param state
     */
    private void setAppBarState(int state) {
        Log.d(TAG, "setAppBarState: changing app bar state to: " + state);

        mAppBarrState = state;

        if(mAppBarrState == STANDARD_APPBAR){
            searchBar.setVisibility(View.GONE);
            viewContactsBar.setVisibility(View.VISIBLE);

            //hide the keyboard
            View view = getView();
            InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (NullPointerException e) {
                Log.d(TAG, "setAppBarState: NullpointerException " + e.getMessage());
            }

        } else if (mAppBarrState == SEARCH_APPBAR) {
            viewContactsBar.setVisibility(View.GONE);
            searchBar.setVisibility(View.VISIBLE);

            //open the keyboard
            InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);

    }
}
