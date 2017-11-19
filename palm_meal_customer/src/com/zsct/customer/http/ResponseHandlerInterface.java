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
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

/**
 * Interface to standardize implementations
 */
public interface ResponseHandlerInterface {

    /**
     * Returns data whether request completed successfully
     *
     * @param response HttpResponse object with data
     * @throws java.io.IOException if retrieving data from response fails
     */
    void sendResponseMessage(HttpResponse response) throws IOException;

    /**
     * Notifies callback, that request started execution
     */
    void sendStartMessage();

    /**
     * Notifies callback, that request was completed and is being removed from thread pool
     */
    void sendFinishMessage();

    /**
     * Notifies callback, that request (mainly uploading) has progressed
     *
     * @param bytesWritten number of written bytes
     * @param bytesTotal   number of total bytes to be written
     */
    void sendProgressMessage(int bytesWritten, int bytesTotal);

    /**
     * Notifies callback, that request was cancelled
     */
    void sendCancelMessage();

    /**
     * Notifies callback, that request was handled successfully
     *
     * @param statusCode   HTTP status code
     * @param headers      returned headers
     * @param responseBody returned data
     */
    void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBody);

    /**
     * Returns if request was completed with error code or failure of implementation
     *
     * @param statusCode   returned HTTP status code
     * @param headers      returned headers
     * @param responseBody returned data
     * @param error        cause of request failure
     */
    void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable error);

    /**
     * Notifies callback of retrying request
     *
     * @param retryNo number of retry within one request
     */
    void sendRetryMessage(int retryNo);

    /**
     * Returns URI which was used to request
     *
     * @return uri of origin request
     */
    public URI getRequestURI();

    /**
     * Returns Header[] which were used to request
     *
     * @return headers from origin request
     */
    public Header[] getRequestHeaders();

    /**
     * Helper for handlers to receive Request URI info
     *
     * @param requestURI claimed request URI
     */
    public void setRequestURI(URI requestURI);

    /**
     * Helper for handlers to receive Request Header[] info
     *
     * @param requestHeaders Headers, claimed to be from original request
     */
    public void setRequestHeaders(Header[] requestHeaders);

    /**
     * Can set, whether the handler should be asynchronous or synchronous
     *
     * @param useSynchronousMode whether data should be handled on background Thread on UI Thread
     */
    void setUseSynchronousMode(boolean useSynchronousMode);

    /**
     * Can set, whether the handler should be asynchronous or synchronous
     *
     * @return boolean if the ResponseHandler is running in synchronous mode
     */
    boolean getUseSynchronousMode();
}
