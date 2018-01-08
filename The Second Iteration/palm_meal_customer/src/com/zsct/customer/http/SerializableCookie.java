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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * A wrapper class around {@link Cookie} and/or {@link BasicClientCookie} designed for use in {@link
 * PersistentCookieStore}.
 */
public class SerializableCookie implements Serializable {
    private static final long serialVersionUID = 6374381828722046732L;

    private transient final Cookie cookie;
    private transient BasicClientCookie clientCookie;

    public SerializableCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        Cookie bestCookie = cookie;
        if (clientCookie != null) {
            bestCookie = clientCookie;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(cookie.getName());
        out.writeObject(cookie.getValue());
        out.writeObject(cookie.getComment());
        out.writeObject(cookie.getDomain());
        out.writeObject(cookie.getExpiryDate());
        out.writeObject(cookie.getPath());
        out.writeInt(cookie.getVersion());
        out.writeBoolean(cookie.isSecure());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        clientCookie = new BasicClientCookie(name, value);
        clientCookie.setComment((String) in.readObject());
        clientCookie.setDomain((String) in.readObject());
        clientCookie.setExpiryDate((Date) in.readObject());
        clientCookie.setPath((String) in.readObject());
        clientCookie.setVersion(in.readInt());
        clientCookie.setSecure(in.readBoolean());
    }
}