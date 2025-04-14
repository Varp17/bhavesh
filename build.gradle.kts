buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")

    }
}
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        maven { url "https://jitpack.io"}
//    }
//}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.0" apply false

    id("com.google.gms.google-services") version "4.3.15" apply false
}
dependencies {

}

