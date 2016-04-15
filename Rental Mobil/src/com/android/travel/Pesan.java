package com.android.travel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import koneksi.CustomHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Pesan extends Activity implements OnItemSelectedListener{
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	String urlmobil="http://192.168.56.1:92/Traveladmin/android/getmobil.php";
	String urlpesan="http://192.168.56.1:92/Traveladmin/android/insertpesan.php";
	String urlkodepesan="http://192.168.56.1:92/Traveladmin/android/getkodepesan.php";
	String [] kdmobil;
	String [] nama_mobil;
	
	EditText txtlamapesan;
	DatePicker tglpesan;
	Spinner spmobil;
	Button btnsimpan,btnbatal;
	
	SimpleDateFormat dateFormatter ;
	Date d ;
	String tanggal ;
	
	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesann);
		txtlamapesan=(EditText)findViewById(R.id.txtlamapesan);
		tglpesan=(DatePicker)findViewById(R.id.tglpesan);
		spmobil=(Spinner)findViewById(R.id.spmobil);
		spmobil.setOnItemSelectedListener(this);
		btnsimpan=(Button)findViewById(R.id.btnpesan);
		btnbatal=(Button)findViewById(R.id.btnbatalpesan);
		txtkodepesan=(TextView)findViewById(R.id.txtkodepesan);		
		
		new getMobil().execute(urlmobil);
		btnsimpan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int day = tglpesan.getDayOfMonth();
				int month = tglpesan.getMonth();
				int year = tglpesan.getYear();

				d = new Date(year-1900, month, day);
				dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				tanggal=dateFormatter.format(d);
				data.add(new BasicNameValuePair("tgl_pesan",tanggal));
				data.add(new BasicNameValuePair("lama_pesan", txtlamapesan.getText()
						.toString()));
				data.add(new BasicNameValuePair("kode_mobil", vkode));
				new insertpesan().execute(urlpesan);
			}
		});
		btnbatal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clear();
				// get tanggal
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pesan, menu);
		return true;
	}
	
	private class getMobil extends AsyncTask<String, Void, String> {
		String Content;
		String Error = null;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(urlmobil, data);
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
		protected void onPostExecute(String result) {
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Connection Internet",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					jObject = new JSONObject(Content);
					JSONArray menuitemArray = jObject.getJSONArray("mobil");
					kdmobil = new String[menuitemArray.length()];
					nama_mobil = new String[menuitemArray.length()];
					for (int i = 0; i < menuitemArray.length(); i++) {
						kdmobil[i] = menuitemArray.getJSONObject(i)
								.getString("kdmobil").toString();
						nama_mobil[i] = menuitemArray.getJSONObject(i)
								.getString("nama_mobil").toString();
					}
					spmobil.setAdapter(new ArrayAdapter<Object>(
							getBaseContext(),
							android.R.layout.simple_spinner_dropdown_item,
							nama_mobil));
				} catch (JSONException ex) {
					Logger.getLogger(Pesan.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

	}
	
	String [] kd_pesan;
	TextView txtkodepesan;
	
	private class getKodepesan extends AsyncTask<String, Void, String> {
		String Content;
		String Error = null;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(urlkodepesan, data);
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
		protected void onPostExecute(String result) {
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Connection Internet",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					jObject = new JSONObject(Content);
					JSONArray menuitemArray = jObject.getJSONArray("kdpesan");
					kd_pesan = new String[menuitemArray.length()];
					for (int i = 0; i < menuitemArray.length(); i++) {
						kd_pesan[i] = menuitemArray.getJSONObject(i)
								.getString("kd_pesan").toString();
					}
					txtkodepesan.setText("Kode Pemesanan Anda : "+kd_pesan[0]);
				} catch (JSONException ex) {
					Logger.getLogger(Pesan.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

	}
	
	String vkode;

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int index,
			long arg3) {
		// TODO Auto-generated method stub
		vkode=kdmobil[index];
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}	
	
	private class insertpesan extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(Pesan.this);
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

					Toast.makeText(getBaseContext(), "Pemesanan Berhasil",
							Toast.LENGTH_SHORT).show();
					// startActivity(new Intent(getActivity(),Cart.class));
					clear();
					new getKodepesan().execute(urlkodepesan);
				} else {
					Toast.makeText(getBaseContext(), "Pemesanan Gagal ",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	private void clear(){
		txtlamapesan.setText("");
	}

}
