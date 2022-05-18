package ma.maroc.bookappversion10

import android.provider.BaseColumns

object Book {
    object Books_Table: BaseColumns {
        const val  TABLE_NAME_BOOKS = "Books_Table"
        const val   Name ="name"
        const val   Auteur= "auteur"
        const val   Nbpages= "nbpages"
        const val   Stock= "stock"
        const val   Description= "description"
        const val   Image= "image"
        const val   Category= "category"
        const val   Voix= "voix"
        const val SQL_CREATE_TABLE_BOOKS = "CREATE TABLE $TABLE_NAME_BOOKS("+
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$Name TEXT,"+ "$Auteur TEXT,"+ "$Nbpages INTEGER ,"+ "$Stock INTEGER ,"+ "$Description TEXT,"+ "$Image BLOB,"+ "$Category TEXT,"+ "$Voix INTEGER DEFAULT 0)"

        const val SQL_DROP_TABLE_BOOKS = "DROP TABLE  IF EXISTS $TABLE_NAME_BOOKS"
    }
}