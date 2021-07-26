package com.tauseef.utils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.tauseef.tauseeflib.TauseefUtils;

public class MainActivity extends AppCompatActivity {
    public static final int WordChooseCode = 1;
    private static final String TAG = "MainActivity";
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = findViewById(R.id.tauseef_pb);

        findViewById(R.id.tauseef_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWordFileFromMobile();
            }
        });
    }

    //Choose word file from mobile
    private void chooseWordFileFromMobile() {
        try {
            //Pdf file extraction
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("*/*");
//            String[] mimetypes = {"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword"};
//            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);

            //Word file extraction
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");

            startActivityForResult(intent, WordChooseCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null)
            return;

        //get word file uri
        if (requestCode == WordChooseCode) {
            new Thread(() -> {
                runOnUiThread(() -> pb.setVisibility(View.VISIBLE));
                Uri uri = Uri.parse(String.valueOf(data.getData()));
                String value = TauseefUtils.extractPDFFile(MainActivity.this, uri);
                Log.d(TAG, "onActivityResult: " + value);
                runOnUiThread(() -> pb.setVisibility(View.GONE));
            }).start();
        }
    }
}