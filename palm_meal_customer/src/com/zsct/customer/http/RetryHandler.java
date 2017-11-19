/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */

/*
    Some of the retry logic in this class is heavily borrowed from the
    fantastic droid-fu project: https://github.com/donnfelker/droid-fu
*/

package com.zsct.customer.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;

import javax.net.ssl.SSLException;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import android.os.SystemClock;

class RetryHandler implements HttpRequestRetryHandler {
    private static HashSet<Class<?>> exceptionWhitelist = new HashSet<Class<?>>();
    private static HashSet<Class<?>> exceptionBlacklist = new HashSet<Class<?>>();

    static {
        // Retry if the server dropped connection on us
        exceptionWhitelist.add(NoHttpResponseException.class);
        // retry-this, since it may happens as part of a Wi-Fi to 3G failover
        exceptionWhitelist.add(UnknownHostException.class);
        // retry-this, since it may happens as part of a Wi-Fi to 3G failover
        exceptionWhitelist.add(SocketException.class);

        // never retry timeouts
        exceptionBlacklist.add(InterruptedIOException.class);
        // never retry SSL handshake failures
        exceptionBlacklist.add(SSLException.class);
    }

    private final int maxRetries;
    private final int retrySleepTimeMS;

    public RetryHandler(int maxRetries, int retrySleepTimeMS) {
        this.maxRetries = maxRetries;
        this.retrySleepTimeMS = retrySleepTimeMS;
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean retry = true;

        Boolean b = (Boolean) context.getAttribute(ExecutionContext.HTTP_REQ_SENT);
        boolean sent = (b != null && b);

        if (executionCount > maxRetries) {
            // Do not retry if over max retry count
            retry = false;
        } else if (isInList(exceptionWhitelist, exception)) {
            // immediately retry if error is whitelisted
            retry = true;
        } else if (isInList(exceptionBlacklist, exception)) {
            // immediately cancel retry if the error is blacklisted
            retry = false;
        } else if (!sent) {
            // for most other errors, retry only if request hasn't been fully sent yet
            retry = true;
        }

        if (retry) {
            // resend all idempotent requests
            HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            if (currentReq == null) {
                return false;
            }
        }

        if (retry) {
            SystemClock.sleep(retrySleepTimeMS);
        } else {
            exception.printStackTrace();
        }

        return retry;
    }

    static void addClassToWhitelist(Class<?> cls) {
        exceptionWhitelist.add(cls);
    }

    static void addClassToBlacklist(Class<?> cls) {
        exceptionBlacklist.add(cls);
    }

    protected boolean isInList(HashSet<Class<?>> list, Throwable error) {
        for (Class<?> aList : list) {
            if (aList.isInstance(error)) {
                return true;
            }
        }
        return false;
    }
}
