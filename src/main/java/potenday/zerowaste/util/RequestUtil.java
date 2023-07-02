package potenday.zerowaste.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getAuthorizationToken(String header) {
        header = header.replace("Bearer ", "");
        // Authorization: Bearer <access_token>
        if (header == null) {
            throw new IllegalArgumentException("Invalid authorization header");
        }
        return header;
    }

    public static String getAuthorizationToken(HttpServletRequest request) {
        return getAuthorizationToken(request.getHeader("Authorization"));
    }
}