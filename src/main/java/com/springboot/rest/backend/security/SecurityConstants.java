package com.springboot.rest.backend.security;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/auth";

    // Signing key for HS512 algorithm by "openssl rand -base64 172 | tr -d '\n'"
    public static final String JWT_SECRET = "9TN4khseqwp7h8700X8UMHI/nFC/wMJCMTrvqGX2VeUBkeIQxtt0lncnY34KZcrJL9ffpEGcnAGfY97KsgkwMYEqeszuNSI0/xnWS9izWiiOHBcVM8dq/w4DaPnUOiuZeHyFY2sPFhoUMK6Ggr07kFQj+lHU+5t+GBu9DfOmKLX0FpF0YK+KnNUtEOtx0Lpk3V2BbBemWDxM0mbNtbVN0HenrgZMgVI/N9tK3A==%";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final Integer EXPIRATION_TIME = 864000000;

}