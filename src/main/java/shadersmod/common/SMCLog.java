package shadersmod.common;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/common/SMCLog.class */
public abstract class SMCLog {
    public static final String smcLogName = "SMC";
    public static final Logger logger = LogManager.getLogger(smcLogName);
    public static final Level SMCERROR = Level.forName("SERR", 200);
    public static final Level SMCWARN = Level.forName("SWRN", 300);
    public static final Level SMCINFO = Level.forName("SINF", 400);
    public static final Level SMCDEBUG = Level.forName("SDBG", 500);
    public static final Level SMCFINE = Level.forName("SFN1", 600);
    public static final Level SMCFINER = Level.forName("SFN2", 610);
    public static final Level SMCFINEST = Level.forName("SFN3", 620);

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void error(String message) {
        logger.log(SMCERROR, message);
    }

    public static void warning(String message) {
        logger.log(SMCWARN, message);
    }

    public static void info(String message) {
        logger.log(SMCINFO, message);
    }

    public static void debug(String message) {
        logger.log(SMCDEBUG, message);
    }

    public static void fine(String message) {
        logger.log(SMCFINE, message);
    }

    public static void finer(String message) {
        logger.log(SMCFINER, message);
    }

    public static void finest(String message) {
        logger.log(SMCFINEST, message);
    }

    public static void log(Level level, String format, Object... args) {
        logger.log(level, String.format(format, args));
    }

    public static void error(String format, Object... args) {
        logger.log(SMCERROR, String.format(format, args));
    }

    public static void warning(String format, Object... args) {
        logger.log(SMCWARN, String.format(format, args));
    }

    public static void info(String format, Object... args) {
        logger.log(SMCINFO, String.format(format, args));
    }

    public static void debug(String format, Object... args) {
        logger.log(SMCDEBUG, String.format(format, args));
    }

    public static void fine(String format, Object... args) {
        logger.log(SMCFINE, String.format(format, args));
    }

    public static void finer(String format, Object... args) {
        logger.log(SMCFINER, String.format(format, args));
    }

    public static void finest(String format, Object... args) {
        logger.log(SMCFINEST, String.format(format, args));
    }
}
