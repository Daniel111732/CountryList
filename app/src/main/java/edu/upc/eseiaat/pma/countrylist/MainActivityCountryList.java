package edu.upc.eseiaat.pma.countrylist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivityCountryList extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> country_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_country_list);

        // Agafem l'array de paisos de l'arxiu xml i el fiquem a l'objecte ArrayList
        String[] countries = getResources().getStringArray(R.array.countries);
        country_list = new ArrayList<>(Arrays.asList(countries));

        //Associem a l'objecte listview el listview del layout
        ListView list = (ListView) findViewById(R.id.country_list);

        //Tots els listView tenen un Adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,country_list);
        //Associem el adapter al ListView
        list.setAdapter(adapter);

        //Posem un listener al List per un click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {

                String message_select = getResources().getString(R.string.message_select);
                Toast.makeText(MainActivityCountryList.this,
                        message_select+ " " +country_list.get(pos),
                        Toast.LENGTH_SHORT)
                        .show();

            }
        });

        //Posem listener al List per un long-click
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, final int pos, long id) {

                String msg = getResources().getString(R.string.confirm_message);
                final String message_delete = getResources().getString(R.string.message_delete);

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivityCountryList.this);
                builder.setTitle(R.string.confirmation);
                builder.setMessage(msg +" "+ country_list.get(pos) + " ?");

                builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivityCountryList.this,
                                message_delete + " " + country_list.get(pos),
                                Toast.LENGTH_SHORT)
                                .show();
                        country_list.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel,null);
                builder.create().show();
                return true;

            }
        });

        //Creem els objectes boto i text i els hi associem els elements del layout
        Button boto = (Button) findViewById(R.id.button);
        final EditText text = (EditText) findViewById(R.id.editText);

        //Posem listener al botó
        //TODO: Afegeix els nous paisos en ordre alfabètic
        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String new_country = text.getText().toString();
                String message_add = getResources().getString(R.string.message_add);
                String message_blank = getResources().getString(R.string.message_blank);

                if(new_country.length()>0) {
                    country_list.add(0,new_country);
                    adapter.notifyDataSetChanged();
                    text.setText(null);

                    Toast.makeText(MainActivityCountryList.this,
                            message_add + " " + new_country,Toast.LENGTH_LONG).show();

                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(text.getWindowToken(), 0);
                }
                else{
                    Toast.makeText(MainActivityCountryList.this,
                            message_blank,Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
