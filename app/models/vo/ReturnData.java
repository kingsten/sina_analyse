package models.vo;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-5-11
 * Time: 下午10:40
 * To change this template use File | Settings | File Templates.
 */
public class ReturnData {
    private Object data;
    private boolean success;
    private String message;

    public ReturnData(Object data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
