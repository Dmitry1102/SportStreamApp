plugins {
   id("com.android.application")
   id("org.jetbrains.kotlin.android")
   id("androidx.navigation.safeargs")
}

android {
   namespace = "com.example.streamapp"
   compileSdk = 34

   defaultConfig {
      applicationId = "com.example.streamapp"
      minSdk = 24
      targetSdk = 34
      versionCode = 1
      versionName = "1.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
   }

   buildFeatures {
      viewBinding = true
   }

   buildTypes {
      release {
         isMinifyEnabled = false
         proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
         )
      }
   }

   compileOptions {
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
   }

   kotlinOptions {
      jvmTarget = "1.8"
   }
}

val coilVersion = "2.4.0"
val koinVersion = "3.5.0"
val retrofitVersion = "2.9.0"
val retrofitAdapterVersion = "3.0.0"
val gsonVersion = "2.9.0"
val okhhtpVersion = "5.0.0-alpha.2"
val rxjavaVersion = "3.1.7"
val rxandroidVersion = "3.0.2"
val media3Version = "1.1.1"
val mockitoVersion = "5.5.0"

dependencies {
   implementation("androidx.core:core-ktx:1.12.0")
   implementation("androidx.appcompat:appcompat:1.6.1")
   implementation("com.google.android.material:material:1.9.0")
   implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
   implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
   testImplementation("junit:junit:4.13.2")
   androidTestImplementation("androidx.test.ext:junit:1.1.5")
   androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
   testImplementation("android.arch.core:core-testing:1.1.1")

   //mockito
   testImplementation ("org.mockito:mockito-core:$mockitoVersion")

   //coil
   implementation("io.coil-kt:coil:$coilVersion")

   //koin
   implementation("io.insert-koin:koin-android:$koinVersion")

   //retrofit
   implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
   implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
   implementation("com.github.akarnokd:rxjava3-retrofit-adapter:$retrofitAdapterVersion")

   //gson
   implementation("com.google.code.gson:gson:$gsonVersion")

   //okhttp3
   implementation("com.squareup.okhttp3:okhttp:$okhhtpVersion")
   implementation("com.squareup.okhttp3:logging-interceptor:$okhhtpVersion")

   //rxjava
   implementation("io.reactivex.rxjava3:rxjava:$rxjavaVersion")
   implementation("io.reactivex.rxjava3:rxandroid:$rxandroidVersion")

   //mediaPlayer
   implementation("androidx.media3:media3-exoplayer:$media3Version")
   implementation("androidx.media3:media3-ui:$media3Version")
   implementation("androidx.media3:media3-exoplayer-dash:$media3Version")
}