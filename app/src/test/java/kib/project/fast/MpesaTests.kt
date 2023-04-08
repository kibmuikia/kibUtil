package kib.project.fast

import kib.project.fast.utils.hasNewMPESABalance
import kib.project.fast.utils.isMpesa
import kib.project.fast.utils.removeNewMPESABalance
import kib.project.fast.utils.replaceMPESABalance
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MPESATest {

    @Test
    fun `test hasNewMPESABalance function returns true when input string contains balance`() {
        val input =
            "RB0719U7GI Confirmed. Ksh70.00 sent to PERSON DOE 0700112233 on 1/2/23 at 5:24 PM. New M-PESA balance is Ksh4,560.50. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari"
        val result = input.hasNewMPESABalance()
        assertTrue(result)
    }

    @Test
    fun `test hasNewMPESABalance function returns false when input string does not contain balance`() {
        val input =
            "RB0719U7GI Confirmed. Ksh70.00 sent to PERSON DOE 0700112233 on 1/2/23 at 5:24 PM. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari"
        val result = input.hasNewMPESABalance()
        assertFalse(result)
    }

    @Test
    fun `test removeNewMPESABalance function removes balance when present`() {
        val input =
            "RB0719U7GI Confirmed. Ksh70.00 sent to PERSON DOE 0700112233 on 1/2/23 at 5:24 PM. New M-PESA balance is Ksh4,560.50. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari"
        val expectedOutput =
            "RB0719U7GI Confirmed. Ksh70.00 sent to PERSON DOE 0700112233 on 1/2/23 at 5:24 PM. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari"
        val result = input.removeNewMPESABalance()
        assertEquals(expectedOutput, result)
    }

    @Test
    fun `test removeNewMPESABalance function returns input string when balance not present`() {
        val input =
            "RB0719U7GI Confirmed. Ksh70.00 sent to PERSON DOE 0700112233 on 1/2/23 at 5:24 PM. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari"
        val result = input.removeNewMPESABalance()
        assertEquals(input, result)
    }

    @Test
    fun testReplaceMPESABalance() {
        val input =
            "RB0719U7GI Confirmed. Ksh70.00 sent to PERSON DOE 0700112233 on 1/2/23 at 5:24 PM. New M-PESA balance is Ksh3,450.68. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari"
        val expectedOutput =
            "RB0719U7GI Confirmed. Ksh70.00 sent to PERSON DOE 0700112233 on 1/2/23 at 5:24 PM. New M-PESA balance is Ksh0.00. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari"
        val actualOutput = input.replaceMPESABalance()
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun testIsMpesa() {
        val s1 = "MPESA"
        val s2 = "NOT_MPESA"

        assertTrue(s1.isMpesa())
        assertFalse(s2.isMpesa())
    }
}
