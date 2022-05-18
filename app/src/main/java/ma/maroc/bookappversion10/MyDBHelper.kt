package ma.maroc.bookappversion10

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.fragment.app.FragmentActivity
import ma.maroc.bookappversion10.Admin.Admin_Table.SQL_CREATE_TABLE
import ma.maroc.bookappversion10.Admin.Admin_Table.SQL_DROP_TABLE
import ma.maroc.bookappversion10.Book.Books_Table.SQL_CREATE_TABLE_BOOKS
import ma.maroc.bookappversion10.Book.Books_Table.SQL_DROP_TABLE_BOOKS
import ma.maroc.bookappversion10.Book.Books_Table.TABLE_NAME_BOOKS
import ma.maroc.bookappversion10.ReservationCol.ResColumns.SQL_CREATE_ENTRIES_RESERVATIONS
import ma.maroc.bookappversion10.ReservationCol.ResColumns.SQL_DROP_TABLE_RESERVATIONS
import ma.maroc.bookappversion10.UserCol.UserColumns.SQL_CREATE_ENTRIES_USER
import ma.maroc.bookappversion10.UserCol.UserColumns.SQL_DROP_TABLE_USER
import ma.maroc.bookappversion10.VoteCol.VoteColumns.SQL_CREATE_ENTRIES_VOTE

class MyDBHelper(context: FragmentActivity?): SQLiteOpenHelper(context,db_name,null, db_version) {

    companion object{
        val db_name = "Loubna.db"
        var db_version=1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
        db?.execSQL(SQL_CREATE_TABLE_BOOKS)
        db?.execSQL(SQL_CREATE_ENTRIES_USER)
        db?.execSQL(SQL_CREATE_ENTRIES_RESERVATIONS)
        db?.execSQL(SQL_CREATE_ENTRIES_VOTE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP_TABLE)
        db?.execSQL(SQL_DROP_TABLE_BOOKS)
        db?.execSQL(SQL_DROP_TABLE_USER)
        db?.execSQL(SQL_DROP_TABLE_RESERVATIONS)
        db?.execSQL(VoteCol.VoteColumns.SQL_DROP_TABLE_USER)
        onCreate(db)
    }
    infix fun getBookData(id:Int): BookN {
        var book=BookN(0,"null","null",0,0,"null",ByteArray(0),"null")
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM ${TABLE_NAME_BOOKS} WHERE ${BaseColumns._ID} = ?"
        val result=db.rawQuery(selectQuery, arrayOf(id.toString()))
        if(result.moveToFirst()){
            do {
                val bookname = result.getString(result.getColumnIndexOrThrow(Book.Books_Table.Name))
                val bookauteur = result.getString(result.getColumnIndexOrThrow(Book.Books_Table.Auteur))
                val booknbpages= result.getInt(result.getColumnIndexOrThrow(Book.Books_Table.Nbpages))
                val bookstock = result.getInt(result.getColumnIndexOrThrow(Book.Books_Table.Stock))
                val bookdes = result.getString(result.getColumnIndexOrThrow(Book.Books_Table.Description))
                val bookimage= result.getBlob(result.getColumnIndexOrThrow(Book.Books_Table.Image))
                val bookCategory=result.getString(result.getColumnIndexOrThrow(Book.Books_Table.Category))
                val bookVoix = result.getInt(result.getColumnIndexOrThrow(Book.Books_Table.Voix))
                book = BookN(result.getString(0).toInt(),
                   bookname,bookauteur,booknbpages,bookstock,bookdes,bookimage,bookCategory,bookVoix)
            }while(result.moveToNext())
        }
        result.close()
        db.close()
        return book
    }
}