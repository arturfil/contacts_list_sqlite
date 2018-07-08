package com.arturofilio.contact_app_sqlite;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.arturofilio.contact_app_sqlite.Utils.UniversalImageLoader;
import com.arturofilio.contact_app_sqlite.models.Contact;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends AppCompatActivity implements
        ViewContactsFragment.OnContactSelectedListener,
        ContactFragment.OnEditContactListener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 1;

    @Override
    public void onEditcontactSelected(Contact contact) {
        Log.d(TAG, "OnContactSelected: contact selected from " +
                getString(R.string.edit_contact_fragment) + " " + contact.getName());

        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.contact), contact);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(getString(R.string.edit_contact_fragment));
        transaction.commit();
    }

    @Override
    public void OnContactSelected(Contact contact) {
        Log.d(TAG, "OnContactSelected: contact selected from " + getString(R.string.view_contacts_fragment) + " " + contact.getName());

        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.contact), contact);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(getString(R.string.contact_fragment));
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        initImageLoader();


        init();

    }

    /**
     * initialize the first fragment (ViewContactsFragment)
     */
    private void init(){
        ViewContactsFragment fragment = new ViewContactsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // reaplce whatever is in the fragment_container view with this fragment,
        // amd add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    /**
     * Generalized method for asking permission Can pass any array of permissions
     * @param permissions
     */
    public void verifyPermissions(String[] permissions) {
        Log.d(TAG, "verifyPermissions: asking user for permissions.");
        ActivityCompat.requestPermissions(
                MainActivity.this,
                permissions,
                REQUEST_CODE
        );
    }

    /**
     * checks to see if permission was granted for the passed parameters
     * ONLY ONE PERMISSION MAY BE CHECKED AT A TIME
     * @param permission
     * @return
     */
    public boolean checkPermission(String[] permission){
        Log.d(TAG, "checkPermission: checkPermission: check permissions for: " + permission[0]);

        int permissionRequest = ActivityCompat.checkSelfPermission(
                MainActivity.this,
                permission[0]);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermission: \n Permission was not granted for: " + permission[0]);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode);

        switch (requestCode) {
            case REQUEST_CODE:
                for(int i = 0; i < permissions.length; i++) {
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "onRequestPermissionsResult: user has allowed permisison to access: " + permissions[i]);
                    } else {
                        break;
                    }
                }

                break;
        }
    }

}
