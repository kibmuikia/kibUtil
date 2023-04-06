package kib.project.fast

import android.content.Intent
import kib.project.fast.utils.fetchTextMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class IntentExtTest {
    @Test
    fun `test fetchTextMessage function with invalid intent`() {
        // Create a mock intent without an SMS message
        val intent = Intent()
        // Call the fetchTextMessage function and verify the result
        val result = intent.fetchTextMessage()
        assertEquals(null, result)
    }
}
