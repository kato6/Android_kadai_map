package jp.android.myapp.googlemapsapplication01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Map;


public class AddressListActivity extends Activity {

    private SQLiteDatabase db;
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_address_list, menu);
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
    public void onStart() {
        super.onStart();
        String regData[];
        regData=new String[5];
        Intent intent = getIntent();



        if(intent!=null) {

            regData[0]=intent.getStringExtra("address");
            regData[1]=intent.getStringExtra("kategori");
            regData[2]=intent.getStringExtra("name");
            regData[3]=intent.getStringExtra("date");
            regData[4]=intent.getStringExtra("tell");


         AlertDialog.Builder ad = new AlertDialog.Builder(AddressListActivity.this);
            ad.setTitle("IntentMessage");
            ad.setMessage(intent.getStringExtra("address")+"\n"+
                    intent.getStringExtra("kategori")+"\n"+
                    intent.getStringExtra("name")+"\n"+
                    intent.getStringExtra("date")+"\n"+
                    intent.getStringExtra("tell"));


            ad.create().show();

        }

        myListView = (ListView)findViewById(R.id.listView);

        MyDbHelper dbHelper = new MyDbHelper(this);
        db=dbHelper.getWritableDatabase();

        try{

            insertToDB(regData);
            Cursor c = serchToDB();
            String[] from = {
                    MyDbHelper.ADDRESS,

            };

            int[] to = {
                    R.id.address,

            };

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.listitem,c,from,to,0);


            myListView.setAdapter(adapter);

            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){


                    String s1 = ((TextView) view.findViewById(R.id.address)).getText().toString();


                    Intent intent = new Intent(AddressListActivity.this,MapsActivity.class);
                    intent.putExtra("address",s1);
                    startActivity(intent);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }

        Button mainBtn = (Button)findViewById(R.id.button_touroku);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(AddressListActivity.this, RegisterActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    private void insertToDB(String[] regData) throws Exception{
        if(regData[0]!=null) {
            db.execSQL("insert into myData("
                            + MyDbHelper.ADDRESS+
                            ")values('"
                            + regData[0] +
                            "')"
            );
        }
    }

    private Cursor serchToDB() throws Exception {
        Cursor c = db.rawQuery("select * from " + MyDbHelper.TABLE_NAME,null);
        return c;
    }
}