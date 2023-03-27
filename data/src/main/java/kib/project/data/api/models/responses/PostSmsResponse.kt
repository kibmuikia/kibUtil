package kib.project.data.api.models.responses

import com.google.gson.annotations.SerializedName

data class PostSmsResponse(
    @SerializedName("amount") val amount: Double?,
    @SerializedName("reference") val reference: String?,
    @SerializedName("participant") val participant: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("isCredit") val isCredit: Boolean?,
    @SerializedName("transactionCost") val transactionCost: String?,
    @SerializedName("balance") val balance: Double?,
    @SerializedName("transactionDate") val transactionDate: String?,
    @SerializedName("message") val message: String?,
)
/*
{
    "amount": 70,
    "reference": "RB0719U7GI",
    "participant": "PERSON  DOE ",
    "phoneNumber": "0700112233",
    "isCredit": false,
    "transactionCost": "0.00",
    "balance": 0,
    "transactionDate": "1/2/23 5:24 PM",
    "message": "RB0719U7GI Confirmed. Ksh70.00 sent to PERSON  DOE 0700112233 on 1/2/23 at 5:24 PM. New M-PESA balance is Ksh0.00. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari"
}
*/
