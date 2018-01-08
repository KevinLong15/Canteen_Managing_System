package alex.canteen_managing_system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OrderListActivity extends AppCompatActivity {

    private String[] order_data = {"Order1","Order2","Order3","Order4","Order5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrderListActivity.this,android.R.layout.simple_list_item_1,order_data);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}
