package alex.canteen_managing_system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alex.canteen_managing_system.R;

public class ReportSalesActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales);
        Button button_evaluate = (Button) findViewById(R.id.button_submit);
        editText1 = (EditText) findViewById(R.id.edit_income);
        editText2 = (EditText) findViewById(R.id.edit_outcome);
        button_evaluate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_submit:
                //below get the income and outcome!!!
                String income = editText1.getText().toString();
                String outcome = editText2.getText().toString();

                Toast.makeText(ReportSalesActivity.this,"Report successfully!",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
