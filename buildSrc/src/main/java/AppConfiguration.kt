import org.gradle.api.JavaVersion

object AppConfiguration {
    const val CompileSdk = 33
    const val BuildTools = "32.0.0"
    const val MinSdk = 24
    const val TargetSdk = 33

    val UseJavaVersion = JavaVersion.VERSION_11
    val JvmTarget = UseJavaVersion.toString()

    const val AppPackageName = "kib.project.fast"
    const val VersionCode = 1000
    const val VersionName = "1.0.0.01"
}