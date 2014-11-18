package com.example.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by v.krupskyi on 10.11.2014.
 */
public class PurchaseEditor extends Activity  implements View.OnClickListener {
    private EditText editText;
    private Button saveButton;
    private Intent intent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_editor);

        editText = (EditText) findViewById(R.id.editText_editor_text);
        saveButton = (Button) findViewById(R.id.button_editor_save);
        saveButton.setOnClickListener(this);
        intent = getIntent();
        String stringExtra = intent.getStringExtra(getString(R.string.extra_message_text));
        editText.setText(stringExtra);
    }

    @Override
    public void onClick(View v) {
        intent.putExtra(getString(R.string.extra_message_text) , editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}