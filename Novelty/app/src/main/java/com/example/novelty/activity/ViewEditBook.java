package com.example.novelty.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.novelty.R;
import com.example.novelty.bean.BookBean;

import java.io.IOException;

public class ViewEditBook extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final int UPLOAD_PHOTO = 100;

    private Button uploadPhotoButton;
    private Button deletePhotoButton;
    private ImageView photoView;
    private Button cancelButton;
    private Button saveButton;
    private EditText editBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_book);

        uploadPhotoButton = findViewById(R.id.btn_upload);
        deletePhotoButton = findViewById(R.id.btn_deletePhoto);
        photoView = findViewById(R.id.photoView);
        cancelButton = findViewById(R.id.btn_cancel);
        saveButton = findViewById(R.id.btn_save);
        editBookTitle = findViewById(R.id.editBookTitle);

        Spinner spinner = findViewById(R.id.status_spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        BookBean book = (BookBean) getIntent().getSerializableExtra("book");
        editBookTitle.setText(book.getTitle());

        photoView.setBackgroundColor(Color.LTGRAY);

        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadIntent = new Intent(ViewEditBook.this, UploadPhoto.class);
                startActivityForResult(uploadIntent, UPLOAD_PHOTO);
            }
        });

        deletePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoView.setImageResource(0);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(1, data);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(2,data);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case UPLOAD_PHOTO:
                if (resultCode == 2) {
                    Uri imageUri = Uri.parse(data.getStringExtra("returnPhoto"));
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        photoView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String statusNow = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), statusNow, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

