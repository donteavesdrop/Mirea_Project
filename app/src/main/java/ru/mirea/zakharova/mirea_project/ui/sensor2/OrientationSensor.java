package ru.mirea.zakharova.mirea_project.ui.sensor2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.zakharova.mirea_project.R;
public class OrientationSensor extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor orientationSensor;

    private TextView rollTextView;
    private TextView pitchTextView;
    private TextView yawTextView;
    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orientation_sensor, container, false);
        textView = rootView.findViewById(R.id.textView6);
        rollTextView = rootView.findViewById(R.id.roll_text_view);
        pitchTextView = rootView.findViewById(R.id.pitch_text_view);
        yawTextView = rootView.findViewById(R.id.yaw_text_view);
        textView.setText("Объяснение терминов:\n" +
                "\n" +
                "Угол крена: Угол крена представляет собой поворот вокруг оси X устройства. Он указывает на величину наклона или поворота из стороны в сторону. Положительный угол крена означает, что устройство наклонено в правую сторону, а отрицательный - в левую.\n" +
                "\n" +
                "Угол продольного наклона: Угол продольного наклона представляет собой поворот устройства вокруг оси Y. Он указывает на величину наклона или поворота спереди назад. Положительный угол наклона означает, что устройство наклонено в сторону пользователя (экраном вниз), а отрицательный угол наклона означает, что устройство наклонено в сторону от пользователя (экраном вверх).\n" +
                "\n" +
                "Угол рысканья: Угол рысканья представляет собой поворот вокруг оси Z устройства. Он указывает на величину поворота в горизонтальной плоскости. По отношению к смартфону или планшету, удерживаемому в портретной ориентации, положительный угол рысканья означает, что устройство повернуто по часовой стрелке, а отрицательный угол рысканья - что оно повернуто против часовой стрелки.\n" +
                "\n" +
                "Эти углы измеряются в градусах и предоставляют информацию об ориентации и повороте устройства в трехмерном пространстве.\n" +
                "\n" +
                "Эти значения могут быть полезны в различных приложениях и сценариях. Вот несколько примеров:\n" +
                "\n" +
                "1. Игры, основанные на ориентации: Углы крена, тангажа и рысканья можно использовать для управления движением и ориентацией объектов в игре. Например, наклон устройства может быть преобразован в наклон игрового персонажа или управление движением транспортного средства.\n" +
                "\n" +
                "2. Дополненная реальность (AR): В приложениях AR ориентация устройства может использоваться для точного наложения виртуальных объектов на реальный мир. Комбинируя ориентацию устройства с другими датчиками, такими как акселерометр, можно добиться точного позиционирования и выравнивания виртуальных объектов.\n" +
                "\n" +
                "3. Виртуальная реальность (VR): Приложения VR в значительной степени зависят от движений головы пользователя для получения эффекта погружения. Углы крена, тангажа и рысканья могут использоваться для отслеживания и интерпретации ориентации головы пользователя, позволяя ему осматриваться и взаимодействовать с виртуальной средой.\n" +
                "\n" +
                "4. Навигация и картография: Отслеживая ориентацию устройства, можно определить направление, в котором оно направлено. Эта информация может быть использована для навигационных целей, например, для применения компаса, поворота карты и предоставления ориентиров.\n" +
                "\n" +
                "5. Робототехника и беспилотники: Углы крена, тангажа и рысканья имеют решающее значение для стабилизации и управления движением роботизированных систем и беспилотников. Они помогают поддерживать баланс, корректировать ориентацию и контролировать динамику полета.\n" +
                "\n" +
                "Это лишь несколько примеров, а применение значений ориентации распространяется на различные области, включая игры, развлечения, навигацию, виртуальную реальность, робототехнику и многое другое. Точное использование зависит от конкретных требований и контекста разрабатываемого приложения или системы." +
                "");

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float roll = event.values[2];
            float pitch = event.values[1];
            float yaw = event.values[0];

            rollTextView.setText("Roll Angle: " + String.valueOf(roll));
            pitchTextView.setText("Pitch Angle: " + String.valueOf(pitch));
            yawTextView.setText("Yaw Angle: " + String.valueOf(yaw));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No implementation required, but must be present
    }
}
