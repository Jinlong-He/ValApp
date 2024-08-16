package com.example.validateactivitysemantics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class SIT_2 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private RadioGroup lmRg;
    private RadioGroup aftRg;
    private RadioGroup finishRg;

    private CheckBox flagStpCkb;
    private CheckBox flagCtpCkb;
    private CheckBox flagCtkCkb;
    private CheckBox flagRtfCkb;
    private CheckBox flagNtkCkb;
    private CheckBox flagMtkCkb;
    private CheckBox flagTohCkb;
    private CheckBox flagRtnCkb;
    private CheckBox flagPitCkb;
    private CheckBox flagNdmCkb;
    private CheckBox flagNhtCkb;

    private Button startActivityBt;
    private TextView tv;
    private TextView tvTasks;

    private String launchMode = "";
    private String taskAffinity = "";
    private Set<String> intentFlags = new HashSet<>();


    protected void getIntentFlags(Intent intent) {
        if (intentFlags.contains("STP")) intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (intentFlags.contains("CTP")) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (intentFlags.contains("CTK")) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (intentFlags.contains("RTF")) intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        if (intentFlags.contains("NTK")) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intentFlags.contains("MTK")) intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        if (intentFlags.contains("TOH")) intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        if (intentFlags.contains("RTN")) intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        if (intentFlags.contains("PIT")) intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        if (intentFlags.contains("NDM")) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        if (intentFlags.contains("NHT")) intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lmRg = (RadioGroup) findViewById(R.id.radioGroup_lm);
        aftRg = (RadioGroup) findViewById(R.id.radioGroup_aft);
        finishRg = (RadioGroup) findViewById(R.id.radioGroup_finish);

        flagStpCkb = (CheckBox) findViewById(R.id.flag_stp);
        flagCtpCkb = (CheckBox) findViewById(R.id.flag_ctp);
        flagCtkCkb = (CheckBox) findViewById(R.id.flag_ctk);
        flagRtfCkb = (CheckBox) findViewById(R.id.flag_rtf);
        flagNtkCkb = (CheckBox) findViewById(R.id.flag_ntk);
        flagMtkCkb = (CheckBox) findViewById(R.id.flag_mtk);
        flagTohCkb = (CheckBox) findViewById(R.id.flag_toh);
        flagRtnCkb = (CheckBox) findViewById(R.id.flag_rtn);
        flagPitCkb = (CheckBox) findViewById(R.id.flag_pit);
        flagNdmCkb = (CheckBox) findViewById(R.id.flag_ndm);
        flagNhtCkb = (CheckBox) findViewById(R.id.flag_nht);


        flagStpCkb.setOnCheckedChangeListener(this);
        flagCtpCkb.setOnCheckedChangeListener(this);
        flagCtkCkb.setOnCheckedChangeListener(this);
        flagRtfCkb.setOnCheckedChangeListener(this);
        flagNtkCkb.setOnCheckedChangeListener(this);
        flagMtkCkb.setOnCheckedChangeListener(this);
        flagTohCkb.setOnCheckedChangeListener(this);
        flagRtnCkb.setOnCheckedChangeListener(this);
        flagPitCkb.setOnCheckedChangeListener(this);
        flagNdmCkb.setOnCheckedChangeListener(this);
        flagNhtCkb.setOnCheckedChangeListener(this);


        startActivityBt = (Button) findViewById(R.id.bt_start);

        tv  = (TextView) findViewById(R.id.tv_tc);
        tv.setText(this.getLocalClassName());
        String packageName = this.getPackageName();

        tvTasks = (TextView) findViewById(R.id.tv_tasks);

        startActivityBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < lmRg.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) lmRg.getChildAt(i);
                    if (rb.isChecked()) {
                        launchMode = rb.getText().toString().trim();
                        break;
                    }
                }
                for (int i = 0; i < aftRg.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) aftRg.getChildAt(i);
                    if (rb.isChecked()) {
                        taskAffinity = rb.getText().toString().trim();
                        break;
                    }
                }
                String activityName = packageName + "." + launchMode + "_" + taskAffinity;
                try {
                    Class activity = getClassLoader().loadClass(activityName);
                    Intent intent = new Intent(SIT_2.this, activity);
                    getIntentFlags(intent);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                RadioButton rb = (RadioButton) findViewById(R.id.finish_1);
                if (rb.isChecked()) {
                    finish();
                }
            }
        });



    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String type = buttonView.getTag().toString().trim();
        String name = buttonView.getText().toString().trim();
        if (isChecked){
            if (type.contains("flag")) {
                intentFlags.add(name);
            }
        } else {
            if (type.contains("flag")) {
                intentFlags.remove(name);
            }
        }
    }
}