/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */

package com.zsct.customer.http;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

import android.util.Log;

/**
 * Used to intercept and handle the responses from requests made using {@link AsyncHttpClient}.
 * Receives response body as byte array with a content-type whitelist. (e.g. checks Content-Type
 * against allowed list, Content-length). <p>&nbsp;</p> For example: <p>&nbsp;</p>
 * <pre>
 * AsyncHttpClient client = new AsyncHttpClient();
 * String[] allowedTypes = new String[] { "image/png" };
 * client.get("http://www.example.com/image.png", new BinaryHttpResponseHandler(allowedTypes) {
 *     &#064;Override
 *     public void onSuccess(byte[] imageData) {
 *         // Successfully got a response
 *     }
 *
 *     &#064;Override
 *     public void onFailure(Throwable e, byte[] imageData) {
 *         // Response failed :(
 *     }
 * });
 * </pre>
 */
public abstract class BinaryHttpResponseHandler extends AsyncHttpResponseHandler {

    private static final String LOG_TAG = "BinaryHttpResponseHandler";

    private String[] mAllowedContentTypes = new String[]{
            "image/jpeg",
            "image/png"
    };

    /**
     * Method can be overriden to return allowed content types, can be sometimes better than passing
     * data in constructor
     *
     * @return array of content-types or Pattern string templates (eg. '.*' to match every response)
     */
    public String[] getAllowedContentTypes() {
        return mAllowedContentTypes;
    }

    /**
     * Creates a new BinaryHttpResponseHandler
     */
    public BinaryHttpResponseHandler() {
        super();
    }

    /**
     * Creates a new BinaryHttpResponseHandler, and overrides the default allowed content types with
     * passed String array (hopefully) of content types.
     *
     * @param allowedContentTypes content types array, eg. 'image/jpeg' or pattern '.*'
     */
    public BinaryHttpResponseHandler(String[] allowedContentTypes) {
        super();
        if (allowedContentTypes != null)
            mAllowedContentTypes = allowedContentTypes;
        else
            Log.e(LOG_TAG, "Constructor passed allowedContentTypes was null !");
    }

    @Override
    public abstract void onSuccess(int statusCode, Header[] headers, byte[] binaryData);

    @Override
    public abstract void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error);

    @Override
    public final void sendResponseMessage(HttpResponse response) throws IOException {
        StatusLine status = response.getStatusLine();
        Header[] contentTypeHeaders = response.getHeaders("Content-Type");
        if (contentTypeHeaders.length != 1) {
            //malformed/ambiguous HTTP Header, ABORT!
            sendFailureMessage(status.getStatusCode(), response.getAllHeaders(), null, new HttpResponseException(status.getStatusCode(), "None, or more than one, Content-Type Header found!"));
            return;
        }
        Header contentTypeHeader = contentTypeHeaders[0];
        boolean foundAllowedContentType = false;
        for (String anAllowedContentType : getAllowedContentTypes()) {
            try {
                if (Pattern.matches(anAllowedContentType, contentTypeHeader.getValue())) {
                    foundAllowedContentType = true;
                }
            } catch (PatternSyntaxException e) {
                Log.e("BinaryHttpResponseHandler", "Given pattern is not valid: " + anAllowedContentType, e);
            }
        }
        if (!foundAllowedContentType) {
            //Content-Type not in allowed list, ABORT!
            sendFailureMessage(status.getStatusCode(), response.getAllHeaders(), null, new HttpResponseException(status.getStatusCode(), "Content-Type not allowed!"));
            return;
        }
        super.sendResponseMessage(response);
    }
}
