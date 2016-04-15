package com.android.travel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import koneksi.CustomHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;

public class Info extends Activity {
	String[] kodeinfo;
	String[] info;
	String URL = "http://192.168.56.1:92/Traveladmin/android/getinfo.php";
	ListView list_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		list_info = (ListView) findViewById(R.id.list_info);
		new getInfo().execute("URL");
	}

	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

	class getInfo extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(Info.this);
		String Content;
		String Error = null;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... arg0) {
			try {

				Content = CustomHttpClient.executeHttpPost(URL, data);
			} catch (ClientProtocolException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (IOException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (Exception e) {
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
				Toast.makeText(getBaseContext(), "Koneksi Internet Error !",
						Toast.LENGTH_LONG).show();
			} else {
//				Toast.makeText(getBaseContext(), Content,
//						Toast.LENGTH_LONG).show();
				try {
					jObject = new JSONObject(Content);
					JSONArray menuitemArray = jObject.getJSONArray("info");
					kodeinfo = new String[menuitemArray.length()];
					info = new String[menuitemArray.length()];

					for (int i = 0; i < menuitemArray.length(); i++) {
						kodeinfo[i] = menuitemArray.getJSONObject(i)
								.getString("kode_info").toString();
						info[i] = menuitemArray.getJSONObject(i)
								.getString("info").toString();
					}
					list_info.setAdapter(new ArrayAdapter<Object>(
							getBaseContext(),
							android.R.layout.simple_list_item_1, info));

				} catch (JSONException ex) {
					Logger.getLogger(Info.class.getName()).log(Level.SEVERE,
							null, ex);

				}
			}
		}

	}

}
