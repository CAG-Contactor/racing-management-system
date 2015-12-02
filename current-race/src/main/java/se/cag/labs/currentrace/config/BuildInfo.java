/*
 * Created by Daniel Marell 13-05-04 2:39 PM
 */
package se.cag.labs.currentrace.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides build version.
 * <br>
 * version=${project.version}
 */
public final class BuildInfo {
    private static Properties props = new Properties();

    static {
        InputStream in = BuildInfo.class.getResourceAsStream("/BuildInfo.properties");
        try {
            props.load(in);
        } catch (IOException e) {
        } finally {
            try {
                in.close();
            } catch (IOException ignore) {
            }
        }
    }

    private BuildInfo() {
    }

    /**
     * Get application version.
     *
     * @return AppVersion
     */
    public static String getVersion() {
        String version = props.getProperty("AppVersion");
        if (version.contains("SNAPSHOT")) {
            return "dev-build";
        }
        return version;
    }

    public static String getTitle() {
        return props.getProperty("title");
    }

    public static String getDescription() {
        return props.getProperty("description");
    }
}
