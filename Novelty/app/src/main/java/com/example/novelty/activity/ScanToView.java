package com.example.novelty.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novelty.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanToView extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

    }

    @Override
    public void handleResult(Result result) {
       // MainActivity.resultText.setText(result.getText());

//        String ISBN = editISBN.getText().toString()
//        Intent data = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("ISBN",ISBN.getText().toString());
//
//        data.putExtra("bundle", bundle);
//        setResult(2,data);
//        finish();
//    }
        onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
