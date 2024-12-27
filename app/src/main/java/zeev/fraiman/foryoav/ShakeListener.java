package zeev.fraiman.foryoav;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 1.25f; // Порог тряски
    private static final int SHAKE_TIME_THRESHOLD = 1000; // Время между двумя событиями тряски

    private long lastShakeTime;
    private OnShakeListener listener;

    public ShakeListener() {
        lastShakeTime = System.currentTimeMillis();
    }

    public interface OnShakeListener {
        void onShake();
    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (listener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);

            if (acceleration > SHAKE_THRESHOLD) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastShakeTime > SHAKE_TIME_THRESHOLD) {
                    lastShakeTime = currentTime;
                    listener.onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
