package com.small.tictactoe;

public class Logger {
    public enum Level { DEBUG, INFO, WARN, ERROR }

    private static Level currentLevel = Level.DEBUG;

    public static void setLevel(Level level) {
        currentLevel = level;
    }

    public static void debug(String msg) {
        log(Level.DEBUG, msg);
    }

    public static void info(String msg) {
        log(Level.INFO, msg);
    }

    public static void warn(String msg) {
        log(Level.WARN, msg);
    }

    public static void error(String msg) {
        log(Level.ERROR, msg);
    }
    @SuppressWarnings("java:S106")
    private static void log(Level level, String msg) {
        if (level.ordinal() >= currentLevel.ordinal()) {
            System.out.println("[" + level + "] " + msg);
        }
    }
}