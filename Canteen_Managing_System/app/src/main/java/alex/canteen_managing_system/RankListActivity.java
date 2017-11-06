package alex.canteen_managing_system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RankListActivity extends AppCompatActivity {

    private String[] rank_data = {"Employee1","Employee2","Employee3","Employee4","Employee5","Employee6","Employee7","Employee8","Employee9","Employee10",
            "Employee11","Employee12","Employee13","Employee14","Employee15","Employee16","Employee17","Employee18","Employee19","Employee20"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranklist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RankListActivity.this,android.R.layout.simple_list_item_1,rank_data);
        ListView listView = (ListView) findViewById(R.id.list_rank);
        listView.setAdapter(adapter);
    }
}