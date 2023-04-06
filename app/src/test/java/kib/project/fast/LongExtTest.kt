import kib.project.fast.utils.toDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class LongExtTest {
    @Test
    fun `test toDateTime function with valid timestamp`() {
        val timestamp = 1649212680000L // 04-Apr-2022 14:38:00
        val expected = "05:38 - 06/Apr/2022"
        val result = timestamp.toDateTime()
        assertEquals(expected, result)
    }
}
