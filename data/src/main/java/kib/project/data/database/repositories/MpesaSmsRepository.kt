package kib.project.data.database.repositories

import kib.project.data.database.dao.MpesaSmsDao
import kib.project.data.database.entities.textMessage.MpesaSms
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

interface MpesaSmsRepository {
    suspend fun saveMpesaSms(mpesaSms: MpesaSms): Long
    fun getAllMpesaSms(): Flow<List<MpesaSms>>
}

class MpesaSmsRespositoryImpl(
    private val mpesaSmsDao: MpesaSmsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MpesaSmsRepository {
    override suspend fun saveMpesaSms(mpesaSms: MpesaSms) = mpesaSmsDao.insert(mpesaSms)

    override fun getAllMpesaSms(): Flow<List<MpesaSms>> = mpesaSmsDao.getAllMpesaSms()
}