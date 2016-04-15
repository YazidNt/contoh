package com.android.travel;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Menu_utama extends Activity implements android.view.View.OnClickListener {
	Button b_info,b_pemesanan,b_bantuan,b_tentang;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_utama);
		
		b_info= (Button) findViewById(R.id.btninfo);
		b_pemesanan= (Button) findViewById(R.id.btnpemesanan);
		b_bantuan= (Button) findViewById(R.id.btnbantuan);
		b_tentang= (Button) findViewById(R.id.btntentang);
		
		b_info.setOnClickListener(this);
		b_pemesanan.setOnClickListener(this);
		b_bantuan.setOnClickListener(this);
		b_tentang.setOnClickListener(this);
	}

	@Override
	public void onClick(View V) {
		// TODO Auto-generated method stub
		switch (V.getId()) {
		case R.id.btninfo:
		startActivity(new Intent(getApplicationContext(), Info.class));	
			break;
		case R.id.btnpemesanan:
			startActivity(new Intent(getApplicationContext(), Submenu.class));		
			break;
		case R.id.btnbantuan:
			startActivity(new Intent(getApplicationContext(), Bantuan.class));	
			break;
		case R.id.btntentang:
			startActivity(new Intent(getApplicationContext(), Tentang.class));	
			break;
		default:
			break;
		}
		
	}

	
}
