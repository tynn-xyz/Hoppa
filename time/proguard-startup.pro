# https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-master-dev/startup/startup-runtime/proguard-rules.pro
# This Proguard rule ensures that ComponentInitializers are are neither shrunk nor obfuscated.
# This is because they are discovered and instantiated during application initialization.
-keep class * extends androidx.startup.Initializer {
    # Keep the public no-argument constructor while allowing other methods to be optimized.
    <init>();
}
-assumenosideeffects class androidx.startup.StartupLogger
