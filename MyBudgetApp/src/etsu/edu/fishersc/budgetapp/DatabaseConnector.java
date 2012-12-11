package etsu.edu.fishersc.budgetapp;

import java.util.Calendar;
import java.util.Date;
import java.sql.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseConnector 
{
   // database name
   private static final String DATABASE_NAME = "BudgetTransactions";
   private SQLiteDatabase database; // database object
   private DatabaseOpenHelper databaseOpenHelper; // database helper

   // public constructor for DatabaseConnector
   public DatabaseConnector(Context context) 
   {
      // create a new DatabaseOpenHelper
      databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
   } // end DatabaseConnector constructor

   // open the database connection
   public void open() throws SQLException 
   {
      // create or open a database for reading/writing
      database = databaseOpenHelper.getWritableDatabase();
   } // end method open

   // close the database connection
   public void close() 
   {
      if (database != null)
         database.close(); // close the database connection
   } // end method close

   // inserts a new transaction in the database
   public void insertTransaction(String date, String itemName, int category, double amount) 
   {
      ContentValues newTransaction = new ContentValues();
      	
      newTransaction.put("date", date);
      newTransaction.put("item", itemName);
      newTransaction.put("cat_id", category);
      newTransaction.put("amount", amount);
      
      open(); // open the database
      database.insert("transactions", null, newTransaction);
      Log.v("newTransaction", newTransaction.valueSet().toString());
      close(); // close the database
   } // end method insertContact

   // inserts a transaction into the database
   public void updateTransaction(int id, Boolean expense, String date, String name, String category, double amount) 
   {
      ContentValues editTransaction = new ContentValues();
      editTransaction.put("date", date);
      editTransaction.put("name", name);
      editTransaction.put("category", category);
      editTransaction.put("amount", amount);
      
      
      open(); // open the database
      database.update("transactions", editTransaction, "_id=" + id, null);
      close(); // close the database
   } // end method updateContact
   
   public void insertCategory(String cat)
   {
	   ContentValues newCategory = new ContentValues();
	      
	      newCategory.put("category_name", cat);
	     
	      
	      open(); // open the database
	      database.insert("categorys", null, newCategory);
	      close(); // close the database
   }

   // return a Cursor with all contact information in the database
   public Cursor getAllTransactions() 
   {
      return database.query("transactions", new String[] {"_id", "amount"}, null, null, null, null, "amount");
   } // end method getAllTransaction

   // get a Cursor containing all information about the transaction specified
   // by the given id
   public Cursor getOneTransaction(Cursor result) 
   {
      return database.query("transactions", null, "_id=" + result, null, null, null, null);
   } // end method getOnTransaction
   
   public Cursor getAllTransactionsOrderedByCategory()
   {
	   return database.query("transactions", new String[] {"item", "cat_id"}, null, null, null, null, "cat_id");
   }

   public Cursor getAllCategorys()
   {
	   return database.query("categorys", new String[] {"cat_id", "category_name"}, 
		         null, null, null, null, null);
   }
   
   public Cursor getCategoryID(String category)
   {
	   return database.query("categorys", new String[] {"cat_id"}, "category_name='"+category+"'", null, null, null, null);
   }
   // delete the transaction specified by the given String name
   public void deleteTransaction(long id) 
   {
      open(); // open the database
      database.delete("transactions", "trans_id='" + id + "'", null);
      Log.v("id deleted", id + "");
      close(); // close the database
   } // end method deleteTransaction
   
   public void deleteCategory(long id) 
   {
      open(); // open the database
      database.delete("categorys", "cat_id='" + id + "'", null);
      Log.v("id deleted", id + "");
      close(); // close the database
   } // end method deleteTransaction
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper 
   {
      // public constructor
      public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) 
      {
         super(context, name, factory, version);
      } // end DatabaseOpenHelper constructor

      // creates the transactions table when the database is created
      @Override
      public void onCreate(SQLiteDatabase db) 
      {
         // query to create a new table named transactions
    	  String createCategory =  "CREATE TABLE categorys" + "(cat_id integer primary key autoincrement, category_name TEXT, total_amount LONG, percent DOUBLE);";
          
          String createQuery = "CREATE TABLE transactions" + "(trans_id integer primary key autoincrement,  item TEXT, amount LONG, cat_id integer, date TEXT, FOREIGN KEY(cat_id) REFERENCES categorys(cat_id));";
                  
         db.execSQL(createQuery); // execute the query
         db.execSQL(createCategory);
      } // end method onCreate

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
      {
      } // end method onUpgrade
   } // end class DatabaseOpenHelper
} // end class DatabaseConnector