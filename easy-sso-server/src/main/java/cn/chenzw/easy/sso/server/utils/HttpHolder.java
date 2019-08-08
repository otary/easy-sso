package cn.chenzw.easy.sso.server.utils;

import cn.chenzw.toolkit.commons.ClassExtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenzw
 */
public class HttpHolder {

    private HttpHolder() {
    }

    private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<>();
    private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<>();

    private static final boolean SPRING_FREMAE_PRESENT = ClassExtUtils
            .isPresent("org.springframework.web.context.request.RequestContextHolder");

    public static void init(HttpServletRequest request, HttpServletResponse response) {
        REQUEST.set(request);
        RESPONSE.set(response);
    }

    public static HttpServletRequest getRequest() {
        if (REQUEST.get() == null) {
            return getRequestInternal();
        }
        return REQUEST.get();
    }

    public static HttpServletResponse getResponse() {
        if (RESPONSE.get() == null) {
            return getResponseInternal();
        }
        return RESPONSE.get();
    }

    private static HttpServletRequest getRequestInternal() {
        if (SPRING_FREMAE_PRESENT) {
            return ((org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder
                    .getRequestAttributes()).getRequest();
        }
        return null;
    }

    private static HttpServletResponse getResponseInternal() {
        if (SPRING_FREMAE_PRESENT) {
            return new org.springframework.web.context.request.ServletWebRequest(getRequestInternal()).getResponse();
        }
        return null;
    }
}
