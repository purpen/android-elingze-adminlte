package com.stephen.taihuoniaolibrary.common;

import java.io.Serializable;

/**
 * @author lilin
 * created at 2016/4/5 17:39
 */
public class HttpResponseBean<T> implements Serializable {
    private boolean success;
    private boolean is_error;
    private String status;
    private String message;
    private T data;
    private String current_user_id;

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return is_error;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getCurrent_user_id() {
        return current_user_id;
    }
}
