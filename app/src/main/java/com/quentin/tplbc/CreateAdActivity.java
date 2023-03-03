package com.quentin.tplbc;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.quentin.tplbc.models.AdModel;

public class CreateAdActivity extends AppCompatActivity {

    private ShapeableImageView previewImage;

    private boolean working;
    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Bitmap image = (Bitmap) data.getExtras().get("data");
                previewImage.setImageBitmap(image);
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
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        working = false;

        previewImage = findViewById(R.id.previewImage);
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

                double price = Double.parseDouble(((TextInputLayout) findViewById(R.id.labelPrice)).getEditText().getText().toString());

                String imgUrl = "";

                DBManager.getDBManager(CreateAdActivity.this).insert(new AdModel(title, address, imgUrl, price));

                Intent mainActivityIntent = new Intent(CreateAdActivity.this, MainActivity.class);
                mainActivityIntent.putExtra("newAdTittle", title);
                startActivity(mainActivityIntent);

                finish();


            }
        });

    }
}