plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.chinchin.image.compare"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.chinhdl891"
            artifactId = "ImageSildeCompare"
            version = "1.0.2"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Image Compare Library")
                description.set("A library to compare images")
                url.set("https://github.com/chinhdl891/ImageSildeCompare")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("chinhdl891")
                        name.set("Chin Chin")
                        email.set("vuquocchinh891@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/chinhdl891/ImageSildeCompare.git")
                    developerConnection.set("scm:git:ssh://github.com/chinhdl891/ImageSildeCompare.git")
                    url.set("https://github.com/chinhdl891/ImageSildeCompare")
                }
            }
        }
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.glide)
    androidTestImplementation(libs.androidx.espresso.core)

}
