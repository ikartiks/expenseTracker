package ikartiks.expensetracker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactionDetails")
data class TransactionDetails
    (@PrimaryKey(autoGenerate = true) var id: Int?,
      var note: String?,var transactionTypeId:Int, var date:Date,
     var accountId:Int, var amount:Int
){
    constructor():this(null,null,0,Date(),0,0)
}