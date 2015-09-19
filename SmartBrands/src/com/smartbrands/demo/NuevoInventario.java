package com.smartbrands.demo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NuevoInventario extends Activity {

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

}
