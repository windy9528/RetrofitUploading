package bwie.com.retrofitupdate1.entity;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public class Result<T> {

    private T result;
    private String message = "网络异常!";
    private String status = "-1";

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
