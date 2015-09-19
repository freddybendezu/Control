package com.smartbrands.demo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActualizarTablasMaestras extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_actualizar_tablas_maestras);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actualizar_tablas_maestras, menu);
		return true;
	}

}
