package com.example.deepa.readwritefile;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    EditText inputText;
    TextView response;
    Button saveButton,readButton,delete;

    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    String myData = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




            delete=(Button)findViewById(R.id.button);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String myFile = "/SD/SampleFile.txt";

                    String myPath = Environment.getExternalStorageDirectory()+myFile;
                    File file = new File(myPath);
                    boolean deleted = file.delete();
                    Toast.makeText(getApplicationContext(),"file deleted",Toast.LENGTH_LONG).show();
                    inputText.setText("");
                }
            });




                inputText = (EditText) findViewById(R.id.myInputText);
                response = (TextView) findViewById(R.id.response);




                saveButton =
                        (Button) findViewById(R.id.saveExternalStorage);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(myExternalFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            fos.write(inputText.getText().toString().getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        inputText.setText("");
                        response.setText("SampleFile.txt saved to External Storage...");
                    }
                });

                readButton = (Button) findViewById(R.id.getExternalStorage);
                readButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(myExternalFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            DataInputStream in = new DataInputStream(fis);
                            BufferedReader br =
                                    new BufferedReader(new InputStreamReader(in));
                            String strLine;
                            while ((strLine = br.readLine()) != null) {
                                myData = myData + strLine;
                            }
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        inputText.setText(myData);
                        response.setText("SampleFile.txt data retrieved ");
                    }
                });

                if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                    saveButton.setEnabled(false);
                }
                else {
                    myExternalFile = new File(getExternalFilesDir(filepath), filename);
                }


            }
            private static boolean isExternalStorageReadOnly() {
                String extStorageState = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                    return true;
                }
                return false;
            }

            private static boolean isExternalStorageAvailable() {
                String extStorageState = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                    return true;
                }
                return false;
            }





        }

