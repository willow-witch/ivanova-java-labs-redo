package lab2.registration.service;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerService {

    private String stringLocationLogs = "log";
    private String logName;
    private Logger LOGGER;

    private final boolean isAppendMode = true;
    private FileHandler fileHandler ;

    public LoggerService(String nameLog) {
        this.logName = nameLog;
        InitializationLoggerService();
    }

    public LoggerService(String nameLog, String pathLogs) {
        this.stringLocationLogs = pathLogs;
        this.logName = nameLog;
        InitializationLoggerService();
    }

    private void InitializationLoggerService() {
        try {
            fileHandler = new FileHandler(stringLocationLogs, isAppendMode);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER = Logger.getLogger(logName);
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fileHandler);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void WriteLogsInfo(String msg){
        LOGGER.log(Level.INFO,msg);
    }

    public void WriteLogsWarning(String msg){
        LOGGER.log(Level.WARNING,msg);
    }

}
