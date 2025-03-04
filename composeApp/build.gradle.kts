import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.cocoapods)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    cocoapods {
        version = "1.0"
        summary = "Revenue Cat KMP Sample"
        homepage = "https://revenuecatkmpt.plusmobileapps.com"

        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")

        pod("PurchasesHybridCommon") {
            version = libs.versions.purchases.common.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("PurchasesHybridCommonUI") {
            version = libs.versions.purchases.common.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        framework {
            baseName = "ComposeApp"
            isStatic = false
            freeCompilerArgs += "-Xbinary=bundleId=com.plusmobileapps.kmp.samples.revenuecat"
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}

android {
    namespace = "com.plusmobileapps.kmp.samples.revenuecat"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.plusmobileapps.kmp.samples.revenuecat"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

buildkonfig {
    packageName = "com.plusmobileapps.kmp.samples.revenuecat"

    defaultConfigs {
        defaultConfigs {
            buildConfigField(
                FieldSpec.Type.STRING,
                "REVENUECAT_ANDROID_API_KEY",
                localProperties.getProperty("REVENUECAT_ANDROID_API_KEY") as String? ?: ""
            )
            buildConfigField(
                FieldSpec.Type.STRING,
                "REVENUECAT_IOS_API_KEY",
                localProperties.getProperty("REVENUECAT_IOS_API_KEY") as String? ?: ""
            )
        }
    }
}