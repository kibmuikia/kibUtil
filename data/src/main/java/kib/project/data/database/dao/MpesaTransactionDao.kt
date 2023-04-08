package kib.project.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kib.project.data.database.entities.textMessage.MpesaTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MpesaTransactionDao : BaseDao<MpesaTransaction> {
    @Query("SELECT * FROM mpesa_transaction ORDER BY createdAt DESC")
    fun getAllMpesaTransactions(): Flow<List<MpesaTransaction>>

    @Query("SELECT * FROM mpesa_transaction WHERE id = :id LIMIT 1")
    fun getMpesaTransactionById(id: Long): Flow<MpesaTransaction?>

    @Query("SELECT * FROM mpesa_transaction WHERE reference = :reference LIMIT 1")
    fun getMpesaTransactionByReference(reference: String): Flow<MpesaTransaction?>

    @Query("SELECT * FROM mpesa_transaction WHERE amount BETWEEN :minAmount AND :maxAmount ORDER BY createdAt DESC")
    fun getMpesaTransactionsByAmountRange(minAmount: Int, maxAmount: Int): Flow<List<MpesaTransaction>>

    @Query("SELECT * FROM mpesa_transaction WHERE participant = :participant ORDER BY createdAt DESC")
    fun getMpesaTransactionsByParticipant(participant: String): Flow<List<MpesaTransaction>>

    @Query("SELECT * FROM mpesa_transaction WHERE phoneNumber = :phoneNumber ORDER BY createdAt DESC")
    fun getMpesaTransactionsByPhoneNumber(phoneNumber: String): Flow<List<MpesaTransaction>>

    @Query("SELECT * FROM mpesa_transaction WHERE transactionCost BETWEEN :minTransactionCost AND :maxTransactionCost ORDER BY createdAt DESC")
    fun getMpesaTransactionsByTransactionCostRange(minTransactionCost: Double, maxTransactionCost: Double): Flow<List<MpesaTransaction>>
}