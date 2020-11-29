package com.example.novelty.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelty.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final int UPLOAD_PHOTO = 100;

    Bitmap bitmap = null;
    Uri imageUri = null;

    Map<String, Object> book = new HashMap<>();

    private Button uploadPhotoButton;
    private Button deletePhotoButton;
    private ImageView photoView;
    private Button cancelButton;
    private Button saveButton;
    private Button deleteButton;
    private TextView status;

    private EditText editBookTitle;
    private EditText editAuthor;
    private EditText editHolder;
    private EditText editISBN;
    private EditText editDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        uploadPhotoButton = findViewById(R.id.btn_upload);
        deletePhotoButton = findViewById(R.id.btn_deletePhoto);
        photoView = findViewById(R.id.photoView);
        cancelButton = findViewById(R.id.btn_cancel);
        saveButton = findViewById(R.id.btn_save);
        final Spinner spinner = findViewById(R.id.status_spinner1);

        deleteButton = findViewById(R.id.btn_delete);

        editBookTitle = findViewById(R.id.editBookTitle);
        editAuthor = findViewById(R.id.editTextAuthor);
        editHolder = findViewById(R.id.editTextHolder);
        editISBN = findViewById(R.id.editTextISBN);
        editDescription = findViewById(R.id.editTextTextMultiLine);


        photoView.setBackgroundColor(Color.LTGRAY);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadIntent = new Intent(AddBook.this, UploadPhoto.class);
                startActivityForResult(uploadIntent, UPLOAD_PHOTO);
            }
        });

        deletePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoView.setImageResource(0);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                book.put("ISBN", editISBN.getText().toString());
                book.put("book_name", editBookTitle.getText().toString());
                book.put("holder", editHolder.getText().toString());
                book.put("author", editAuthor.getText().toString());
                book.put("description", editDescription.getText().toString());


                Database.getBookInfo(editISBN.getText().toString()).set(book);
                 */

                book.put("ISBN", editISBN.getText().toString());
                book.put("Title", editBookTitle.getText().toString());
                book.put("Holder", editHolder.getText().toString());
                book.put("Author", editAuthor.getText().toString());
                book.put("Description", editDescription.getText().toString());


                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("Title", editBookTitle.getText().toString());
                bundle.putString("Author", editAuthor.getText().toString());
                bundle.putString("ISBN", editISBN.getText().toString());
                bundle.putString("Description", editDescription.getText().toString());
                bundle.putString("Holder", editHolder.getText().toString());
                bundle.putString("Status", spinner.getSelectedItem().toString());

                data.putExtra("bundle", bundle);
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
                    imageUri = Uri.parse(data.getStringExtra("returnPhoto"));
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
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

