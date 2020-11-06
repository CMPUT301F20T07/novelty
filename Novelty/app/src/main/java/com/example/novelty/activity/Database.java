package com.example.novelty.activity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

public class Database {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
//    public static FirebaseStorage storage = FirebaseStorage.getInstance();
//    public static StorageReference storageRef = storage.getReference();

    public static DocumentReference getUserRef(String userID) {
        return Database.db.collection("users")
                .document(userID);
    }

    public static DocumentReference getBookRef(CollectionReference collec, String ISBN) {
        return collec.document(ISBN);
    }

    public static CollectionReference userAvailRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("available");
    }

    public static CollectionReference userBorrowedRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("borrowed");
    }

    /* as an owner, the requests that I received*/
    public static CollectionReference userReceivedRequestRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("received requests");
    }

    /* as a borrower, the requests that I made*/
    public static CollectionReference userRequestRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("my requests");
    }

    /* as an owner, the requests that I accepted*/
    public static CollectionReference userAcceptedRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("accepted requests");
    }

    /* as a borrower, my requests that got accepted*/
    public static CollectionReference userRequestAcceptedRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("my accepted requests");
    }
}
