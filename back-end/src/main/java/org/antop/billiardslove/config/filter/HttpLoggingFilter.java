package org.antop.billiardslove.config.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Enumeration;

@Slf4j(topic = "http.logging")
public class HttpLoggingFilter extends OncePerRequestFilter {
    /**
     * 뽑아낼 해더명들
     */
    private static final String[] HEADERS = new String[]{
            "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"
    };
    /**
     * 알 수 없음 플래그
     */
    public static final String UNKNOWN_REMOTE = "unknown";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (!log.isDebugEnabled() || !request.getRequestURI().startsWith("/api")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.debug(""); // new line

        long startTime = System.currentTimeMillis();
        String clientIp = request.getRemoteAddr();
        String realClientIp = getRealClientIp(request);

        if (StringUtils.equals(realClientIp, clientIp)) {
            log.debug("Request: {} → {} {}", realClientIp, request.getMethod(), request.getRequestURL());
        } else {
            log.debug("Request: {} → {} → {} {}",
                    realClientIp,
                    clientIp,
                    request.getMethod(),
                    request.getRequestURL());
        }

        printSession(request.getSession());
        printHeader(request);
        printParameter(request);

        ReadableRequestBodyWrapper wrappedRequest = new ReadableRequestBodyWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        printRequestBody(wrappedRequest);

        filterChain.doFilter(wrappedRequest, wrappedResponse);
        long duration = System.currentTimeMillis() - startTime;

        log.debug("Returned status={} in {}ms, charset={}",
                response.getStatus(),
                duration,
                response.getCharacterEncoding());

        printResponseBody(wrappedResponse);
        // IMPORTANT: copy content of response back into original response
        wrappedResponse.copyBodyToResponse();
    }

    /**
     * 응답 내용을 출력한다. JSON만 출력한다.
     *
     * @param response {@link ContentCachingResponseWrapper}
     */
    private void printResponseBody(ContentCachingResponseWrapper response) {
        String json = "[EMPTY]";
        byte[] buf = response.getContentAsByteArray();
        if (buf.length != 0) {
            try {
                json = new String(buf, 0, buf.length, StandardCharsets.UTF_8);
                json = System.lineSeparator() + gson.toJson(gson.fromJson(json, JsonElement.class));
            } catch (JsonSyntaxException e) {
                json = "[NOT JSON]";
            }
        }
        log.debug("Response Body: {}", json);
    }

    /**
     * 요청 내용을 출력한다.<br>
     * 1. JSON 일경우 JSON 출력<br>
     * 2. JSON이 아닐 경우 그대로 출력
     *
     * @param request JSON String
     */
    private void printRequestBody(ReadableRequestBodyWrapper request) {
        String json = "[EMPTY]";
        String body = request.getRequestBody();
        if (StringUtils.isNotBlank(body)) {
            try {
                json = System.lineSeparator() + gson.toJson(gson.fromJson(body, JsonElement.class));
            } catch (JsonSyntaxException e) {
                json = body;
            }
        }
        log.debug("Request body: {}", json);
    }

    private void printParameter(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            log.debug("parameter[{}] = {}", name, request.getParameterValues(name));
        }
    }

    private void printHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            log.debug("header[{}] = {}", name, request.getHeader(name));
        }
    }

    /**
     * 세션 내용을 출력한다.
     *
     * @param session {@link HttpSession}
     */
    private void printSession(HttpSession session) {
        if (session == null) {
            log.debug("session is null.");
            return;
        }
        LocalDateTime created =
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(session.getCreationTime()), ZoneId.systemDefault());

        log.debug("session id = {}, created={}, interval={}s",
                session.getId(),
                created,
                session.getMaxInactiveInterval());

        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            log.debug("session[{}] = {}", name, session.getAttribute(name));
        }
    }

    /**
     * 웹서버를 타고 들어오는 경우 실제 클라이언트의 IP를 알아낸다.<br>
     * 웹서버 쪽에서도 설정이 되어 있어야 한다.
     *
     * @param request {@link HttpServletRequest}
     * @return 실제 클라이언트 아이피
     */
    private String getRealClientIp(HttpServletRequest request) {
        for (String header : HEADERS) {
            String ip = request.getHeader(header);
            if (StringUtils.isNotBlank(ip) && StringUtils.equalsIgnoreCase(ip, UNKNOWN_REMOTE)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

}
