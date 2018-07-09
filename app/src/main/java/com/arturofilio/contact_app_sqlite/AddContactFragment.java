package com.arturofilio.contact_app_sqlite;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.arturofilio.contact_app_sqlite.Utils.ChangePhotoDialog;
import com.arturofilio.contact_app_sqlite.Utils.Init;
import com.arturofilio.contact_app_sqlite.Utils.UniversalImageLoader;
import com.arturofilio.contact_app_sqlite.models.Contact;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddContactFragment extends Fragment implements ChangePhotoDialog.OnPhotoReceiveListener {

    private static final String TAG = "AddContactFragment";

    private Contact mContact;
    private EditText mPhoneNumber, mName, mEmail;
    private CircleImageView mProfileImage;
    private Spinner mSelectDevice;
    private Toolbar toolbar;
    private String mSelectedPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addcontact, container, false);

        mPhoneNumber = (EditText) view.findViewById(R.id.etContactPhone);
        mName = (EditText) view.findViewById(R.id.etContactName);
        mEmail = (EditText) view.findViewById(R.id.etEmail);
        mProfileImage = (CircleImageView) view.findViewById(R.id.contactImage);
        mSelectDevice = (Spinner) view.findViewById(R.id.selectDevice);
        toolbar = (Toolbar) view.findViewById(R.id.editContactToolbar);
        Log.d(TAG, "onCreateView: started");

        mSelectedPath = null;

        //Load the default Image by causing an error
        UniversalImageLoader.setImage(null, mProfileImage,null, "");


        //set the heading the toolbar
        TextView heading = (TextView) view.findViewById(R.id.textCotnactToolbar);
        heading.setText(getString(R.string.add_contact));

        //setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        //navigation for the backarrow
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                //remove previous fragment form the back-stack (therefore navigating back)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // save new contact
        ImageView ivCheckMark = (ImageView) view.findViewById(R.id.ivCheckMark);
        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving new contact");
                //execute the save method for the database
            }
        });

        // initiate the dialog box or choosing an image
        ImageView ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Make sure all permissions have been verified before opening the dialog
                 */
                for (int i = 0; i < Init.PERMISSIONS.length; i++) {
                    String[] permission = {Init.PERMISSIONS[i]};
                    if(((MainActivity)getActivity()).checkPermission(permission)){
                        if(i == Init.PERMISSIONS.length - 1) {
                            Log.d(TAG, "onClick: opening the image selection dialog box.");
                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
                            dialog.setTargetFragment(AddContactFragment.this, 0);
                        }
                    } else {
                        ((MainActivity)getActivity()).verifyPermissions(permission);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: deleting content ");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Retrieves the selected image from the bundle (coming form ChangeBundle Dialog)
     * @param bitmap
     */
    @Override
    public void getBitmapImage(Bitmap bitmap) {
        Log.d(TAG, "getBitmapImage: get the bitmap: " + bitmap);
        // get the bitmap from 'ChangePhotoDialog'
        if(bitmap != null) {
            //compress the image (if you like)
            ((MainActivity)getActivity()).compressBitmap(bitmap, 70);
            mProfileImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void getImagePath(String imagePath) {
        Log.d(TAG, "getImagePath: got the image path: " + imagePath);

        if(!imagePath.equals("")){
            imagePath = imagePath.replace(":/", "://");
            mSelectedPath = imagePath;
            UniversalImageLoader.setImage(imagePath,  mProfileImage, null, "");
        }
    }
}
