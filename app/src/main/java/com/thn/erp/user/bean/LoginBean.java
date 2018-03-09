package com.thn.erp.user.bean;

/**
 * @author lilin
 * created at 2016/8/25 16:13
 */
public class LoginBean {


    /**
     * meta : {"message":"登录成功！","status_code":200}
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vay50YWlodW9uaWFvLmNvbS9hcGkvYXV0aC9sb2dpbiIsImlhdCI6MTQ5OTI0NjU5MSwiZXhwIjoxNDk5MzMyOTkxLCJuYmYiOjE0OTkyNDY1OTEsImp0aSI6IjlpSXRzd2thR1M4TnZRU20iLCJzdWIiOiI4In0.Wqax69FRuPjSr8NOyEboRg4fUNza8_a5eEFuykHugcQ"}
     */

    public MetaBean meta;
    public DataBean data;

    public static class MetaBean {
        /**
         * message : 登录成功！
         * status_code : 200
         */

        public String message;
        public int status_code;
    }

    public static class DataBean {
        /**
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vay50YWlodW9uaWFvLmNvbS9hcGkvYXV0aC9sb2dpbiIsImlhdCI6MTQ5OTI0NjU5MSwiZXhwIjoxNDk5MzMyOTkxLCJuYmYiOjE0OTkyNDY1OTEsImp0aSI6IjlpSXRzd2thR1M4TnZRU20iLCJzdWIiOiI4In0.Wqax69FRuPjSr8NOyEboRg4fUNza8_a5eEFuykHugcQ
         */

        public String token;
    }
}
