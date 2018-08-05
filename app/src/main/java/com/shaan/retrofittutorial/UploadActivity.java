package com.shaan.retrofittutorial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final String TAG = "UploadActivity";
    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private static final String BASE_URL = "http://192.168.137.1:80/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        if (ContextCompat.checkSelfPermission(UploadActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UploadActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
        }

        Button bUpload = findViewById(R.id.bUpload);
        bUpload.setOnClickListener(event -> {
            Intent intent = new Intent();
            intent.setType("image/");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    PICK_IMAGE_FROM_GALLERY_REQUEST);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri uri = data.getData();
            uploadFile(uri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Do nothing as permission granted
                } else {
                    // Permission denied, terminate the further process
                }
        }
    }

    private void uploadFile(Uri uri) {
        final EditText etDesc = findViewById(R.id.etDesc);

        RequestBody descPart = RequestBody.create(MultipartBody.FORM, etDesc.getText().toString().trim());

        File originalFile = FileUtils.getFile(this, uri);
        RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)),
                originalFile);

        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        UserClient userClient = retrofit.create(UserClient.class);
        Call<ResponseBody> call = userClient.uploadFile(descPart,file);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
