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
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewEditBook extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final int UPLOAD_PHOTO = 100;

    FirebaseStorage storage;

    Bitmap bitmap = null;
    Uri imageUri = null;

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

    private FirebaseAuth fAuth;
    private String userID;

    private String ISBN;
    private String status;
    private String imageID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_book);

        fAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

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

        status = book.getStatus();
        ISBN = book.getISBN();

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

        imageID = book.getImageID();
        if (imageID.length() > 0) {
            StorageReference imageRef = storage.getReference().child(imageID);
            imageRef.getBytes(1024*1024)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            photoView.setImageBitmap(bitmap);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Sample", "onFailure", e.getCause());
                        }
                    });
        }


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
                bitmap = null;
                photoView.setImageResource(0);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case "Available":
                        Database.userAvailRef(userID).document(ISBN).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Sample", "Data has been deleted successfully!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Sample", "Data could not be deleted!" + e.toString());
                                    }
                                });
                        break;

                    case "Requested":
                        Database.userRequestRef(userID).document(ISBN).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Sample", "Data has been deleted successfully!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Sample", "Data could not be deleted!" + e.toString());
                                    }
                                });
                        break;

                    case "Accepted":
                        Database.userAcceptedRef(userID).document(ISBN).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Sample", "Data has been deleted successfully!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Sample", "Data could not be deleted!" + e.toString());
                                    }
                                });
                        break;

                    case "Borrowed":
                        Database.userBorrowedRef(userID).document(ISBN).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Sample", "Data has been deleted successfully!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Sample", "Data could not be deleted!" + e.toString());
                                    }
                                });
                        break;

                    default:
                        break;
                }


                if (imageID.length() > 0) {
                    StorageReference imageRef = storage.getReference().child(imageID);
                    imageRef.delete();
                }

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

                switch (status) {
                    case "Available":
                        Database.userAvailRef(userID).document(ISBN).delete();
                        break;

                    case "Requested":
                        Database.userRequestRef(userID).document(ISBN).delete();
                        break;

                    case "Accepted":
                        Database.userAcceptedRef(userID).document(ISBN).delete();
                        break;

                    case "Borrowed":
                        Database.userBorrowedRef(userID).document(ISBN).delete();
                        break;

                    default:
                        break;
                }


                if (imageID.length() > 0) {
                    StorageReference imageRef = storage.getReference().child(imageID);
                    imageRef.delete();
                }


                String ISBN = editISBN.getText().toString();
                // make ISBN required
                if (TextUtils.isEmpty(ISBN)){
                    //display error message
                    editISBN.setError("ISBN is required");
                    return;
                }

                String author = editAuthor.getText().toString();
                // make author required
                if (TextUtils.isEmpty(author)){
                    //display error message
                    editAuthor.setError("author is required");
                    return;
                }

                String title = editBookTitle.getText().toString();
                // make title required
                if (TextUtils.isEmpty(title)){
                    //display error message
                    editBookTitle.setError("author is required");
                    return;
                }

                String holder = editHolder.getText().toString();
                String description = editDescription.getText().toString();
                String status = spinner.getSelectedItem().toString();

                String imageID = "";
                if (bitmap != null) {
                    imageID = handleUpload(bitmap);
                }


                if (ISBN.length() > 0 && author.length() > 0 && title.length() > 0) {
                    Map<String, Object> bookMap = new HashMap<>();
                    bookMap.put("ISBN", ISBN);
                    bookMap.put("Title", title);
                    bookMap.put("Author", author);
                    bookMap.put("Holder", holder);
                    bookMap.put("Description", description);
                    bookMap.put("Status", status);
                    bookMap.put("imageID", imageID);

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
                    Uri imageUri = Uri.parse(data.getStringExtra("returnPhoto"));
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


    private String handleUpload(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);

        String bookID = userID + "-" + editISBN.getText().toString();
        String imgID = bookID + ".jpeg";
        StorageReference reference = storage.getReference()
                .child(imgID);

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ViewEditBook.this,"Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Sample", "onFailure", e.getCause());
                    }
                });

        return imgID;
    }

}

