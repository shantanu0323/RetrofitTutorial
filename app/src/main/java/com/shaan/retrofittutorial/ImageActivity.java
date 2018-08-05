package com.shaan.retrofittutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shaan.retrofittutorial.Model.ImageFile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageActivity extends AppCompatActivity {

    private EditText etTitle;
    private Button bChoose, bUpload;
    private ImageView imageView;
    private static final int IMG_REQUEST = 754;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        etTitle = findViewById(R.id.etTitle);
        bChoose = findViewById(R.id.bChoose);
        bUpload = findViewById(R.id.bUpload);
        imageView = findViewById(R.id.imageView);

        bChoose.setOnClickListener(event -> {
            selectImage();
        });

        bUpload.setOnClickListener(event -> {
            uploadImage();
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                bChoose.setEnabled(false);
                bUpload.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage () {
        String image = imageToString();
        String title = etTitle.getText().toString().trim();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageFile> call = apiInterface.uploadImage(title,image);
        call.enqueue(new Callback<ImageFile>() {
            @Override
            public void onResponse(Call<ImageFile> call, Response<ImageFile> response) {
                ImageFile imageFile = response.body();
                Toast.makeText(ImageActivity.this, imageFile.getResponse(), Toast.LENGTH_SHORT).show();
                bChoose.setEnabled(true);
                bUpload.setEnabled(false);
                etTitle.setText("");
            }

            @Override
            public void onFailure(Call<ImageFile> call, Throwable t) {

            }
        });

    }
    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
