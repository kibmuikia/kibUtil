package kib.project.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kib.project.data.database.AppDatabase
import kib.project.data.database.dao.MpesaTransactionDao
import kib.project.data.utils.createFakeMpesaTransaction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MpesaTransactionDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: MpesaTransactionDao

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.mpesaTransactionDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun testInsertAndGetById() = runBlockingTest {
        // given
        val transaction = createFakeMpesaTransaction()
        dao.insert(transaction)

        // when
        val result = dao.getMpesaTransactionById(transaction.id).firstOrNull()

        // then
        assertEquals(transaction, result)
    }

    @Test
    fun testGetAllMpesaTransactions() = runBlockingTest {
        // given
        val transactions = (1..3).map { createFakeMpesaTransaction() }
        transactions.forEach { dao.insert(it) }

        // when
        val result = dao.getAllMpesaTransactions().firstOrNull()

        // then
        assertEquals(transactions, result)
    }

    @Test
    fun testGetMpesaTransactionByReference() = runBlockingTest {
        // given
        val transaction = createFakeMpesaTransaction()
        dao.insert(transaction)

        // when
        val result = dao.getMpesaTransactionByReference(transaction.reference).firstOrNull()

        // then
        assertEquals(transaction, result)
    }

    @Test
    fun testGetMpesaTransactionsByAmountRange() = runBlockingTest {
        // given
        val transactions = listOf(
            createFakeMpesaTransaction().copy(amount = 50),
            createFakeMpesaTransaction().copy(amount = 100),
            createFakeMpesaTransaction().copy(amount = 200),
            createFakeMpesaTransaction().copy(amount = 300)
        )
        transactions.forEach { dao.insert(it) }

        // when
        val result = dao.getMpesaTransactionsByAmountRange(100, 200).firstOrNull()

        // then
        assertEquals(listOf(transactions[2], transactions[1]), result)
    }

    @Test
    fun testGetMpesaTransactionsByParticipant() = runBlockingTest {
        // given
        val transactions = listOf(
            createFakeMpesaTransaction().copy(participant = "John Doe"),
            createFakeMpesaTransaction().copy(participant = "Jane Doe"),
            createFakeMpesaTransaction().copy(participant = "John Smith")
        )
        transactions.forEach { dao.insert(it) }

        // when
        val result = dao.getMpesaTransactionsByParticipant("John Doe").firstOrNull()

        // then
        assertEquals(listOf(transactions[0]), result)
    }
}