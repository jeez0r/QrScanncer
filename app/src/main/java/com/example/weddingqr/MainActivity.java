package com.example.weddingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private CodeScanner mCodeScanner;
    String xfindercolon = ":";
    String xfinderdot = ".";
    String xtable;
    Button btn_try;
    TextView tv_result, tv_welcome, tv_table;
    String xresult;
    int xtablestring;
    int xwelcomestring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setFormats(CodeScanner.ALL_FORMATS);
        mCodeScanner.setScanMode(com.budiyev.android.codescanner.ScanMode.SINGLE);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_welcome = (TextView) findViewById(R.id.tv_welcome);
        tv_table = (TextView) findViewById(R.id.tv_table);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                Log.d(TAG, "onDecoded: " + result.getText());
                xresult = result.getText();
                xtablestring = xresult.indexOf(xfindercolon) + 1;
                xwelcomestring = xresult.indexOf(xfinderdot) + 1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(xresult.substring(0, xwelcomestring));
                        tv_welcome.setText(xresult.substring(xwelcomestring, xtablestring - 1));
                        tv_table.setText(xresult.substring(xtablestring));
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//               Toast.makeText(MainActivity.this, xresult.substring(xtablestring,xresult.length()), Toast.LENGTH_SHORT).show();
//               Toast.makeText(MainActivity.this, xresult.substring(xwelcomestring,xtablestring -1), Toast.LENGTH_SHORT).show();
//               Log.d(TAG, "run: " + xresult.substring(xwelcomestring,xtablestring -1));
//                builder1.setMessage(result.getText());
//                builder1.setCancelable(true);
//                builder1.setPositiveButton(
//                        "Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                builder1.setNegativeButton(
//                        "No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}