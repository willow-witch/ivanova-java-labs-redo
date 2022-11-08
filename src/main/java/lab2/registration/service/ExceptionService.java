package lab2.registration.service;

import java.io.IOException;

public class ExceptionService extends IOException {

    private LoggerService LOGGER_SERVICE= new LoggerService("ExceptionServiceLog","lab2/Logs/ExceptionService/ExceptionLog");;

    private String message;
    private Class<?> whereClass;
    private String nameFunction;

    public ExceptionService(String msg, String nameFunction){
        this.message = msg;
        this.nameFunction = nameFunction;
        WriteLogs();
    }

    public void WriteLogs(){
        LOGGER_SERVICE.WriteLogsWarning(message);
    }

}
