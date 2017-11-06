package alex.canteen_managing_system.Data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import alex.canteen_managing_system.Dish.Dish;

/**
 * Created by MACHENIKE on 2017/11/6.
 */

public class DishesDataOutput {
//    public void Save(ArrayList<Dish> dishes){
//        FileOutputStream out =null;
//        BufferedWriter writer =null;
//        Context context = null;
//        try {
//            out = context.openFileOutput("data", Context.MODE_PRIVATE);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public void Save(ArrayList<Dish> dishes){
        try {
            File file = new File("DishesData.txt");
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + "DishesData.txt");
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            for (Dish d:dishes) {
                try {
                    raf.write((d.Output()+"\n").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File.");
        }

//        FileWriter fw = null;
//        try {
//            fw = new FileWriter(new File("DishesData.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.d("balabala",dishes.get(0).Output());// 打印出str1
//            fw.write(dishes.get(0).Output());//////
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (Dish d:dishes) {
//            try {
//                fw.write(d.Output());//////
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return;
    }
}