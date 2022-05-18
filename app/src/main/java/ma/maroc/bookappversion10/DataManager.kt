package ma.maroc.bookappversion10


import android.content.ContentValues
import android.database.AbstractWindowedCursor
import android.database.CursorWindow
import android.os.Build
import android.provider.BaseColumns
import ma.maroc.bookappversion10.Admin.Admin_Table.COLUMN_FIRST_NAME
import ma.maroc.bookappversion10.Admin.Admin_Table.COLUMN_SECOND_NAME
import ma.maroc.bookappversion10.Admin.Admin_Table.TABLE_NAME
import ma.maroc.bookappversion10.Book.Books_Table.Auteur
import ma.maroc.bookappversion10.Book.Books_Table.Category
import ma.maroc.bookappversion10.Book.Books_Table.Description
import ma.maroc.bookappversion10.Book.Books_Table.Image
import ma.maroc.bookappversion10.Book.Books_Table.Name
import ma.maroc.bookappversion10.Book.Books_Table.Nbpages
import ma.maroc.bookappversion10.Book.Books_Table.Stock
import ma.maroc.bookappversion10.Book.Books_Table.TABLE_NAME_BOOKS
import ma.maroc.bookappversion10.Book.Books_Table.Voix
import ma.maroc.bookappversion10.ReservationCol.ResColumns.TABLE_RESERVATIONS
import ma.maroc.bookappversion10.UserCol.UserColumns.TABLE_USER
import ma.maroc.bookappversion10.VoteCol.VoteColumns.TABLE_VOTE

object DataManager {
    //Table Admin
    fun insererInformation(myHelper : MyDBHelper){
        val db = myHelper.writableDatabase
        val values = ContentValues()
        with(values){
            put("${BaseColumns._ID}",1)
            put("$COLUMN_FIRST_NAME", "Loubna")
            put("$COLUMN_SECOND_NAME", "123")
        }
       db?.insert(TABLE_NAME,null,values)
    }

    fun recuper_tt_Information(myHelper : MyDBHelper): ArrayList<ma.maroc.bookappversion10.admins>?{
        val db = myHelper.readableDatabase
        val admin = ArrayList<admins>()
        val cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null,
        )

        with(cursor){
            while(moveToNext()){
                val nom = getString(getColumnIndexOrThrow(COLUMN_FIRST_NAME))
                val password = getString(getColumnIndexOrThrow(COLUMN_SECOND_NAME))
                admin.add(admins(nom,password))
            }
        }

        return  admin
    }
    //Table Books
    fun insererBookInformation(
        myHelper: MyDBHelper, name: String, auteur: String,
        nbpages:Int,
        stock: Int,
        des:String,
        image:ByteArray,
        category:String
      ){
        val db = myHelper.writableDatabase
        val values = ContentValues()
        with(values){
            put("$Name", name)
            put("$Auteur", auteur)
            put("$Nbpages", nbpages)
            put("$Stock", stock)
            put("$Description", des)
            put("$Image", image)
            put("$Category", category)
        }
        val newRowId =db?.insert(TABLE_NAME_BOOKS,null,values)

    }
    fun recuper_books_Information(myHelper: MyDBHelper):ArrayList<ma.maroc.bookappversion10.Books>?{
        val db = myHelper.readableDatabase
        val book = ArrayList<Books>()
        val slq="SELECT * FROM $TABLE_NAME_BOOKS"

        val cursor=db.rawQuery(slq,null)
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    val id=cursor.getInt(0)
                    val bookname = cursor.getString(cursor.getColumnIndexOrThrow(Name))
                    val bookauteur = cursor.getString(cursor.getColumnIndexOrThrow(Auteur))
                    val booknbpages= cursor.getInt(cursor.getColumnIndexOrThrow(Nbpages))
                    val bookstock = cursor.getInt(cursor.getColumnIndexOrThrow(Stock))
                    val bookdes = cursor.getString(cursor.getColumnIndexOrThrow(Description))
                    val bookimage= cursor.getBlob(cursor.getColumnIndexOrThrow(Image))
                    val bookCategory=cursor.getString(cursor.getColumnIndexOrThrow(Category))
                    val bookVoix = cursor.getInt(cursor.getColumnIndexOrThrow(Voix))
                    book.add(Books(bookname,bookauteur,booknbpages,bookstock,bookdes,bookimage,bookCategory,bookVoix))

                }while(cursor.moveToNext())
            }
        }
        return  book
    }
    //Table User
    fun addUser(DbHelper: MyDBHelper,user:User) {
        val db = DbHelper.writableDatabase
        var cv = ContentValues()
        cv.apply {
            put(UserCol.UserColumns.COL_NAME, user.name)
            put(UserCol.UserColumns.COL_AGE, user.age)
            put(UserCol.UserColumns.COL_PHONE, user.phone)
            put(UserCol.UserColumns.COL_EMAIL, user.email)
            put(UserCol.UserColumns.COL_PASSWORD, user.password)
        }
        var result = db.insert(TABLE_USER, null, cv)

    }

    fun checkUser(DbHelper: MyDBHelper,email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(UserCol.UserColumns.COL_NAME)
        val db = DbHelper.readableDatabase
        // selection criteria
        val selection = "${UserCol.UserColumns.COL_EMAIL}= ? AND ${UserCol.UserColumns.COL_PASSWORD} = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }
    fun checkUser(DbHelper: MyDBHelper,email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(UserCol.UserColumns.COL_ID)
        val db = DbHelper.readableDatabase
        // selection criteria
        val selection = "${UserCol.UserColumns.COL_EMAIL} = ?"
        // selection argument
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }
    fun readId(DbHelper: MyDBHelper,email:String):Int{
        val db = DbHelper.readableDatabase
        var userId=0
        val selectQuery = "SELECT  * FROM $TABLE_USER WHERE ${UserCol.UserColumns.COL_EMAIL} = ?"
        val result=db.rawQuery(selectQuery, arrayOf(email))
        if(result.moveToFirst()){
            do {
                userId = result.getString(0).toInt()
            }while(result.moveToNext())
        }

        result.close()
        db.close()
        return userId
    }

    fun getUserData(DbHelper: MyDBHelper,id:Int):User{
        //val UserData : MutableList<User> = ArrayList()
        var user=User(0,"null",0,"null","null","null")
        val db = DbHelper.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_USER WHERE ${UserCol.UserColumns.COL_ID} = ?"
        val result=db.rawQuery(selectQuery, arrayOf(id.toString()))
        if(result.moveToFirst()){
            do {
                user = User(result.getString(0).toInt(),
                    result.getString(1).toString(),
                    result.getString(2).toInt(),
                    result.getString(3).toString(),
                    result.getString(4).toString(),
                    result.getString(5))
            }while(result.moveToNext())
        }
        result.close()
        db.close()
        return user
    }
    public fun getAllUsers(DbHelper: MyDBHelper):ArrayList<ma.maroc.bookappversion10.User>?{
        var listUser=ArrayList<User>()
        val db = DbHelper.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_USER "
        val result=db.rawQuery(selectQuery, null)
        if(result.moveToFirst()){
            do {
               val user = User(result.getString(0).toInt(),
                    result.getString(1).toString(),
                    result.getString(2).toInt(),
                    result.getString(3).toString(),
                    result.getString(4).toString(),
                    result.getString(5))
                listUser.add(user)
            }while(result.moveToNext())
        }
        result.close()
        db.close()
        return listUser
    }
    fun getAllusersize(DbHelper: MyDBHelper):Int{
        val nbuser= getAllUsers(DbHelper)!!.size
        return nbuser
    }
    fun getAllbooksize(DbHelper: MyDBHelper):Int{
        val nbuser= recuper_books_Information(DbHelper)!!.size
        return nbuser
    }
    fun updateProfileUser(DbHelper: MyDBHelper,user: User) {
        val db = DbHelper.writableDatabase
        val values = ContentValues()
        values.put(UserCol.UserColumns.COL_NAME, user.name)
        values.put(UserCol.UserColumns.COL_AGE, user.age)
        values.put(UserCol.UserColumns.COL_PHONE, user.phone)
        values.put(UserCol.UserColumns.COL_EMAIL, user.email)
        values.put(UserCol.UserColumns.COL_PASSWORD, user.password)
        db.update(
            TABLE_USER, values, "${UserCol.UserColumns.COL_ID} = ?",
            arrayOf(user.id.toString()))
        db.close()
    }

    fun getAllBooks(DbHelper: MyDBHelper):ArrayList<BookN> {
        val list : ArrayList<BookN> = ArrayList()
        val db = DbHelper.readableDatabase
        val query ="Select * from $TABLE_NAME_BOOKS"
        val result=db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val bookname = result.getString(result.getColumnIndexOrThrow(Name))
                val bookauteur = result.getString(result.getColumnIndexOrThrow(Auteur))
                val booknbpages= result.getInt(result.getColumnIndexOrThrow(Nbpages))
                val bookstock = result.getInt(result.getColumnIndexOrThrow(Stock))
                val bookdes = result.getString(result.getColumnIndexOrThrow(Description))
                val bookimage= result.getBlob(result.getColumnIndexOrThrow(Image))
                val bookCategory=result.getString(result.getColumnIndexOrThrow(Category))
                val bookVoix = result.getInt(result.getColumnIndexOrThrow(Voix))
                var book = BookN(result.getString(0).toInt(),
                    bookname,bookauteur,booknbpages,bookstock,bookdes,bookimage,bookCategory,bookVoix)
                list.add(book)
             
            }while(result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }


    fun reserveBook(DbHelper: MyDBHelper,idBook:Int,idUser:Int,dateRes:String,stock:String) {
        val db = DbHelper.writableDatabase
        var cv = ContentValues()
        cv.apply {
            put(ReservationCol.ResColumns.COL_IDUSER,idUser)
            put(ReservationCol.ResColumns.COL_IDBOOK,idBook)
            put(ReservationCol.ResColumns.COL_DATERES,dateRes)
        }
        var result = db.insert(TABLE_RESERVATIONS, null, cv)
        val values = ContentValues()
        values.put(Stock,stock.toInt()-1)

        db.update(
           TABLE_NAME_BOOKS, values, "${BaseColumns._ID} = ?",
            arrayOf(idBook.toString()))
        /*if(result==-1.toLong()) {
            Toast.makeText(context, "FAILED", Toast.LENGTH_LONG).show()
        }*/
    }

    fun checkRes(DbHelper: MyDBHelper, idBook: Int, idUser: Int):Boolean {
        // array of columns to fetch
        val columns = arrayOf(ReservationCol.ResColumns.COL_IDRES)
        val db = DbHelper.readableDatabase
        // selection criteria
        val selection = "${ReservationCol.ResColumns.COL_IDUSER}= ? AND ${ReservationCol.ResColumns.COL_IDBOOK} = ?"
        // selection arguments
        val selectionArgs = arrayOf(idUser.toString(), idBook.toString())
        // query user table with conditions
        val cursor = db.query(
            TABLE_RESERVATIONS, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }


    fun getBooksLiked(DbHelper: MyDBHelper):ArrayList<BookN> {
        var book=BookN(0,"null","null",0,0,"null",ByteArray(0),"null")
        // sorting orders
        val list : ArrayList<BookN> = ArrayList()
        val sortOrder = "${Voix} DESC"
        val db = DbHelper.readableDatabase
        // query the user table
        val result = db.query(
            TABLE_NAME_BOOKS, //Table to query
            null,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder)         //The sort order
        if (result.moveToFirst()) {
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
                list.add(book)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun getBooksReserved(DbHelper: MyDBHelper, userId: Int): ArrayList<BookN> {
        val db = DbHelper.readableDatabase
        val listId : ArrayList<Int> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_RESERVATIONS WHERE ${ReservationCol.ResColumns.COL_IDUSER} = ?"
        val res=db.rawQuery(selectQuery, arrayOf(userId.toString()))
        if(res.moveToFirst()){
            do {
                listId.add(res.getString(2).toInt())
            }while(res.moveToNext())
        }
        val list : ArrayList<BookN> = ArrayList()
        for(i in 0 until listId.size){
            var book=getBook(DbHelper,listId[i])
            list.add(book)

        }
        res.close()
        db.close()
        return list
    }

    fun getBook(DbHelper: MyDBHelper,id:Int):BookN {
        var book=BookN(0,"null","null",0,0,"null",ByteArray(0),"null")
        val db = DbHelper.readableDatabase
        val selectQuery =
            "SELECT  * FROM ${TABLE_NAME_BOOKS} WHERE ${BaseColumns._ID} = ?"
        val result = db.rawQuery(selectQuery, arrayOf(id.toString()))
        if (result.moveToFirst()) {
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
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return book
    }

    fun checkVote(DbHelper: MyDBHelper, idUser: Int, idBook: Int):Boolean {
        // array of columns to fetch
        val columns = arrayOf(VoteCol.VoteColumns.COL_ID)
        val db = DbHelper.readableDatabase
        // selection criteria
        val selection = "${VoteCol.VoteColumns.COL_USERID}= ? AND ${VoteCol.VoteColumns.COL_BOOKID} = ?"
        // selection arguments
        val selectionArgs = arrayOf(idUser.toString(), idBook.toString())
        // query user table with conditions
        val cursor = db.query(
            TABLE_VOTE, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false

    }
    fun updateBookVoix(DbHelper: MyDBHelper,voix: String, id: String) {
        val db = DbHelper.writableDatabase
        val values = ContentValues()
        values.put(Voix,voix.toInt()+1)

        db.update(
            TABLE_NAME_BOOKS, values, "${BaseColumns._ID} = ?",
            arrayOf(id))
        db.close()
    }

    fun voteBook(DbHelper: MyDBHelper,idBook:Int,idUser:Int) {
        val db = DbHelper.writableDatabase
        var cv = ContentValues()
        cv.apply {
            put(VoteCol.VoteColumns.COL_USERID,idUser)
            put(VoteCol.VoteColumns.COL_BOOKID,idBook)
        }
        db.insert(TABLE_VOTE, null, cv)

    }
    fun getBookByName(DbHelper: MyDBHelper,name:String):ArrayList<BookN> {
        val list : ArrayList<BookN> = ArrayList()
        val db = DbHelper.readableDatabase
        val selectQuery = "SELECT  * FROM ${TABLE_NAME_BOOKS} WHERE ${Name} = ?"
        val result=db.rawQuery(selectQuery, arrayOf(name))
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
                val book = BookN(result.getString(0).toInt(),
                    bookname,bookauteur,booknbpages,bookstock,bookdes,bookimage,bookCategory,bookVoix)
                list.add(book)
            }while(result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
}

