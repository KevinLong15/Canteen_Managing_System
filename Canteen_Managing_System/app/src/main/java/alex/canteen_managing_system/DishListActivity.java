package alex.canteen_managing_system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DishListActivity extends AppCompatActivity {

    private String[] data = {"Dish1","Dish2","Dish3","Dish4","Dish5","Dish6","Dish7","Dish8","Dish9","Dish10","Dish11","Dish12","Dish13","Dish14",
            "Dish15","Dish16","Dish17","Dish18","Dish19","Dish20"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishlist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DishListActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}
