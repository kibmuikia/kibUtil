package kib.project.data.database.entities.textMessage

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["reference"], unique = true)], tableName = "mpesa_transaction")
data class MpesaTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val reference: String = "",
    val amount: Int = -1,
    val participant: String = "",
    val phoneNumber: String = "",
    val isCredit: Boolean = false,
    val transactionCost: Double = 0.00,
    val balance: Double = 0.00,
    val transactionDate: String = "",
    val message: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
/*
*
*   {
      "reference": "RD80MV4AQR",
      "amount": 1,
      "participant": "PERSON  DOE",
      "phoneNumber": "0711222333",
      "isCredit": true,
      "transactionCost": 0,
      "balance": 1410.89,
      "transactionDate": "7/1/23 9:00 PM",
      "message": "RD80MV4AQR Confirmed.You have received Ksh1.00 from PERSON  DOE 0711222333 on 7/1/23 at 9:00 PM  New M-PESA balance is Ksh1,410.89. Use Lipa Na M-PESA Buy Goods option to pay for shopping at NO COST."
    }
* */


