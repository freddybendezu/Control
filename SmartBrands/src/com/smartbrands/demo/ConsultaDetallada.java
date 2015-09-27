package com.smartbrands.demo;

import com.smartbrands.adapter.MyCursorAdapter;
import com.smartbrands.db.DBAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ConsultaDetallada extends Activity {
	
	String TAG = "ConsultaDetallada";
	private ListView lstConsulta;
	private Handler handler;
	private Button btnBuscar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_consulta_detallada);
		
		lstConsulta = (ListView) findViewById(R.id.lstConsulta);		
		btnBuscar = (Button) findViewById(R.id.btnBuscar);
		
        new Handler().post(new Runnable() {
            @Override
            public void run() {
            	displayData();
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.consulta_detallada, menu);
		return true;
	}
	
	private void displayData() {
		DBAdapter dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		Cursor allDataCursor = dbAdapter.getAllRow(DBAdapter.PRO_TABLE);
		MyCursorAdapter adapter = new MyCursorAdapter(this, allDataCursor);
		lstConsulta.setAdapter(adapter);
	}

}
