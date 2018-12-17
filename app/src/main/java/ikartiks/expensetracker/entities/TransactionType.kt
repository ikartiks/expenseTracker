package ikartiks.expensetracker.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactionType")
data class TransactionType(@PrimaryKey(autoGenerate = true) var id: Int?,
                           var name: String?, var type:String?){
    constructor():this(null,null,null)
}