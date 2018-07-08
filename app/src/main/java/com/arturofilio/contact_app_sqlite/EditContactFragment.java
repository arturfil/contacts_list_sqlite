package com.arturofilio.contact_app_sqlite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arturofilio.contact_app_sqlite.models.Contact;

public class EditContactFragment extends Fragment {

    private static final String TAG = "EditContactFragment";

    //This will evade the nullpointer exception when adding to a new bundle from MainActivity
    public EditContactFragment(){
        super();
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editcontact, container, false);
        Log.d(TAG, "onCreateView: started");

        getContactFromBundle();

        return view;
    }

    /**
     * Retrieves the selected contact from the bundle (coming from MainActivity)
     * @return
     */
    private Contact getContactFromBundle(){
        Log.d(TAG, "getContactFromBundle: argumetns" + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable(getString(R.string.contact));
        } else {
            return null;
        }
    }
}
