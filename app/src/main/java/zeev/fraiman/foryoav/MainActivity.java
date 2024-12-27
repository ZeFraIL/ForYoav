package zeev.fraiman.foryoav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Context context;
    CountDownTimer countDownTimer;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        Intent goService=new Intent(context, TextToSpeechService.class);
        goService.putExtra("text","Hello, Yoav, it's for you");
        startService(goService);


    countDownTimer=new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                goNext();
            }
        }.start();

        tv= (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                goNext();
            }
        });
    }

    private void goNext() {
        Intent go=new Intent(context, Recommendations.class);
        startActivity(go);
    }
}