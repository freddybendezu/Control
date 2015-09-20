package com.smartbrands.demo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class NuevoInventario extends Activity {

	private Button scanBtn;
	private TextView formatTxt, contentTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_nuevo_inventario);
		
		// Se Instancia el bot—n de Scan
		
		// Se Instancia el Campo de Texto para el nombre del formato de c—digo
		// de barra
		formatTxt = (TextView) findViewById(R.id.txtUltimoRegistro);
		// Se Instancia el Campo de Texto para el contenido del c—digo de barra
		contentTxt = (TextView) findViewById(R.id.txtTotal);
		// Se agrega la clase MainActivity.java como Listener del evento click
		// del bot—n de Scan
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_inventario, menu);
		return true;
	}
	
	public void agregar(View v) {
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
			// Quiere decir que se obtuvo resultado pro lo tanto:
			// Desplegamos en pantalla el contenido del c—digo de barra scaneado
			String scanContent = scanningResult.getContents();
			contentTxt.setText("Contenido: " + scanContent);
			// Desplegamos en pantalla el nombre del formato del c—digo de barra
			// scaneado
			String scanFormat = scanningResult.getFormatName();
			formatTxt.setText("Formato: " + scanFormat);
		} else {
			// Quiere decir que NO se obtuvo resultado
			Toast toast = Toast.makeText(getApplicationContext(),
					"No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
