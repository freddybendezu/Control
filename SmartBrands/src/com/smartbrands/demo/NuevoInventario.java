package com.smartbrands.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.smartbrands.db.DBAdapter;
import com.smartbrands.db.Excel2SQLiteHelper;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoInventario extends Activity {
	String TAG = "NuevoInventario";
	private Handler handler;
	
	private TextView formatTxt, contentTxt, cantidad;
	Spinner cboAlmacen, cboUbicacion; 
	int x = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_nuevo_inventario);
		
		cboAlmacen = (Spinner) findViewById(R.id.cboAlmacenn);				
		cboUbicacion = (Spinner) findViewById(R.id.cboUbicacionn);
		formatTxt = (TextView) findViewById(R.id.txtProducto);
		contentTxt = (TextView) findViewById(R.id.txtUltimoRegistro);
		cantidad = (TextView) findViewById(R.id.txtCantidad);
		
		importExcel2Sqlite();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.inventario_nuevo, menu);
		return true;
	}
	
	public void agregar(View v) {
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
		
	}
	public void seguirAgregando() {
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);		
		if (scanningResult != null) {
			
			String scanContent = scanningResult.getContents();
			
			String producto = contentTxt.getText().toString().trim();
			String scanFormat = scanningResult.getFormatName();
						
			if (producto.equalsIgnoreCase(scanContent)){
				x += 1;
				cantidad.setText(x+"");
				seguirAgregando();
			}
			contentTxt.setText(scanContent);
			formatTxt.setText(scanFormat);
		
		} else {
			
			Toast toast = Toast.makeText(getApplicationContext(), "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
			toast.show();
		}

	}
	
	private void importExcel2Sqlite() {
		AssetManager am = this.getAssets();
		InputStream inStream;
		Workbook wb = null;
		try {
			//
			inStream = am.open("demo.xls");
			// HSSF
			wb = new HSSFWorkbook(inStream);
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (wb == null) {
			Log.e(TAG, "No se encontro ningun libro de excel");
			return;
		}
		
		DBAdapter dbAdapter = new DBAdapter(this);
		Sheet sheet1 = wb.getSheetAt(0);
		Sheet sheet2 = wb.getSheetAt(1);
		Sheet sheet3 = wb.getSheetAt(2);
		if (sheet1 == null) {
			return;
		}
		dbAdapter.open();
		//
		Excel2SQLiteHelper.insertExcelToSqlite1(dbAdapter, sheet1);
		Excel2SQLiteHelper.insertExcelToSqlite2(dbAdapter, sheet2);
		Excel2SQLiteHelper.insertExcelToSqlite3(dbAdapter, sheet3);
		dbAdapter.close();
		Log.e(TAG, "Se cerro la BD");
		llenarSpinner();
//		handler.post(new Runnable() {
//			public void run() {
//				llenarSpinner();
//			}
//		});
	}	
	
    private void llenarSpinner() {  
        
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        Log.e(TAG, "Se abre la BD");
        List<String> listaAlmacen = dbAdapter.getAllSimple(DBAdapter.ALM_TABLE, DBAdapter.ALM_NOM_ALMACEN);
        dbAdapter.open();
        List<String> listaUbicacion = dbAdapter.getAllSimple(DBAdapter.UBI_TABLE, DBAdapter.UBI_NOM_UBICACION);
        Log.e(TAG, "Se obtuvo las listas para los spinner");  
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaAlmacen);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaUbicacion);
     
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     
        cboAlmacen.setAdapter(dataAdapter1);  
        cboUbicacion.setAdapter(dataAdapter2);
    } 

}
