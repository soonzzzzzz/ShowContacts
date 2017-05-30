package com.homework.showcontacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (TextView) findViewById(R.id.output);
    }

    public void onClick(View target) {
        String phoneNumber = null;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        ContentResolver cr = getContentResolver();

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    message.append("\nNAME:" + name);
                    Cursor pCur = cr.query(
                            ContactsContract.Data.CONTENT_URI,
                            null, ContactsContract.Data.CONTACT_ID
                                    + "=?"
                                    + " AND "
                                    + ContactsContract.Data.MIMETYPE
                                    + "='"
                                    + ContactsContract.CommonDataKinds.Phone
                                         .CONTENT_ITEM_TYPE
                                    + "'", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phoneNumber = pCur.getString(pCur
                                .getColumnIndex(NUMBER));
                        message.append("\nPHONE NUMBER:" + phoneNumber);
                    }
                    pCur.close();
                }
                message.append("\n\n");
            }
        }

    }
}
