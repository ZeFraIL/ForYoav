package zeev.fraiman.foryoav;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;
import android.provider.Settings;

public class Announcement extends AppCompatActivity {

    Context context;
    EditText etAnn;
    Button bSandW, bSTTSet;
    ActivityResultLauncher<Intent> speechRecognitionLauncher;
    ActivityResultLauncher<Intent> settingsLauncher; // Новый запуск для настроек
    private Locale selectedLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                1);

        initComponents();

        bSandW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechRecognition();
            }
        });

        bSTTSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Используйте settingsLauncher вместо startActivityForResult
                Intent intent = new Intent(Settings.ACTION_VOICE_INPUT_SETTINGS);
                settingsLauncher.launch(intent);
            }
        });
    }

    private void initComponents() {
        context = this;
        etAnn = (EditText) findViewById(R.id.etAnn);
        bSandW = (Button) findViewById(R.id.bSandW);
        bSTTSet = (Button) findViewById(R.id.bSTTSet);

        // Инициализация speechRecognitionLauncher
        speechRecognitionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        handleSpeechRecognitionResult(result.getResultCode(), data);
                    }
                }
        );

        // Инициализация settingsLauncher
        settingsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Обработка результата из активности настроек
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            selectedLocale = Locale.getDefault();
                            updateSpeechRecognizerLocale(selectedLocale);
                        }
                    }
                }
        );
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Скажите что-нибудь...");

        try {
            speechRecognitionLauncher.launch(intent);
        } catch (ActivityNotFoundException e) {
            // Обработка отсутствия распознавания речи
        }
    }

    private void handleSpeechRecognitionResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (results != null && !results.isEmpty()) {
                    String recognizedText = results.get(0);
                    etAnn.setText(recognizedText);
                }
            }
        }
    }

    private void updateSpeechRecognizerLocale(Locale locale) {
        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale);
        startSpeechRecognition();
    }
}
