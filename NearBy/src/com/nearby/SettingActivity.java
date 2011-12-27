package com.nearby;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity{
	
	private Button btn_SaveSetting;
	private EditText editText_NickName;
	public static String nickName;
	private SharedPreferences mySharedPreferences;	
    private SharedPreferences.Editor editor;    
    
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_setting);

	    btn_SaveSetting = (Button)findViewById(R.id.btn_SaveSetting);
	    editText_NickName = (EditText)findViewById(R.id.editText_NickName);
	    
	    //SharedPreferences�� �̸��� ��� ����(0�� ȣ���� application������ ����� �� �ִ� ���)
	    mySharedPreferences = getSharedPreferences("mysp", 0);	
	    
	    //SharedPreferences�� ������ �� �ִ� ������ ����
	    editor = mySharedPreferences.edit();
	    
	    //����Ʈ �г��� guest�� �ƴ϶�� mySharedPreferences�� ���� �� �̸����� setText
	    if(mySharedPreferences.getString("NickName", "")!="guest"){
	    	editText_NickName.setText(mySharedPreferences.getString("NickName", ""));
	    }
	    
	    btn_SaveSetting.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				editmySharedPreferences();
				Toast.makeText(SettingActivity.this, "���� �Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
				nickName = editText_NickName.getText().toString();
				nickName = mySharedPreferences.getString("NickName", "");
			}
		});
	}
	
	//editmySharedPreferences�� ������ �г��� ����
	public void editmySharedPreferences(){
		editor.putString("NickName", editText_NickName.getText().toString());
		editor.commit();
	}
}
