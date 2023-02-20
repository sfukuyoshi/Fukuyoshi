package com.example.fukuyoshi.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;


import java.util.ArrayList;
import java.util.List;

public class ContactHelper {
    public Loader<Cursor> contactsLoader(Context context) {
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI; // The content URI of the phone contacts

        String[] projection = {                                  // The columns to return for each row
                ContactsContract.Contacts.DISPLAY_NAME
        } ;

        String selection = null;                                 //Selection criteria
        String[] selectionArgs = {};                             //Selection criteria
        String sortOrder = null;                                 //The sort order for the returned rows

        return new CursorLoader(
                context,
                contactsUri,
                projection,
                selection,
                selectionArgs,
                sortOrder);
    }

    public List<String> contactsFromCursor(Cursor cursor) {
        List<String> contacts = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }

        return contacts;
    }
}
