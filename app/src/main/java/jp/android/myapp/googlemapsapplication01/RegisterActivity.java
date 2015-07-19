package jp.android.myapp.googlemapsapplication01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart(){
        super.onStart();

        Button regBtn = (Button)findViewById(R.id.addr_button);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etaddress    = (EditText) findViewById(R.id.addr_edit);
                String address    = etaddress.getText().toString();

                EditText etkategori = (EditText)findViewById(R.id.kategori_edit);
                String  kategori = etkategori.getText().toString();

                EditText etname = (EditText)findViewById(R.id.name_edit);
                String  name = etname.getText().toString();

                EditText etdate = (EditText)findViewById(R.id.date_edit);
                String date = etdate.getText().toString();

                EditText ettell = (EditText)findViewById(R.id.tell_edit);
                String tell = ettell.getText().toString();



                Intent regIntent = new Intent(RegisterActivity.this,AddressListActivity.class);
                regIntent.putExtra("address",address);
                regIntent.putExtra("kategori",kategori);
                regIntent.putExtra("name",name);
                regIntent.putExtra("date",date);
                regIntent.putExtra("tell",tell);


                startActivity(regIntent);
            }
        });
    }
}
