package kib.project.data.utils

import kib.project.data.api.models.responses.PostSmsResponse
import kib.project.data.database.entities.textMessage.MpesaTransaction
import timber.log.Timber

fun PostSmsResponse.toMpesaTransaction(): MpesaTransaction? = MpesaTransaction(
    reference = this.reference.orEmpty(),
    amount = this.amount?.toInt() ?: -1,
    participant = this.participant.orEmpty(),
    phoneNumber = this.phoneNumber.orEmpty(),
    isCredit = this.isCredit ?: false,
    transactionCost = this.transactionCost?.toDoubleOrNull() ?: 0.00,
    balance = this.balance ?: 0.00,
    transactionDate = this.transactionDate.orEmpty(),
    message = this.message.orEmpty(),
).let {
    Timber.i(":: MpesaTransaction: isValid[ ${it.isValid} ]: $it")
    it
}.takeIf { it.isValid }
