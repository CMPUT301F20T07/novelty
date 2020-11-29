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
import java.util.HashMap;
import java.util.Map;

public class ViewEditBook extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final int UPLOAD_PHOTO = 100;

    private Button uploadPhotoButton;
    private Button deletePhotoButton;
    private ImageView photoView;
    private Button cancelButton;
    private Button saveButton;
    private EditText editBookTitle;
    private EditText editAuthor;
    private EditText editHolder;
    private EditText editISBN;
    private EditText editDescription;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_book);

        uploadPhotoButton = findViewById(R.id.btn_upload);
        deletePhotoButton = findViewById(R.id.btn_deletePhoto);
        photoView = findViewById(R.id.photoView);
        cancelButton = findViewById(R.id.btn_cancel);
        saveButton = findViewById(R.id.btn_save);
        deleteButton = findViewById(R.id.btn_delete);

        editBookTitle = findViewById(R.id.editBookTitle);
        editAuthor = findViewById(R.id.editTextAuthor);
        editHolder = findViewById(R.id.editTextHolder);
        editISBN = findViewById(R.id.editTextISBN);
        editDescription = findViewById(R.id.editTextTextMultiLine);

        final Spinner spinner = findViewById(R.id.status_spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        BookBean book = (BookBean) getIntent().getSerializableExtra("book");

        if (book.getTitle() != null) {
            editBookTitle.setText(book.getTitle());
        }

        if (book.getAuthor() != null) {
            editAuthor.setText(book.getAuthor());
        }

        if (book.getDescription() != null) {
            editDescription.setText(book.getDescription());
        }

        if (book.getHolder() != null) {
            editHolder.setText(book.getHolder());
        }

        if (book.getISBN() != null) {
            editISBN.setText(book.getISBN());
        }


        if (book.getStatus() != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (book.getStatus().equals(adapter.getItem(i).toString())) {
                    spinner.setSelection(i);
                    break;
                } else {
                    spinner.setSelection(0);
                }
            }
        } else {
            spinner.setSelection(0);
        }


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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(4, data);
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

                String ISBN = editISBN.getText().toString();
                String author = editAuthor.getText().toString();
                String title = editBookTitle.getText().toString();
                String holder = editHolder.getText().toString();
                String description = editDescription.getText().toString();

                if (ISBN.length() > 0 && author.length() > 0 && title.length() > 0) {
                    Map<String, Object> bookMap = new HashMap<>();
                    bookMap.put("ISBN", ISBN);
                    bookMap.put("Title", title);
                    bookMap.put("Author", author);
                    bookMap.put("Holder", holder);
                    bookMap.put("Description", description);

                    Intent data = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("Title", title);
                    bundle.putString("Author", author);
                    bundle.putString("ISBN", ISBN);
                    bundle.putString("Description", description);
                    bundle.putString("Holder", holder);
                    bundle.putString("Status", spinner.getSelectedItem().toString());

                    data.putExtra("bundle", bundle);
                    setResult(2,data);

                    finish();
                }

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

