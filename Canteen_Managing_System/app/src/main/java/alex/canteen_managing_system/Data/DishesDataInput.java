package alex.canteen_managing_system.Data;

import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import alex.canteen_managing_system.Dish.Dish;
import alex.canteen_managing_system.Order.Order;

/**
 * Created by MACHENIKE on 2017/11/6.
 */

public class DishesDataInput {

    public ArrayList<Order> Read() {
//        FileInputStream in = null;
//        BufferedReader reader = null;
//        StringBuilder content = new StringBuilder();
//        try {
//            in = openFileInput("data");
//            reader = new BufferedReader((new InputStreamReader(in)));
//            String line = "";
//            try {
//                while ((line = reader.readLine()) != null) {
//                    content.append(line);
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }catch (IOException e) {
//            e.printStackTrace();
//        }



////        String path = "DishesData.txt";
////        String content = ""; //文件内容字符串
////        //打开文件
////        File file = new File(path);
////        //如果path是传递过来的参数，可以做一个非目录的判断
//
//        String fileName = "DishesData.txt";//文件路径
//        // 也可以用String fileName = "mnt/sdcard/Y.txt";
//        String res = "";
//        Log.d("balabala","haha");
//        try {
//            FileInputStream fin = new FileInputStream(fileName);
//            // FileInputStream fin = openFileInput(fileName);
//            // 用这个就不行了，必须用FileInputStream
//            int length = fin.available();
//            Log.d("balabala", "1");
//            Log.d("balabala", String.valueOf(length));
//            byte[] buffer = new byte[length];
//            fin.read(buffer);
//            res = buffer.toString();
//            fin.close();//关闭资源
//            Log.d("balabala", res);
//        } catch (Exception e) {
//            Log.d("balabala","cao");
//        }


//        RandomAccessFile raf = null;
//        try {
//            raf = new RandomAccessFile(file, "rw");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            raf.seek(file.length());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String line;
//        //分行读取
//        try {
//            while (( line = raf.readLine()) != null) {
//                content += line + "\n";
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            raf.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (file.isDirectory())
//        {
//            Log.d("balabala", "The File doesn't not exist.");
//        }
//        else
//        {
//            try {
//                InputStream instream = new FileInputStream(new File("DishesData.txt"));
//                if (instream != null)
//                {
//                    InputStreamReader inputreader = new InputStreamReader(instream);
//                    BufferedReader buffreader = new BufferedReader(inputreader);
//                    String line;
//                    //分行读取
//                    while (( line = buffreader.readLine()) != null) {
//                        content += line + "\n";
//                    }
//                    instream.close();
//                }
//            }
//            catch (java.io.FileNotFoundException e)
//            {
//                Log.d("balabala", "The File doesn't exist.");
//            }
//                catch (IOException e)
//            {
//                Log.d("balabala", e.getMessage());
//            }
//    }
        return null;
    }
}
