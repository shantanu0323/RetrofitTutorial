package com.shaan.retrofittutorial;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    String pkg = "com.shaan.retrofittutorial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView listView = findViewById(R.id.listView);
        final String[] activities = {
                "GithubActivity",
                "RegisterActivity",
                "UploadActivity",
                "ContactsActivity",
                "CaloriesActivity",
                "ImageActivity"};
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent res = new Intent();
                String mClass = ".actYouAreLaunching";
                res.setComponent(new ComponentName(pkg, pkg + "." + activities[i]));
                try {
                    startActivity(res);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this, "Can't find Activity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
