package alex.canteen_managing_system;

import android.content.Intent;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import alex.canteen_managing_system.Order.EvaluateActivity;

public class CustomerMainUI extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent intent5 = new Intent(CustomerMainUI.this,DishListActivity.class);
                    startActivity(intent5);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_custom_main_ui, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "settings waited to be added", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_evaluate:
                //intent to evaluate
                Intent intent_evaluate = new Intent(CustomerMainUI.this,EvaluateActivity.class);
                startActivity(intent_evaluate);
                break;
            case R.id.action_order:
                //intent to order
                Intent intent_order = new Intent(CustomerMainUI.this,DishListActivity.class);
                startActivity(intent_order);
                break;
            case R.id.action_logout:
                Toast.makeText(this, "Log out successfully", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main_ui);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
