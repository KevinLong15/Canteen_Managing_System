/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;
import android.util.Log;


public abstract class FileAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

    private File mFile;
    private static final String LOG_TAG = "FileAsyncHttpResponseHandler";

    /**
     * Obtains new FileAsyncHttpResponseHandler and stores response in passed file
     *
     * @param file File to store response within, must not be null
     */
    public FileAsyncHttpResponseHandler(File file) {
        super();
        assert (file != null);
        this.mFile = file;
    }

    /**
     * Obtains new FileAsyncHttpResponseHandler against context with target being temporary file
     *
     * @param context Context, must not be null
     */
    public FileAsyncHttpResponseHandler(Context context) {
        super();
        this.mFile = getTemporaryFile(context);
    }

    /**
     * Attempts to delete file with stored response
     *
     * @return false if the file does not exist or is null, true if it was successfully deleted
     */
    public boolean deleteTargetFile() {
        return getTargetFile() != null && getTargetFile().delete();
    }

    /**
     * Used when there is no file to be used when calling constructor
     *
     * @param context Context, must not be null
     * @return temporary file or null if creating file failed
     */
    protected File getTemporaryFile(Context context) {
        assert (context != null);
        try {
            return File.createTempFile("temp_", "_handled", context.getCacheDir());
        } catch (Throwable t) {
            Log.e(LOG_TAG, "Cannot create temporary file", t);
        }
        return null;
    }

    /**
     * Retrieves File object in which the response is stored
     *
     * @return File file in which the response is stored
     */
    protected File getTargetFile() {
        assert (mFile != null);
        return mFile;
    }

    @Override
    public final void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        onFailure(statusCode, headers, throwable, getTargetFile());
    }

    /**
     * Method to be overriden, receives as much of file as possible Called when the file is
     * considered failure or if there is error when retrieving file
     *
     * @param statusCode http file status line
     * @param headers    file http headers if any
     * @param throwable  returned throwable
     * @param file       file in which the file is stored
     */
    public abstract void onFailure(int statusCode, Header[] headers, Throwable throwable, File file);

    @Override
    public final void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
        onSuccess(statusCode, headers, getTargetFile());
    }

    /**
     * Method to be overriden, receives as much of response as possible
     *
     * @param statusCode http response status line
     * @param headers    response http headers if any
     * @param file       file in which the response is stored
     */
    public abstract void onSuccess(int statusCode, Header[] headers, File file);

    @Override
    protected byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream instream = entity.getContent();
            long contentLength = entity.getContentLength();
            FileOutputStream buffer = new FileOutputStream(getTargetFile());
            if (instream != null) {
                try {
                    byte[] tmp = new byte[BUFFER_SIZE];
                    int l, count = 0;
                    // do not send messages if request has been cancelled
                    while ((l = instream.read(tmp)) != -1 && !Thread.currentThread().isInterrupted()) {
                        count += l;
                        buffer.write(tmp, 0, l);
                        sendProgressMessage(count, (int) contentLength);
                    }
                } finally {
                    instream.close();
                    buffer.flush();
                    buffer.close();
                }
            }
        }
        return null;
    }

}
