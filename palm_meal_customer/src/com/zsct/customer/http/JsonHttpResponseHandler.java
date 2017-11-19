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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

/**
 * Used to intercept and handle the responses from requests made using {@link AsyncHttpClient}, with
 * automatic parsing into a {@link JSONObject} or {@link JSONArray}. <p>&nbsp;</p> This class is
 * designed to be passed to get, post, put and delete requests with the {@link #onSuccess(int,
 * org.apache.http.Header[], org.json.JSONArray)} or {@link #onSuccess(int,
 * org.apache.http.Header[], org.json.JSONObject)} methods anonymously overridden. <p>&nbsp;</p>
 * Additionally, you can override the other event methods from the parent class.
 */
public class JsonHttpResponseHandler extends TextHttpResponseHandler {
    private static final String LOG_TAG = "JsonHttpResponseHandler";

    /**
     * Creates new JsonHttpResponseHandler, with Json String encoding UTF-8
     */
    public JsonHttpResponseHandler() {
        super(DEFAULT_CHARSET);
    }

    /**
     * Creates new JsonHttpRespnseHandler with given Json String encoding
     *
     * @param encoding String encoding to be used when parsing JSON
     */
    public JsonHttpResponseHandler(String encoding) {
        super(encoding);
    }

    /**
     * Returns when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   parsed response if any
     */
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

    }

    /**
     * Returns when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   parsed response if any
     */
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

    }

    /**
     * Returns when request failed
     *
     * @param statusCode    http response status line
     * @param headers       response headers if any
     * @param throwable     throwable describing the way request failed
     * @param errorResponse parsed response if any
     */
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

    }

    /**
     * Returns when request failed
     *
     * @param statusCode    http response status line
     * @param headers       response headers if any
     * @param throwable     throwable describing the way request failed
     * @param errorResponse parsed response if any
     */
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {

    }

    @Override
    public final void onSuccess(final int statusCode, final Header[] headers, final byte[] responseBytes) {
        if (statusCode != HttpStatus.SC_NO_CONTENT) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Object jsonResponse = parseResponse(responseBytes);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                if (jsonResponse instanceof JSONObject) {
                                    onSuccess(statusCode, headers, (JSONObject) jsonResponse);
                                } else if (jsonResponse instanceof JSONArray) {
                                    onSuccess(statusCode, headers, (JSONArray) jsonResponse);
                                } else {
                                    onFailure(statusCode, headers, new JSONException("Unexpected response type " + jsonResponse.getClass().getName()), (JSONObject) null);
                                }

                            }
                        });
                    } catch (final JSONException ex) {
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                onFailure(statusCode, headers, ex, (JSONObject) null);
                            }
                        });
                    }
                }
            }).start();
        } else {
            onSuccess(statusCode, headers, new JSONObject());
        }
    }

    @Override
    public final void onFailure(final int statusCode, final Header[] headers, final byte[] responseBytes, final Throwable throwable) {
        if (responseBytes != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Object jsonResponse = parseResponse(responseBytes);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                if (jsonResponse instanceof JSONObject) {
                                    onFailure(statusCode, headers, throwable, (JSONObject) jsonResponse);
                                } else if (jsonResponse instanceof JSONArray) {
                                    onFailure(statusCode, headers, throwable, (JSONArray) jsonResponse);
                                } else if (jsonResponse instanceof String) {
                                    onFailure(statusCode, headers, (String) jsonResponse, throwable);
                                } else {
                                    onFailure(statusCode, headers, new JSONException("Unexpected response type " + jsonResponse.getClass().getName()), (JSONObject) null);
                                }
                            }
                        });

                    } catch (final JSONException ex) {
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                onFailure(statusCode, headers, ex, (JSONObject) null);
                            }
                        });

                    }
                }
            }).start();
        } else {
            Log.v(LOG_TAG, "response body is null, calling onFailure(Throwable, JSONObject)");
            onFailure(statusCode, headers, throwable, (JSONObject) null);
        }
    }

    /**
     * Returns Object of type {@link JSONObject}, {@link JSONArray}, String, Boolean, Integer, Long,
     * Double or {@link JSONObject#NULL}, see {@link org.json.JSONTokener#nextValue()}
     *
     * @param responseBody response bytes to be assembled in String and parsed as JSON
     * @return Object parsedResponse
     * @throws org.json.JSONException exception if thrown while parsing JSON
     */
    protected Object parseResponse(byte[] responseBody) throws JSONException {
        if (null == responseBody)
            return null;
        Object result = null;
        //trim the string to prevent start with blank, and test if the string is valid JSON, because the parser don't do this :(. If Json is not valid this will return null
        String jsonString = getResponseString(responseBody, getCharset());
        if (jsonString != null) {
            jsonString = jsonString.trim();
            if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
                result = new JSONTokener(jsonString).nextValue();
            }
        }
        if (result == null) {
            result = jsonString;
        }
        return result;
    }
}
