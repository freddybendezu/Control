package com.smartbrands.demo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Inicio extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inicio);
    }


    public void lanzar(View v){
    	Bundle bundle = new Bundle();
    	Intent intent = new Intent();
    	intent.setClass(Inicio.this, NuevoInventario.class);
    	intent.putExtras(bundle);
    	startActivity(intent);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }
    
}
