package com.android.travel;

import java.io.IOException;
import java.util.ArrayList;

import koneksi.CustomHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registrasi extends Activity implements OnClickListener {

	EditText txtid, txtnoidentitas, txtnama, txtalamat, txtttl, txtnotelp;
	Button btnsimpan, btnbatal;
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	String url = "http://192.168.56.1:92/Traveladmin/android/registrasi.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrasi);

		txtid = (EditText) findViewById(R.id.txtidpesan);
		txtnoidentitas = (EditText) findViewById(R.id.txtnomerid);
		txtnama = (EditText) findViewById(R.id.txtnama);
		txtalamat = (EditText) findViewById(R.id.txtalamat);
		txtttl = (EditText) findViewById(R.id.txtttl);
		txtnotelp = (EditText) findViewById(R.id.txtnotelp);

		btnsimpan = (Button) findViewById(R.id.btnreg);
		btnbatal = (Button) findViewById(R.id.btnbatal);
		btnsimpan.setOnClickListener(this);
		btnbatal.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registrasi, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnreg) {
			data.add(new BasicNameValuePair("id_member", txtid.getText()
					.toString()));
			data.add(new BasicNameValuePair("no_identitas", txtnoidentitas
					.getText().toString()));
			data.add(new BasicNameValuePair("nama", txtnama.getText()
					.toString()));
			data.add(new BasicNameValuePair("alamat", txtalamat.getText()
					.toString()));
			data.add(new BasicNameValuePair("ttl", txtttl.getText().toString()));
			data.add(new BasicNameValuePair("telepon", txtnotelp.getText()
					.toString()));
			new registrasi().execute(url);
		}
		if (v.getId() == R.id.btnbatal) {
			clear();
		}
	}

	private void clear() {
		txtid.setText("");
		txtnoidentitas.setText("");
		txtnama.setText("");
		txtalamat.setText("");
		txtttl.setText("");
		txtnotelp.setText("");
		txtid.requestFocus();
	}

	private class registrasi extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(Registrasi.this);
		String Content;
		String Error = null;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(url, data);
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

					Toast.makeText(getBaseContext(), "Registrasi Berhasil",
							Toast.LENGTH_SHORT).show();
					// startActivity(new Intent(getActivity(),Cart.class));
					clear();
				} else {
					Toast.makeText(getBaseContext(), "Registrasi Gagal ",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}
}
