package com.kredily.config;

/** Central config. Override any value with -Dkey=value or an environment variable. */
public final class Config {
    private Config() {}

    private static String get(String key, String env, String def) {
        String v = System.getProperty(key);
        if (v == null || v.isEmpty()) v = System.getenv(env);
        return (v == null || v.isEmpty()) ? def : v;
    }

    public static final String APPIUM_URL  = get("appium.url", "APPIUM_URL", "http://127.0.0.1:4723");
    public static final String APK_PATH    = get("apk.path", "APK_PATH", System.getProperty("user.dir") + "/kredily-mobile-v2.apk");
    // Find with: adb shell dumpsys window | grep mCurrentFocus   (Windows: findstr)
    public static final String APP_PACKAGE  = get("app.package", "APP_PACKAGE", "");
    public static final String APP_ACTIVITY = get("app.activity", "APP_ACTIVITY", "");
    public static final String DEVICE_NAME  = get("device.name", "DEVICE_NAME", "Android");

    public static final String VALID_USER = get("kredily.user", "KREDILY_USER", "peoplekredily1@yopmail.com");
    public static final String VALID_PASS = get("kredily.pass", "KREDILY_PASS", "Pass@9865");
    public static final String WRONG_PASS = "Wrong@1234";
}
