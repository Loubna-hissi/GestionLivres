package ma.maroc.bookappversion10

import android.provider.BaseColumns

object UserCol {
    object UserColumns: BaseColumns {
        const val TABLE_USER="users"
        const val COL_ID="id"
        const val COL_NAME="name"
        const val COL_AGE="age"
        const val COL_PHONE="phone"
        const val COL_EMAIL="email"
        const val COL_PASSWORD="password"

        val SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE "+ TABLE_USER+"("+
                    COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    COL_NAME+" VARCHAR(256),"+
                    COL_AGE+" INTEGER,"+
                    COL_PHONE+" VARCHAR(256),"+
                    COL_EMAIL+" VARCHAR(256),"+
                    COL_PASSWORD+" VARCHAR(256))";

        val SQL_DROP_TABLE_USER = "DROP TABLE  IF EXISTS $TABLE_USER"

    }
}
