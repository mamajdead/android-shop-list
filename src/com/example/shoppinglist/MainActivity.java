package com.example.shoppinglist;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String EXTRA_MESSAGE = "MSG";
    private static final String TAG = "MainActivity";
    private static final List<String> values = new ArrayList<String>();
    private ShopListArrayAdapter<String> adapter ;

    private final class ShopListArrayAdapter<T> extends ArrayAdapter{

        private TextView textView;
        /**
         * Constructor
         *
         * @param context            The current context.
         * @param resource           The resource ID for a layout file containing a layout to use when
         *                           instantiating views.
         * @param textViewResourceId The id of the TextView within the layout resource to be populated
         * @param objects            The objects to represent in the ListView.
         */
        public ShopListArrayAdapter(Context context, int resource, int textViewResourceId, List objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if(view != null) {

                textView = (TextView) view.findViewById(R.id.label_purchase_name);
                Button button = (Button) view.findViewById(R.id.button_purchase_edit);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), PurchaseEditor.class);
                        intent.putExtra(getString(R.string.extra_message_text), textView.getText());
                        intent.putExtra(getString(R.string.extra_message_old_text), textView.getText());
                        intent.putExtra(getString(R.string.extra_message_new_purchase), false);
                        startActivityForResult(intent, 1);
                    }
                });
            }
            return view;
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d(TAG, "onCreate");
        Log.d(TAG, "values.size = " + Integer.toString(values.size()));

        adapter = new ShopListArrayAdapter<String>(this, R.layout.purchase_item, R.id.label_purchase_name, values);
        ListView goodsList = (ListView) findViewById(R.id.listView_purchase_list);
        goodsList.setAdapter(adapter);

        Button button = (Button)findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PurchaseEditor.class);
                intent.putExtra(getString(R.string.extra_message_text), "Test");
                intent.putExtra(getString(R.string.extra_message_new_purchase), true);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"onActivityResult");
        if (data == null) {
            return;
        }
        boolean isNew = data.getBooleanExtra(getString(R.string.extra_message_new_purchase), true);
        Log.d(TAG,"isNew = " + isNew);
        String purchase_name = data.getStringExtra(getString(R.string.extra_message_text));


        if(isNew) {
            values.add(purchase_name);
        }else{
            String old_purchase_name = data.getStringExtra(getString(R.string.extra_message_old_text));
            for (String value : values) {
                if (value.equals(old_purchase_name)) {
                    value.copyValueOf(purchase_name.toCharArray());
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

}
