package com.j256.ormlite.android;

import java.sql.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.support.ConnectionSource;

/**
 * Our source for connections to Android databases.
 * 
 * @author kevingalligan, graywatson
 */
public class AndroidConnectionSource implements ConnectionSource {

	private DateAdapter dateAdapter;
	private SQLiteOpenHelper dbHelper;
	private SQLiteDatabase readableDb = null;
	private SQLiteDatabase readWriteDb = null;

	public AndroidConnectionSource(SQLiteOpenHelper dbHelper, DateAdapter dateAdapter) {
		this.dbHelper = dbHelper;
		this.dateAdapter = (dateAdapter == null ? new NumericDateAdapter() : dateAdapter);
	}

	public DateAdapter getDateAdapter() {
		return dateAdapter;
	}

	public AndroidDatabaseConnection getReadOnlyConnection() throws SQLException {
		if (readableDb == null) {
			readableDb = dbHelper.getReadableDatabase();
		}
		return new AndroidDatabaseConnection(readableDb, dateAdapter);
	}

	public AndroidDatabaseConnection getReadOnlyConnection(String username, String password) throws SQLException {
		return getReadOnlyConnection();
	}

	public AndroidDatabaseConnection getReadWriteConnection() throws SQLException {
		if (readWriteDb == null) {
			readWriteDb = dbHelper.getWritableDatabase();
		}
		return new AndroidDatabaseConnection(readWriteDb, dateAdapter);
	}

	public AndroidDatabaseConnection getReadWriteConnection(String username, String password) throws SQLException {
		return getReadWriteConnection();
	}

	public void close() throws SQLException {
		if (readableDb != null) {
			readableDb.close();
			readableDb = null;
		}
		if (readWriteDb != null) {
			readWriteDb.close();
			readWriteDb = null;
		}
	}
}
