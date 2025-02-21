import com.vanniktech.maven.publish.SonatypeHost
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.vanniktech.maven)
}

val mavenCentralUsername: String by project
val mavenCentralPassword: String by project
val signingKeyId: String by project
val signingPassword: String by project
val signingSecretKeyRingFile: String by project

val POM_LICENSE_NAME: String by project
val POM_LICENSE_URL: String by project
val POM_LICENSE_DIST: String by project
val POM_DESCRIPTION: String by rootProject
val POM_URL: String by project

val POM_SCM_URL: String by project
val POM_SCM_CONNECTION: String by project
val POM_SCM_DEV_CONNECTION: String by project

val POM_DEVELOPER_ID: String by project
val POM_DEVELOPER_NAME: String by project
val POM_DEVELOPER_URL: String by project
val POM_DEVELOPER_EMAIL: String by project

val GROUP: String by project
val VERSION_NAME: String by project

android {
    namespace = "id.elutility.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    
    buildFeatures {
        viewBinding = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

mavenPublishing {
    val secretProperties = Properties().apply {
        load(FileInputStream(project.file("secret.properties")))
    }

    val pomName = secretProperties.getProperty("POM_NAME")
    val pomArtifactId = secretProperties.getProperty("POM_ARTIFACT_ID")

    coordinates(
        groupId = GROUP,
        artifactId = pomArtifactId,
        version = VERSION_NAME
    )

    pom {
        name.set(pomName)
        description.set(POM_DESCRIPTION)
        url.set(POM_URL)
        licenses {
            license {
                name.set(POM_LICENSE_NAME)
                url.set(POM_LICENSE_URL)
                distribution.set(POM_LICENSE_DIST)
            }
        }
        developers {
            developer {
                id.set(POM_DEVELOPER_ID)
                name.set(POM_DEVELOPER_NAME)
                url.set(POM_DEVELOPER_URL)
                email.set(POM_DEVELOPER_EMAIL)
            }
        }
        scm {
            url.set(POM_SCM_URL)
            connection.set(POM_SCM_CONNECTION)
            developerConnection.set(POM_SCM_DEV_CONNECTION)
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.preferences.ktx)
    implementation(libs.androidx.security.crypto)
    implementation(libs.google.gson)

    implementation(libs.okhttp3.client)
    implementation(libs.okhttp3.logging)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.rxjava2)

    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.core)
    implementation(libs.ktor.android)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.serialization.jvm)
    implementation(libs.ktor.kotlinx.json)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.gson)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.lottie.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}