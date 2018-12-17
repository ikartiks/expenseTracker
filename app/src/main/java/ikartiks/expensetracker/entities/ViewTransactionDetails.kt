package ikartiks.expensetracker.entities

import androidx.room.DatabaseView
import java.util.*

@DatabaseView(value=("select td.id as transactionDetailsId, td.accountId as transactionDetailsAccountId," +
        " td.note as transactionDetailsNote, tt.name as transactionTypeName, " +
        " td.amount as transactionDetailsAmount, "+
        " tt.type as transactionTypeType, td.date as transactionDetailsDate" +
        " from transactionDetails td, transactionType tt" +
        " where td.transactionTypeId = tt.id"),viewName = ("viewTransactionDetails"))
data class ViewTransactionDetails(var transactionDetailsId: Int,
                                  var transactionDetailsAmount: Int,
                                  var transactionDetailsAccountId: Int,
                                  var transactionDetailsNote: String?,
                                  var transactionDetailsDate: Date?,
                                  var transactionTypeName: String?,
                                  var transactionTypeType: String?)