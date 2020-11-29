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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final int UPLOAD_PHOTO = 100;

    Bitmap bitmap = null;
    Uri imageUri = null;


    private Button uploadPhotoButton;
    private Button deletePhotoButton;
    private ImageView photoView;
    private Button cancelButton;
    private Button saveButton;
    private Button deleteButton;

    private EditText editBookTitle;
    private EditText editAuthor;
    private EditText editHolder;
    private EditText editISBN;
    private EditText editDescription;

    private FirebaseAuth fAuth;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        fAuth = FirebaseAuth.getInstance();

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


        userID = fAuth.getCurrentUser().getUid();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ISBN = editISBN.getText().toString();
                String author = editAuthor.getText().toString();
                String title = editBookTitle.getText().toString();
                String holder = editHolder.getText().toString();
                String description = editDescription.getText().toString();
                String status = spinner.getSelectedItem().toString();

                if (ISBN.length() > 0 && author.length() > 0 && title.length() > 0) {
                    Map<String, Object> bookMap = new HashMap<>();
                    bookMap.put("ISBN", ISBN);
                    bookMap.put("Title", title);
                    bookMap.put("Author", author);
                    bookMap.put("Holder", holder);
                    bookMap.put("Description", description);
                    bookMap.put("Status", status);

                    if (status.length() > 0) {
                        switch (status) {
                            case "Available":
                                Database.userAvailRef(userID).document(ISBN).set(bookMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Sample", "Data has been added successfully!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Sample", "Data could not be added!" + e.toString());
                                            }
                                        });
                                break;

                            case "Requested":
                                Database.userRequestRef(userID).document(ISBN).set(bookMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Sample", "Data has been added successfully!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Sample", "Data could not be added!" + e.toString());
                                            }
                                        });
                                break;

                            case "Accepted":
                                Database.userAcceptedRef(userID).document(ISBN).set(bookMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Sample", "Data has been added successfully!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Sample", "Data could not be added!" + e.toString());
                                            }
                                        });
                                break;

                            case "Borrowed":
                                Database.userBorrowedRef(userID).document(ISBN).set(bookMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Sample", "Data has been added successfully!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Sample", "Data could not be added!" + e.toString());
                                            }
                                        });
                                break;

                            default:
                                break;
                        }
                    }

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

