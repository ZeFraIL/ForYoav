package zeev.fraiman.foryoav;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class TextToSpeechService extends Service {

    TextToSpeech tts;
    int language;
    String stSpeak="";

    public TextToSpeechService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stSpeak=intent.getStringExtra("text");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initializeTextToSpeech(stSpeak);
                //TextToSpeechHelper.speak(st);
            }
        });
        thread.start();
        return START_STICKY;
    }

    private void initializeTextToSpeech(final String stSpeak) {
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(TextToSpeechService.this, "Language is not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        tts.speak(stSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    Toast.makeText(TextToSpeechService.this, "Initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}