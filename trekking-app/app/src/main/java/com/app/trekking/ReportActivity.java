package com.app.trekking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {
    EditText edtChuDe, edtNoiDung;
    Button btnGuiGopY;
    String nd = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_form);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnGuiGopY = (Button) findViewById(R.id.btnGuiGopY);
        edtChuDe = (EditText) findViewById(R.id.edtChuDe);
        edtNoiDung = (EditText) findViewById(R.id.edtNoiDung);


        btnGuiGopY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nd = nd + edtChuDe.getText().toString();
                nd = nd  + '\n' + edtNoiDung.getText().toString();


                String[] TO = new String[]{"dothanhlam.98@gmail.com"};
                //String[] CC = {"xyz@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                //emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, nd);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Feedback"));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ReportActivity.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
