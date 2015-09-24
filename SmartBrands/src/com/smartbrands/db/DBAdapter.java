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
		Log.e(TAG, "Se inserta valores" + values);
		return (int) db.insert(table, null, values);
	}


	public Cursor getAllRow(String table) {
		return db.query(table, null, null, null, null, null, ALM_ID_ALMACEN);
	}
	
	public List<String> getAllSimple(String table) {
        List<String> almacenList = new ArrayList<String>() ;
        Cursor cursor = db.query(table, null, null, null, null, null, ALM_ID_ALMACEN);
        Integer i=0;
        if (cursor.moveToFirst()) {
            do {
            	almacenList.add(i,cursor.getString(cursor.getColumnIndex(ALM_NOM_ALMACEN)));
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
			String create_sql = "CREATE TABLE IF NOT EXISTS " + ALM_TABLE + "("
					+ ALM_ID_ALMACEN + " TEXT PRIMARY KEY," + ALM_NOM_ALMACEN
					+ " TEXT NOT NULL," + ALM_ID_SUCURSAL + " TEXT NOT NULL,"
					+ ")";

			db.execSQL(create_sql);
			Log.d(TAG, "Creamos la tabla" + ALM_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + ALM_TABLE);
		}
	}
}
