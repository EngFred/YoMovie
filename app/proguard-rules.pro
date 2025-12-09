# ----------------------------------------------------------------------------
# General Android & Kotlin
# ----------------------------------------------------------------------------
-dontwarn javax.annotation.**
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault

# ----------------------------------------------------------------------------
# Application Data Models
# ----------------------------------------------------------------------------
# Prevents R8 from renaming your Data Transfer Objects (DTOs)
# otherwise Retrofit/Gson won't be able to map JSON to your classes.
-keep class com.omongole.fred.yomovieapp.data.model.** { *; }
-keep class com.omongole.fred.yomovieapp.domain.model.** { *; }

# ----------------------------------------------------------------------------
# Retrofit & Network
# ----------------------------------------------------------------------------
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8

# Keep Retrofit service interfaces
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# ----------------------------------------------------------------------------
# Gson / Moshi (Serialization)
# ----------------------------------------------------------------------------
# If you use @SerializedName, fields are generally safe, but keeping the class
# structure prevents missing constructor crashes.
-keep class com.google.gson.** { *; }
-keep class com.squareup.moshi.** { *; }

# ----------------------------------------------------------------------------
# Room Database
# ----------------------------------------------------------------------------
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# ----------------------------------------------------------------------------
# Coil (Image Loading)
# ----------------------------------------------------------------------------
-dontwarn coil.**

# ----------------------------------------------------------------------------
# YouTube Player
# ----------------------------------------------------------------------------
-keep class com.pierfrancescosoffritti.androidyoutubeplayer.** { *; }
-keep interface com.pierfrancescosoffritti.androidyoutubeplayer.** { *; }

# ----------------------------------------------------------------------------
# Jetpack Compose
# ----------------------------------------------------------------------------
# Usually handled automatically by R8, but good to ensure Previews don't cause issues
-keep class androidx.compose.ui.tooling.preview.Preview { *; }