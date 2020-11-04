package com.example.novelty;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Database {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReference();

    public static DocumentReference getUserProfileRef(String userID) {
        return Database.db.collection(userID)
                .document("userProfile");
    }

    public static DocumentReference getUserBooksRef(String userID) {
        return Database.db.collection(userID)
                .document("books");
    }
}
