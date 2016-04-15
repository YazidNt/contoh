package com.android.travel;

import android.os.AsyncTask;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import koneksi.CustomHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import android.widget.Toast;
import android.app.ProgressDialog;

public class Submenu extends Activity implements OnClickListener {
	Button btnpesan, btnregistrasi, btnbatalpesan;
	String id_member;
	String URL = "http://192.168.56.1:92/Traveladmin/android/ceklogin.php";
	String urlpesan="http://192.168.56.1:92/Traveladmin/android/insertbatalpesan.php";
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	ArrayList<NameValuePair> dataPengajar = new ArrayList<NameValuePair>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submenu);

		btnpesan = (Button) findViewById(R.id.btnpesan);
		btnregistrasi = (Button) findViewById(R.id.btnregistrasi);
		btnbatalpesan = (Button) findViewById(R.id.btnbatalpesan);

		btnpesan.setOnClickListener(this);
		btnregistrasi.setOnClickListener(this);
		btnbatalpesan.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.btnpesan:
			alertLogin();
			break;

		case R.id.btnregistrasi:
			startActivity(new Intent(getApplicationContext(), Registrasi.class));

			break;

		case R.id.btnbatalpesan:
			alertbatalpesan();
			break;

		default:
			break;

		}
	}

	// Coding alert dialog form login :

	EditText txtidmember,txtidmemberbatal;

	private void alertLogin() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Form Login");
		LinearLayout ly = new LinearLayout(this);
		ly.setOrientation(LinearLayout.VERTICAL);
		txtidmember = new EditText(this);
		txtidmember.setHint("Masukan ID member");
		ly.addView(txtidmember);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dataPengajar.add(new BasicNameValuePair("id_member", txtidmember.getText().toString()));
				new GetDataLogin().execute(URL);
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
		alert.setView(ly);
		
		alert.show();
	}

	private void alertbatalpesan() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Form Batal Pesan");
		LinearLayout ly = new LinearLayout(this);
		ly.setOrientation(LinearLayout.VERTICAL);
		txtidmemberbatal = new EditText(this);
		txtidmemberbatal.setHint("Masukan Kode Pemesanan");
		ly.addView(txtidmemberbatal);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				data.add(new BasicNameValuePair("kode_pesan", txtidmemberbatal.getText().toString()));
				data.add(new BasicNameValuePair("tanggal_batal", giveDate()));
				new insertbatalpesan().execute(urlpesan);
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
		alert.setView(ly);
		alert.show();
	}

	private class GetDataLogin extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(Submenu.this);

		private String Content;
		private String Error = null;
		

		@Override
		protected String doInBackground(String... params) {
			
			try {
				// Jangan masukan komponen interface di sini
				
				Content = CustomHttpClient.executeHttpPost(URL, dataPengajar);
			} catch (ClientProtocolException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (IOException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Content;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog.setMessage("Loading Data..");
			this.dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			this.dialog.dismiss();
			if (result.contains("0")) {
				startActivity(new Intent(getBaseContext(), Pesan.class));
			} else {
				startActivity(new Intent(getBaseContext(), Pesan.class));
//				Toast.makeText(getBaseContext(),
//						"id Member  Tidak Ditemukan !", Toast.LENGTH_LONG)
//						.show();
			}
			// labelerror.setText(Content);
		}
	}
	@SuppressLint("SimpleDateFormat")
	public String giveDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}
	
	private class insertbatalpesan extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(Submenu.this);
		String Content;
		String Error = null;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(urlpesan, data);
			} catch (ClientProtocolException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (IOException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Content;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog.setMessage("Loading Data..");
			this.dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			this.dialog.dismiss();
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Internet Connection",
						Toast.LENGTH_LONG).show();
			} else {
				if (result.contains("1")) {

					Toast.makeText(getBaseContext(), "Pemabatalan Berhasil",
							Toast.LENGTH_SHORT).show();
					// startActivity(new Intent(getActivity(),Cart.class));
					
				} else {
					Toast.makeText(getBaseContext(), "Pembatalan Gagal ",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}
}
