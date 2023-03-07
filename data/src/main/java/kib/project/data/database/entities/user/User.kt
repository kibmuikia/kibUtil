import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["userId"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var userId: String,
    var firstName: String?,
    var lastName: String?,
    var phoneNumber: String,
    var email: String?,
    var dateOfBirth: String?,
) {
    fun getFullName(): String = "$firstName $lastName"
}