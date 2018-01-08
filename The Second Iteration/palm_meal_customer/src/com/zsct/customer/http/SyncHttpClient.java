/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.http;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import android.content.Context;

/**
 * Processes http requests in synchronous mode, so your caller thread will be blocked on each
 * request
 *
 * @see com.loopj.android.http.AsyncHttpClient
 */
public class SyncHttpClient extends AsyncHttpClient {

    /**
     * Creates a new SyncHttpClient with default constructor arguments values
     */
    public SyncHttpClient() {
        super(false, 80, 443);
    }

    /**
     * Creates a new SyncHttpClient.
     *
     * @param httpPort non-standard HTTP-only port
     */
    public SyncHttpClient(int httpPort) {
        super(false, httpPort, 443);
    }

    /**
     * Creates a new SyncHttpClient.
     *
     * @param httpPort  non-standard HTTP-only port
     * @param httpsPort non-standard HTTPS-only port
     */
    public SyncHttpClient(int httpPort, int httpsPort) {
        super(false, httpPort, httpsPort);
    }

    /**
     * Creates new SyncHttpClient using given params
     *
     * @param fixNoHttpResponseException Whether to fix or not issue, by ommiting SSL verification
     * @param httpPort                   HTTP port to be used, must be greater than 0
     * @param httpsPort                  HTTPS port to be used, must be greater than 0
     */
    public SyncHttpClient(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        super(fixNoHttpResponseException, httpPort, httpsPort);
    }

    /**
     * Creates a new SyncHttpClient.
     *
     * @param schemeRegistry SchemeRegistry to be used
     */
    public SyncHttpClient(SchemeRegistry schemeRegistry) {
        super(schemeRegistry);
    }

    @Override
    protected RequestHandle sendRequest(DefaultHttpClient client,
                                        HttpContext httpContext, HttpUriRequest uriRequest,
                                        String contentType, ResponseHandlerInterface responseHandler,
                                        Context context) {
        if (contentType != null) {
            uriRequest.addHeader("Content-Type", contentType);
        }

        responseHandler.setUseSynchronousMode(true);

		/*
         * will execute the request directly
		*/
        new AsyncHttpRequest(client, httpContext, uriRequest, responseHandler).run();

        // Return a Request Handle that cannot be used to cancel the request
        // because it is already complete by the time this returns
        return new RequestHandle(null);
    }
}
