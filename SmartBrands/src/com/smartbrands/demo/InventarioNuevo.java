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

public class InventarioNuevo extends Activity {
	String TAG = "InventarioNuevo";
	private Handler handler;
	
	private TextView formatTxt, contentTxt, cantidad;
	Spinner cboAlmacen; 
	int x = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_inventario_nuevo);
		
		cboAlmacen = (Spinner) findViewById(R.id.cboAlmacen);
		
		
		formatTxt = (TextView) findViewById(R.id.txtProducto);
		// Se Instancia el Campo de Texto para el contenido del c—digo de barra
		contentTxt = (TextView) findViewById(R.id.txtUltimoRegistro);
		// Se agrega la clase MainActivity.java como Listener del evento click
		// del bot—n de Scan
		cantidad = (TextView) findViewById(R.id.txtCantidad);
		
		importExcel2Sqlite();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventario_nuevo, menu);
		return true;
	}
	
	public void agregar(View v) {
		// Se responde al evento click
		// Se instancia un objeto de la clase IntentIntegrator
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			// Se procede con el proceso de scaneo
			scanIntegrator.initiateScan();
		
	}
	public void seguirAgregando() {
		// Se responde al evento click
		// Se instancia un objeto de la clase IntentIntegrator
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			// Se procede con el proceso de scaneo
			scanIntegrator.initiateScan();		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// Se obtiene el resultado del proceso de scaneo y se parsea
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);		
		if (scanningResult != null) {
			
			String scanContent = scanningResult.getContents();
			String producto = contentTxt.getText().toString().trim();
			String scanFormat = scanningResult.getFormatName();
			// Quiere decir que se obtuvo resultado pro lo tanto:
			// Desplegamos en pantalla el contenido del c—digo de barra scaneado						
			if (producto.equalsIgnoreCase(scanContent)){
				x += 1;
				cantidad.setText(x+"");
				seguirAgregando();
			}
			contentTxt.setText(scanContent);
			formatTxt.setText(scanFormat);
			// Desplegamos en pantalla el nombre del formato del c—digo de barra
			// scaneado

		
		} else {
			// Quiere decir que NO se obtuvo resultado
			Toast toast = Toast.makeText(getApplicationContext(),
					"No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
			toast.show();
		}
		//scanningResult = null;
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
		if (sheet1 == null) {
			return;
		}
		dbAdapter.open();
		//
		Excel2SQLiteHelper.insertExcelToSqlite(dbAdapter, sheet1);
		//Excel2SQLiteHelper.insertExcelToSqlite(dbAdapter, sheet2);
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
        List<String> lista = dbAdapter.getAllSimple(DBAdapter.ALM_TABLE);
          
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lista);  
     
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
     
        cboAlmacen.setAdapter(dataAdapter);  
    } 

}
