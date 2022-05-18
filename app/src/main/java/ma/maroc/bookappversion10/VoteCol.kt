package ma.maroc.bookappversion10

import android.provider.BaseColumns

object VoteCol {
    object VoteColumns: BaseColumns {
        const val TABLE_VOTE="vote"
        const val COL_ID="id"
        const val COL_USERID="userId"
        const val COL_BOOKID="bookId"

        val SQL_CREATE_ENTRIES_VOTE =
            "CREATE TABLE "+ TABLE_VOTE+"("+
                    COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    COL_USERID+" INTEGER,"+
                    COL_BOOKID+" INTEGER)"

        val SQL_DROP_TABLE_USER = "DROP TABLE  IF EXISTS $TABLE_VOTE"

    }
}