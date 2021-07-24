package com.chess.ui;

public final class OSVerifier {
    public final static String os = System.getProperty("os.name").toLowerCase();

    public SystemType check() {
        if (os.contains("win")) {
            return SystemType.WINDOWS;
        } else if (os.contains("mac")) {
            return SystemType.MAC;
        } else if (os.contains("linux")) {
            return SystemType.LINUX;
        } else {
            return SystemType.OTHER;
        }
    }
}
