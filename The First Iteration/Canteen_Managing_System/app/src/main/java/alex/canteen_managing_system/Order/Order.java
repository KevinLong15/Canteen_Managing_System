package alex.canteen_managing_system.Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alex on 2017/11/6.
 */

public class Order {
    protected int orderID;
    protected int customID;
    protected int [] dishesID;
    protected int price;
    protected Date orderTime;
    protected Date expectedTime;
}

//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间