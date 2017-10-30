package com.example.leonardo.each_os_problemas_user;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class StoreProblem extends AppCompatActivity {

    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_problem);
        takePhoto();
    }

    private static final String TAG = "upload";

    private void takePhoto() {
        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            setPic();
        }
    }

    private void setPic() {
        Log.d("path", mCurrentPhotoPath);
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        photo = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        //sendPhoto(photo);
    }

    protected  void storeProblem(View view) {
        String description = ((EditText) findViewById(R.id.txtDescription)).getText().toString();
        String place = ((EditText) findViewById(R.id.txtPlace)).getText().toString();
        String type = ((EditText) findViewById(R.id.txtType)).getText().toString();
        String nusp = getNusp();
        StoreProblemTask(photo, description, place, 1, nusp);
    }

    private String getNusp(){
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.shared_preference_key),MODE_PRIVATE);
        String nusp = sharedPref.getString(getString(R.string.nusp_key), null);
        return nusp;
    }

    public void StoreProblemTask(Bitmap bitmap, final String description, final String place, final int type, final String nusp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskStoreProblemClass extends AsyncTask<Void, Void, String> {

            private ProgressBar progress;

            public AsyncTaskStoreProblemClass(){
            }

            @Override
            protected void onPreExecute() {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progress = (ProgressBar) findViewById(R.id.progressBar);
                progress.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String response) {
                progress.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                try {
                    JSONObject json = new JSONObject(response);
                    Log.d("json", json.getString("success"));
                    if(json.getInt("success") == 1){
                        Toast.makeText(getApplicationContext(), "Problema Cadastrado!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("response",response);
            }

            @Override
            protected String doInBackground(Void... params) {
                UserFunctions uf = new UserFunctions();
                String response = uf.storeProblem(nusp, description, type, place, image_tag, ConvertImage);
                return response;
            }
        }
        AsyncTaskStoreProblemClass AsyncTaskUploadClassOBJ = new AsyncTaskStoreProblemClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //TODO catch error
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.leonardo.each_os_problemas_user.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * http://developer.android.com/training/camera/photobasics.html
     */
    String mCurrentPhotoPath;
    String image_tag;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        image_tag = imageFileName;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
