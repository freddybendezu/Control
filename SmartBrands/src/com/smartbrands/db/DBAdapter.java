package com.smartbrands.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	String TAG = "DBAdapter";

	public static final String ALM_TABLE = "alm_table";
	public static final String ALM_ID_ALMACEN = "id_almacen";// 0 integer
	public static final String ALM_NOM_ALMACEN = "nom_almacen";// 1 text(String)
	public static final String ALM_ID_SUCURSAL = "id_sucursal";// 2 integer
	
	public static final String UBI_TABLE = "ubi_table";
	public static final String UBI_ID_UBICACION = "id_ubicacion";// 0 integer
	public static final String UBI_NOM_UBICACION = "nom_ubicacion";// 1 text(String)

	public static final String PRO_TABLE = "pro_table";
	public static final String PRO_ID_PRODUCTO = "id_producto";// 0 integer
	public static final String PRO_NOM_PRODUCTO = "nom_producto";// 1 text(String)

	
	private SQLiteDatabase db;
	private DBHelper dbHelper;

	public DBAdapter(Context context) {
		dbHelper = new DBHelper(context);
	}

	
	public void open() {
		if (null == db || !db.isOpen()) {
			try {
				db = dbHelper.getWritableDatabase();
			} catch (SQLiteException sqLiteException) {
				Log.e(TAG, "Error en BD");
			}
		}
	}


	public void close() {
		if (db != null) {
			db.close();
		}
	}


	public int insert(String table, ContentValues values) {
		Log.e(TAG, "Se inserta valores " + values);
		return (int) db.insert(table, null, values);
	}


	public Cursor getAllRow(String table) {
		return db.query(table, null, null, null, null, null, ALM_ID_ALMACEN);
	}
	
	public List<String> getAllSimple(String table, String campo) {
        List<String> almacenList = new ArrayList<String>() ;
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        Integer i=0;
        if (cursor.moveToFirst()) {
            do {
            	almacenList.add(i,cursor.getString(cursor.getColumnIndex(campo)));
                i+=1;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return almacenList;
    }

	private class DBHelper extends SQLiteOpenHelper {
		private static final int VERSION = 1;
		private static final String DB_NAME = "stu_db.db";

		public DBHelper(Context context) {
			super(context, DB_NAME, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String crear_table_alm_table = "CREATE TABLE IF NOT EXISTS " + ALM_TABLE + "("
					+ ALM_ID_ALMACEN + " TEXT PRIMARY KEY," + ALM_NOM_ALMACEN
					+ " TEXT NOT NULL," + ALM_ID_SUCURSAL + " TEXT NOT NULL"
					+ ")";

			String crear_table_ubi_table = "CREATE TABLE IF NOT EXISTS " + UBI_TABLE + "("
					+ UBI_ID_UBICACION + " TEXT PRIMARY KEY," + UBI_NOM_UBICACION
					+ " TEXT NOT NULL"
					+ ")";
			
			String crear_table_pro_table = "CREATE TABLE IF NOT EXISTS " + PRO_TABLE + "("
					+ PRO_ID_PRODUCTO + " TEXT PRIMARY KEY," + PRO_NOM_PRODUCTO
					+ " TEXT NOT NULL"
					+ ")";
			
			db.execSQL(crear_table_alm_table);
			Log.d(TAG, "Se creo la tabla " + ALM_TABLE);
			db.execSQL(crear_table_ubi_table);
			Log.d(TAG, "Se creo la tabla " + UBI_TABLE);
			db.execSQL(crear_table_pro_table);
			Log.d(TAG, "Se creo la tabla " + PRO_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + ALM_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + UBI_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + PRO_TABLE);
		}
	}
}
