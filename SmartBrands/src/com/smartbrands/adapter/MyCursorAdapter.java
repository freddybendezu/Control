package com.smartbrands.adapter;

import com.smartbrands.demo.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {
	String TAG = "MainActivity";
	private LayoutInflater inflater;
	

	public MyCursorAdapter(Context context, Cursor c) {
		super(context, c);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.layout_listview_producto, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView codigo = (TextView) view.findViewById(R.id.lblCodigoo);
		TextView producto = (TextView) view.findViewById(R.id.lblProductoo);
		TextView cantidad = (TextView) view.findViewById(R.id.lblCantidadd);
		
		codigo.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0))));
		producto.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
		cantidad.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));

	}
	
}

