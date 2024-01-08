import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.byteteam.bluesense"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.byteteam.bluesense"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val mqttHost = properties.getProperty("MQTT_HOST") ?: ""
        val mqttUsername = properties.getProperty("MQTT_USERNAME") ?: ""
        val mqttPassword = properties.getProperty("MQTT_PASSWORD") ?: ""

        buildConfigField(
            type = "String",
            name = "MQTT_HOST",
            value = mqttHost
        )
        buildConfigField(
            type = "String",
            name = "MQTT_USERNAME",
            value = mqttUsername
        )
        buildConfigField(
            type = "String",
            name = "MQTT_PASSWORD",
            value = mqttPassword
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val nav_version = "2.7.6"
    val compose_version =  "1.5.1"
    val vico = "1.13.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //PAHO MQTT
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0")
    implementation("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1")
    //QR Scanner
    implementation("com.google.android.gms:play-services-code-scanner:16.1.0")
    //Icon
    implementation("androidx.compose.material:material-icons-extended:$compose_version")
    //Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")
    //Splash screen
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:okhttp-urlconnection:4.4.1")
    implementation("com.google.code.gson:gson:2.6.2")
    //Chart vico
    // For Jetpack Compose.
    implementation("com.patrykandpatrick.vico:compose:$vico")
    implementation("com.patrykandpatrick.vico:compose-m3:$vico")
}
