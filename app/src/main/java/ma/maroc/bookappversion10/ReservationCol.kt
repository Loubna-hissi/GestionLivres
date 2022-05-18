package ma.maroc.bookappversion10

import android.provider.BaseColumns

object ReservationCol {

    object ResColumns: BaseColumns {
        const val TABLE_RESERVATIONS="reservations"
        const val COL_IDRES="idRes"
        const val COL_IDUSER="IdUser"
        const val COL_IDBOOK="idBook"
        const val COL_DATERES="dateRes"

        val SQL_CREATE_ENTRIES_RESERVATIONS =
            "CREATE TABLE "+ TABLE_RESERVATIONS+"("+
                    COL_IDRES +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    COL_IDUSER+" INTEGER,"+
                    COL_IDBOOK+" INTEGER,"+
                    COL_DATERES+" VARCHAR(256))";
        val SQL_DROP_TABLE_RESERVATIONS = "DROP TABLE  IF EXISTS $TABLE_RESERVATIONS"
    }
}