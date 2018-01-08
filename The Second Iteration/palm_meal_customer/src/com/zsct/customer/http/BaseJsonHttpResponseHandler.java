/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */

package com.zsct.customer.http;

import org.apache.http.Header;
import org.apache.http.HttpStatus;

import android.util.Log;

/**
 * Class meant to be used with custom JSON parser (such as GSON or Jackson JSON) <p>&nbsp;</p>
 * {@link #parseResponse(String, boolean)} should be overriden and must return type of generic param
 * class, response will be then handled to implementation of abstract methods {@link #onSuccess(int,
 * org.apache.http.Header[], String, Object)} or {@link #onFailure(int, org.apache.http.Header[],
 * Throwable, String, Object)}, depending of response HTTP status line (result http code)
 */
public abstract class BaseJsonHttpResponseHandler<JSON_TYPE> extends TextHttpResponseHandler {
    private static final String LOG_TAG = "BaseJsonHttpResponseHandler";

    /**
     * Creates a new JsonHttpResponseHandler with default charset "UTF-8"
     */
    public BaseJsonHttpResponseHandler() {
        this(DEFAULT_CHARSET);
    }

    /**
     * Creates a new JsonHttpResponseHandler with given string encoding
     *
     * @param encoding result string encoding, see <a href="http://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Charset</a>
     */
    public BaseJsonHttpResponseHandler(String encoding) {
        super(encoding);
    }

    /**
     * Base abstract method, handling defined generic type
     *
     * @param statusCode      HTTP status line
     * @param headers         response headers
     * @param rawJsonResponse string of response, can be null
     * @param response        response returned by {@link #parseResponse(String, boolean)}
     */
    public abstract void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSON_TYPE response);

    /**
     * Base abstract method, handling defined generic type
     *
     * @param statusCode    HTTP status line
     * @param headers       response headers
     * @param throwable     error thrown while processing request
     * @param rawJsonData   raw string data returned if any
     * @param errorResponse response returned by {@link #parseResponse(String, boolean)}
     */
    public abstract void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSON_TYPE errorResponse);

    @Override
    public final void onSuccess(final int statusCode, final Header[] headers, final String responseString) {
        if (statusCode != HttpStatus.SC_NO_CONTENT) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final JSON_TYPE jsonResponse = parseResponse(responseString, false);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                onSuccess(statusCode, headers, responseString, jsonResponse);
                            }
                        });
                    } catch (final Throwable t) {
                        Log.d(LOG_TAG, "parseResponse thrown an problem", t);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                onFailure(statusCode, headers, t, responseString, null);
                            }
                        });
                    }
                }
            }).start();
        } else {
            onSuccess(statusCode, headers, null, null);
        }
    }

    @Override
    public final void onFailure(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable) {
        if (responseString != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final JSON_TYPE jsonResponse = parseResponse(responseString, true);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                onFailure(statusCode, headers, throwable, responseString, jsonResponse);
                            }
                        });
                    } catch (Throwable t) {
                        Log.d(LOG_TAG, "parseResponse thrown an problem", t);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                onFailure(statusCode, headers, throwable, responseString, null);
                            }
                        });
                    }
                }
            }).start();
        } else {
            onFailure(statusCode, headers, throwable, null, null);
        }
    }

    /**
     * Should return deserialized instance of generic type, may return object for more vague
     * handling
     *
     * @param rawJsonData response string, may be null
     * @param isFailure   indicating if this method is called from onFailure or not
     * @return object of generic type or possibly null if you choose so
     * @throws Throwable allows you to throw anything from within deserializing JSON response
     */
    protected abstract JSON_TYPE parseResponse(String rawJsonData, boolean isFailure) throws Throwable;
}
