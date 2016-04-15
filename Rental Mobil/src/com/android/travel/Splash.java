package com.android.travel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.ProgressBar;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        generateCode();
    }
    private void generateCode() {
		proBar = (ProgressBar) findViewById(R.id.progres);
		setProgress();
	}

	private void setProgress() {
		Timers = new Thread() {
			public void run() {
				try {
					int logoTimer = 0;
					while (logoTimer <= 100) {
						sleep(25);
						logoTimer++;
						proBar.setProgress(logoTimer);
						if (logoTimer == 100) {
							startActivity(new Intent(getBaseContext(),
									Menu_utama.class));
						}
					}
					// Timers.stop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		Timers.start();
	}

	private ProgressBar proBar = null;
	private Thread Timers = null;

}
