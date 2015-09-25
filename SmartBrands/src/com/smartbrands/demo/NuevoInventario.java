package com.smartbrands.demo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.smartbrands.adapter.MyCursorAdapter;
import com.smartbrands.db.DBAdapter;
import com.smartbrands.db.Excel2SQLiteHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.os.Handler;

public class NuevoInventario extends Activity {
	String TAG = "NuevoInventario";
	private Handler handler;
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_nuevo_inventario);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_inventario, menu);
		return true;
	}
	

	private void importExcel2Sqlite() {
		AssetManager am = this.getAssets();
		InputStream inStream;
		Workbook wb = null;
		try {
			//
			inStream = am.open("students.xls");
			// HSSF
			wb = new HSSFWorkbook(inStream);
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (wb == null) {
			Log.e(TAG, "No se pudo leer excel");
			return;
		}
		// 
		DBAdapter dbAdapter = new DBAdapter(this);
		Sheet sheet1 = wb.getSheetAt(0);
		Sheet sheet2 = wb.getSheetAt(1);
		if (sheet1 == null) {
			return;
		}
		dbAdapter.open();
		//
		Excel2SQLiteHelper.insertExcelToSqlite(dbAdapter, sheet1);
		Excel2SQLiteHelper.insertExcelToSqlite(dbAdapter, sheet2);
		dbAdapter.close();
		Log.e(TAG, "Se logro cerrar la BD");
		handler.post(new Runnable() {
			public void run() {
				displayData();
			}
		});
	}

	private void displayData() {
		DBAdapter dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		Cursor allDataCursor = dbAdapter.getAllRow(DBAdapter.ALM_TABLE);
		MyCursorAdapter adapter = new MyCursorAdapter(this, allDataCursor);
		listView.setAdapter(adapter);
	}


}
