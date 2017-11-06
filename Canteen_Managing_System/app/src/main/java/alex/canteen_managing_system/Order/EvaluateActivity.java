package alex.canteen_managing_system.Order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alex.canteen_managing_system.R;

public class EvaluateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        Button button_evaluate = (Button) findViewById(R.id.button_evaluate);
        editText = (EditText) findViewById(R.id.editText);
        button_evaluate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_evaluate:
                String inputText = editText.getText().toString();
                Toast.makeText(EvaluateActivity.this,"Evaluate successfully!",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
