package TableTennis.Exception;

public class DataBaseException extends RuntimeException{

    // Конструктор, который не используется можно удалить
    public DataBaseException(String message){
        super(message);
    }
    public DataBaseException(String message,Throwable e){
        super(message,e);
    }
}
