package ikartiks.expensetracker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ikartiks.expensetracker.entities.Account
import ikartiks.expensetracker.entities.TransactionDetails
import ikartiks.expensetracker.entities.TransactionType
import ikartiks.expensetracker.entities.ViewTransactionDetails
import java.util.*

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(vararg account: Account)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactionType(vararg transactionType: TransactionType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactionDetails(vararg transactionDetails: TransactionDetails)

    @Query("SELECT * FROM viewTransactionDetails " +
            "WHERE  " +//transactionDetailsDate BETWEEN :from AND :to
            " transactionDetailsAccountId = :accountId")//and
    fun findTransactionDetails(accountId:Int): List<ViewTransactionDetails>//from: Date?, to: Date?,

    @Query("SELECT * FROM account")
    fun loadAllAccount(): Array<Account>

    @Query("SELECT * FROM transactionType")
    fun loadAllTransactionTypes(): Array<TransactionType>

    @Query("SELECT * FROM transactionDetails")
    fun loadAllTransactionDetails(): Array<TransactionDetails>
}