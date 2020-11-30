package com.example.novelty.activity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Database {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * gets reference for the specific  user
     *
     * @param userID unique userID for user from firebase auth.
     * @return document reference for the user
     */
    public static DocumentReference getUserRef(String userID) {
        return Database.db.collection("users")
                .document(userID);
    }

    /**
     * gets reference for the "user" Collection
     *
     * @return document reference for the user
     */
    public static CollectionReference getusers() {
        return Database.db.collection("users");
    }


    /**
     * gets a specific book's reference
     *
     * @param collec collection reference for the books
     * @param ISBN   unique ISBN of the book to access document
     * @return document reference for the specified book
     */
    public static DocumentReference getBookRef(CollectionReference collec, String ISBN) {
        return collec.document(ISBN);
    }

    /**
     * available books
     *
     * @param userID string unique userID from firebase auth.
     * @return collection reference to the "available"
     */
    public static CollectionReference userAvailRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("available");
    }

    /**
     * borrowed books
     *
     * @param userID string unique userID from firebase auth.
     * @return collection reference to the "borrowed"
     */
    public static CollectionReference userBorrowedRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("borrowed");
    }



    /**
     * book requests
     *
     * @param userID string unique userID from firebase auth.
     * @return collection reference to the "my requets"
     */
    public static CollectionReference userRequestRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("requested");
    }

    /**
     * accepted books
     *
     * @param userID string unique userID from firebase auth.
     * @return collection reference to the "accepted requests"
     */
    public static CollectionReference userAcceptedRef(String userID) {
        return Database.db.collection("users")
                .document(userID).collection("accepted");
    }
}
