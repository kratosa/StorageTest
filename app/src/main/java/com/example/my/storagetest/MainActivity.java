package com.example.my.storagetest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEt;
    private Button mbtn1;
    private Button mbtn2;
    private TextView mtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEt = (EditText) findViewById(R.id.et_view);
        mbtn1 = (Button) findViewById(R.id.btn1);
        mbtn2 = (Button) findViewById(R.id.btn2);
        mtv = (TextView) findViewById(R.id.tv);

        mbtn1.setOnClickListener(this);
        mbtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn1:
                String txt = mEt.getEditableText().toString();
                putStringToSdcard(txt,"test.txt");
                break;
            case R.id.btn2:
                String result =getStringFromSdcard("test.txt");
                if(!TextUtils.isEmpty(result))
                {
                    mtv.setText(result);
                }
        }
    }

    public void putStringToSdcard(String s,String fileName)
    {
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = sdcardPath+ File.separator+fileName;
        File file = new File(filePath);
        BufferedOutputStream bos = null;
        try {
            OutputStream os = new FileOutputStream(filePath);
            bos = new BufferedOutputStream(os);
            byte[] data = s.getBytes();
            os.write(data, 0, data.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringFromSdcard(String fileName)
    {
        String sdcardPath = Environment.getExternalStorageDirectory().toString();
        String filePath = sdcardPath+File.separator+fileName;
        File file = new File(filePath);
        if(!file.exists())
        {
            return null;
        }
        BufferedInputStream bis = null;
        try {
            InputStream is = new FileInputStream(filePath);
            bis =new BufferedInputStream(is);
            byte []data = new byte[1024];
            int len = 0;
            StringBuffer buffer = new StringBuffer();
            while((len = bis.read())!=-1)
            {
                buffer.append(new String(data,0,len));
                return buffer.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
