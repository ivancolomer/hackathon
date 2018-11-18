package com.ibar.protectme;

import java.util.Map;

public class Config {
    public static final String SERVER_IP = "192.168.4.1";

    private static String SessionId = "";
    private static long UserId = 0;

    public static void setLogIn(long userid, String sessionid) {
        SessionId = sessionid;
        UserId = userid;
    }

    public static boolean hasLogged(){
        return !SessionId.isEmpty() && UserId > 0;
    }

    public static void putLogInParameters(Map<String, String> map) {
        map.put("user_id", String.valueOf(UserId));
        map.put("session_id", SessionId);
    }

}
