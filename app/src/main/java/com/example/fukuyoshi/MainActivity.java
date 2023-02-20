package com.example.fukuyoshi;

import static com.example.fukuyoshi.constant.Constant.CONTACTS_LOADER_ID;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fukuyoshi.adapter.CountryAdapter;
import com.example.fukuyoshi.helper.ContactHelper;
import com.example.fukuyoshi.helper.PermissionHelper;
import com.example.fukuyoshi.model.Nationalize;
import com.example.fukuyoshi.service.controller.NationalizeController;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {
    private EditText etName;
    private Button btnPredict;
    private ListView lvResult;
    private ImageView ivContacts;
    private ContactHelper contactHelper;
    private PermissionHelper permissionHelper;
    private List<String> contacts = new ArrayList<>();

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    getContactLoader();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_contact_list), Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        btnPredict = findViewById(R.id.btn_predict);
        lvResult = findViewById(R.id.lv_result);
        ivContacts = findViewById(R.id.iv_contacts);

        permissionHelper = new PermissionHelper();
        contactHelper = new ContactHelper();

        ivContacts.setOnClickListener(view -> {
            permissionHelper.checkPermissionRequest(this, requestPermissionLauncher, new PermissionHelper.PermissionHelperInterface() {
                @Override
                public void onGranted() {
                    getContactLoader();
                }
            });
        });
        btnPredict.setOnClickListener(view -> {

            lvResult.setAdapter(null);

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);

            NationalizeController controller = new NationalizeController();
            controller.predictName(this, etName.getText().toString().trim(), new Nationalize.NationalizeInterface() {
                @Override
                public void onSuccess(Nationalize item) {
                    CountryAdapter adapter = new CountryAdapter(getApplicationContext(), item.getCountryList());
                    runOnUiThread(() -> lvResult.setAdapter(adapter));
                }
                @Override
                public void onError(String message) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show());
                }
            });
        });
    }

    private void showContactList(String[] contacts) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(contacts, (dialog, pos) -> etName.setText(contacts[pos]));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getContactLoader() {
        getLoaderManager().initLoader(CONTACTS_LOADER_ID,
                null,
                this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == CONTACTS_LOADER_ID) {
            return contactHelper.contactsLoader(this);
        }
        return null;

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        //The framework will take care of closing the
        // old cursor once we return.
        contacts = contactHelper.contactsFromCursor(data);
        showContactList(contacts.toArray(new String[0]));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
    }
}