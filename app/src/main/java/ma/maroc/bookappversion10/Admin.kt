package ma.maroc.bookappversion10

import android.provider.BaseColumns

object Admin {
    object Admin_Table: BaseColumns {
        const val TABLE_NAME = "Admin"
        const val COLUMN_FIRST_NAME ="nom"
        const val  COLUMN_SECOND_NAME = "password"

        const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME("+
                "${BaseColumns._ID} INTEGER PRIMARY KEY,"+
                "$COLUMN_FIRST_NAME TEXT,"+ "$COLUMN_SECOND_NAME TEXT)"

        const val SQL_DROP_TABLE = "DROP TABLE  IF EXISTS $TABLE_NAME"
    }

}