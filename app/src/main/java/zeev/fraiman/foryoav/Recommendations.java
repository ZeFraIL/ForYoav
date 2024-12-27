package zeev.fraiman.foryoav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Recommendations extends AppCompatActivity {

    Context context;
    Button bGoAnn, bShakeSensor, bBRs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        initComponents();

        bGoAnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(context, Announcement.class);
                startActivity(go);
            }
        });

        bShakeSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(context, ShakeSensor.class);
                startActivity(go);
            }
        });

        bBRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(context, ManyBroadcastReceivers.class);
                startActivity(go);
            }
        });
    }

    private void initComponents() {
        context=this;
        bGoAnn= (Button) findViewById(R.id.bGoAnn);
        bShakeSensor= (Button) findViewById(R.id.bShakeSensor);
        bBRs= (Button) findViewById(R.id.bBRs);
    }
}