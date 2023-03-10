package com.example.tpleboncoin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.example.tpleboncoin.AdModel;

import java.io.FileOutputStream;
import java.util.UUID;
import java.util.logging.Logger;

public class CreateAdActivity extends AppCompatActivity {

    private ShapeableImageView previewImage;
    private boolean working;


    private Bitmap selectedBitmap;
    private Uri selectedUri;

    // Ad en cours edition optinnel
    private AdModel editedAd;

    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Bitmap image = (Bitmap) data.getExtras().get("data");
                previewImage.setImageBitmap(image);


                selectedBitmap= image;

            }
        }
    });

    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Uri image = (Uri) result.getData().getData();
                previewImage.setImageURI(image);

                selectedUri = image;
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        working = false;
        selectedBitmap = null;
        selectedUri = null;
        previewImage = findViewById(R.id.previewImage);

        Intent intent = getIntent();

        if(intent != null && intent.hasExtra("editedAd"))
        {
            editedAd = (AdModel) intent.getSerializableExtra("editedAd");
        } else {
            editedAd = null;
        }

        if(editedAd != null)
        {
           ((TextInputLayout) findViewById(R.id.labelTitle)).getEditText().setText(editedAd.getTitle());
            ((TextInputLayout) findViewById(R.id.labelAddress)).getEditText().setText(editedAd.getAddress());

           ((TextInputLayout) findViewById(R.id.labelPrice)).getEditText().setText(editedAd.getPrice() + "");

           ((TextInputLayout) findViewById(R.id.labelPhone)).getEditText().setText(editedAd.getPhone());

            findViewById(R.id.editCameraImageBtn).setVisibility(View.GONE);
            findViewById(R.id.editGaleryImageBtn).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.adViewTitle)).setText(R.string.activityEditAdTitle);
            ((Button)  findViewById(R.id.submitBtn)).setText(R.string.editBtn);
        } else {
            findViewById(R.id.editCameraImageBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraActivityResultLauncher.launch(camera);
                }
            });

            findViewById(R.id.editGaleryImageBtn).setOnClickListener(v -> {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryActivityResultLauncher.launch(gallery);
            });
        }



        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(working)
                {
                    return;
                }
                working = true;
                // save image en local

                String title = ((TextInputLayout) findViewById(R.id.labelTitle)).getEditText().getText().toString();
                String address = ((TextInputLayout) findViewById(R.id.labelAddress)).getEditText().getText().toString();

                String priceText = ((TextInputLayout) findViewById(R.id.labelPrice)).getEditText().getText().toString();

                String phoneText = ((TextInputLayout) findViewById(R.id.labelPhone)).getEditText().getText().toString();


                if(title.length() <= 0 || address.length() <= 0 || priceText.length() <= 0 || phoneText.length() <= 0)
                {
                    working = false;
                    Toast.makeText(CreateAdActivity.this, R.string.errorMissingFields, Toast.LENGTH_LONG).show();
                    return;
                }

                double price = Double.parseDouble(priceText);

                if(editedAd == null) {
                    if (selectedBitmap == null && selectedUri == null) {
                        working = false;
                        Toast.makeText(CreateAdActivity.this, R.string.errorMissingImage, Toast.LENGTH_LONG).show();
                        return;
                    }

                    String imgUrl = "internal" + UUID.randomUUID().toString().replace("-", "") + ".png";

                    if (selectedBitmap != null) {

                        // on là store
                        try {

                            Logger.getGlobal().info("TRYING TO SAVE : " + CreateAdActivity.this.getApplication().getCacheDir() + "/" + imgUrl);
                            FileOutputStream stream = new FileOutputStream(CreateAdActivity.this.getApplication().getCacheDir() + "/" + imgUrl);
                            selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            stream.close();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // on met simplement url

                        try {

                            Logger.getGlobal().info("TRYING TO SAVE : " + CreateAdActivity.this.getApplication().getCacheDir() + "/" + imgUrl);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(CreateAdActivity.this.getContentResolver(), selectedUri);

                            FileOutputStream stream = new FileOutputStream(CreateAdActivity.this.getApplication().getCacheDir() + "/" + imgUrl);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            stream.close();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    DBManager.getDBManager(CreateAdActivity.this).insert(new AdModel(title, address, imgUrl, price, phoneText));

                    Intent mainActivityIntent = new Intent(CreateAdActivity.this, MainActivity.class);
                    mainActivityIntent.putExtra("newAdTittle", title);
                    startActivity(mainActivityIntent);
                } else {

                    editedAd.setTitle(title);
                    editedAd.setAddress(address);
                    editedAd.setPhone(phoneText);
                    editedAd.setPrice(price);

                    // update
                    DBManager.getDBManager(CreateAdActivity.this).update(editedAd.getID(), editedAd);

                    Intent mainActivityIntent = new Intent(CreateAdActivity.this, MainActivity.class);
                    mainActivityIntent.putExtra("updatedAdTittle", title);
                    startActivity(mainActivityIntent);
                }
                finish();


            }
        });

    }
}