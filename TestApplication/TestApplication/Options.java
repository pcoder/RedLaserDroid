package com.ebay.rlsample;

import com.ebay.redlasersdk.RedLaserExtras;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;


public class Options extends Activity
{

	public static final String OPTIONS_UPCE = "optionsUpce";
	public static final String OPTIONS_EAN13 = "optionsEan13";
	public static final String OPTIONS_EAN5 = "optionsEan5";
	public static final String OPTIONS_EAN2 = "optionsEan2";
	public static final String OPTIONS_EAN8 = "optionsEan8";
	public static final String OPTIONS_STICKY = "optionsSticky";
	public static final String OPTIONS_QRCODE = "optionsQRCode";
	public static final String OPTIONS_CODE128 = "optionsCode128";
	public static final String OPTIONS_CODE39 = "optionsCode39";
	public static final String OPTIONS_CODE93 = "optionsCode93";
	public static final String OPTIONS_DATAMATRIX = "optionsDataMatrix";
	public static final String OPTIONS_RSS14 = "optionsRss14";
	public static final String OPTIONS_ITF = "optionsItf";
	public static final String OPTIONS_CODABAR = "optionsCodabar";
	
	public static final String OPTIONS_ORIENTATION = "optionsOrientation";
	
	public static final String OPTIONS_PREFERENCE = "scannerOptionsPrefs";
	
	SharedPreferences prefs;
	SharedPreferences.Editor prefsEditor;
	
	ToggleButton toggleUPC;
	ToggleButton toggleEAN13;
	ToggleButton toggleEAN5;
	ToggleButton toggleEAN2;
	ToggleButton toggleEAN8;
	ToggleButton toggleSticky;
	ToggleButton toggleQR;
	ToggleButton toggle128;
	ToggleButton toggle39;
	ToggleButton toggle93;
	ToggleButton toggleDataMatrix;
	ToggleButton toggleRSS14;
	ToggleButton toggleITF;
	ToggleButton toggleCodabar;
	
	ToggleButton toggleOrientation;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(0x7f030003);
		
		prefs = this.getSharedPreferences(OPTIONS_PREFERENCE, MODE_PRIVATE);
		prefsEditor = prefs.edit();
		
		
		toggleUPC = (ToggleButton) findViewById(0x7f06000f);
		toggleUPC.setChecked(prefs.getBoolean(OPTIONS_UPCE, false));
		toggleUPC.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleUPC.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_UPCE, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_UPCE, false);
		        }
		    }
		});
		
		toggleEAN13 = (ToggleButton) findViewById(0x7f06000b);
		toggleEAN13.setChecked(prefs.getBoolean(OPTIONS_EAN13, false));
		toggleEAN13.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleEAN13.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_EAN13, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_EAN13, false);
		        }
		    }
		});
		
		toggleEAN5 = (ToggleButton) findViewById(0x7f06000d);
		toggleEAN5.setChecked(prefs.getBoolean(OPTIONS_EAN5, false));
		toggleEAN5.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleEAN5.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_EAN5, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_EAN5, false);
		        }
		    }
		});
		
		toggleEAN2 = (ToggleButton) findViewById(0x7f06000e);
		toggleEAN2.setChecked(prefs.getBoolean(OPTIONS_EAN2, false));
		toggleEAN2.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleEAN2.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_EAN2, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_EAN2, false);
		        }
		    }
		});
		
		toggleEAN8 = (ToggleButton) findViewById(0x7f06000c);
		toggleEAN8.setChecked(prefs.getBoolean(OPTIONS_EAN8, false));
		toggleEAN8.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleEAN8.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_EAN8, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_EAN8, false);
		        }
		    }
		});
		
		toggleSticky = (ToggleButton) findViewById(0x7f060010);
		toggleSticky.setChecked(prefs.getBoolean(OPTIONS_STICKY, false));
		toggleSticky.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleSticky.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_STICKY, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_STICKY, false);
		        }
		    }
		});
		
		toggleQR = (ToggleButton) findViewById(0x7f060011);
		toggleQR.setChecked(prefs.getBoolean(OPTIONS_QRCODE, false));
		toggleQR.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleQR.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_QRCODE, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_QRCODE, false);
		        }
		    }
		});
		
		toggle128 = (ToggleButton) findViewById(0x7f060012);
		toggle128.setChecked(prefs.getBoolean(OPTIONS_CODE128, false));
		toggle128.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggle128.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_CODE128, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_CODE128, false);
		        }
		    }
		});
		
		toggle39 = (ToggleButton) findViewById(0x7f060013);
		toggle39.setChecked(prefs.getBoolean(OPTIONS_CODE39, false));
		toggle39.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggle39.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_CODE39, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_CODE39, false);
		        }
		    }
		});
		
		toggle93 = (ToggleButton) findViewById(0x7f060014);
		toggle93.setChecked(prefs.getBoolean(OPTIONS_CODE93, false));
		toggle93.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggle93.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_CODE93, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_CODE93, false);
		        }
		    }
		});
		
		toggleDataMatrix = (ToggleButton) findViewById(0x7f060015);
		toggleDataMatrix.setChecked(prefs.getBoolean(OPTIONS_DATAMATRIX, false));
		toggleDataMatrix.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleDataMatrix.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_DATAMATRIX, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_DATAMATRIX, false);
		        }
		    }
		});
		
		toggleRSS14 = (ToggleButton) findViewById(0x7f060016);
		toggleRSS14.setChecked(prefs.getBoolean(OPTIONS_RSS14, false));
		toggleRSS14.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleRSS14.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_RSS14, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_RSS14, false);
		        }
		    }
		});
		
		toggleITF = (ToggleButton) findViewById(0x7f060017);
		toggleITF.setChecked(prefs.getBoolean(OPTIONS_ITF, false));
		toggleITF.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleITF.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_ITF, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_ITF, false);
		        }
		    }
		});
		
		toggleCodabar = (ToggleButton) findViewById(0x7f060018);
		toggleCodabar.setChecked(prefs.getBoolean(OPTIONS_CODABAR, false));
		toggleCodabar.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleCodabar.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_CODABAR, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_CODABAR, false);
		        }
		    }
		});
		
		toggleOrientation = (ToggleButton) findViewById(0x7f060019);
		toggleOrientation.setChecked(prefs.getBoolean(OPTIONS_ORIENTATION, false));
		toggleOrientation.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		        if (toggleOrientation.isChecked()) {
		        	prefsEditor.putBoolean(OPTIONS_ORIENTATION, true);
		        } else {
		        	prefsEditor.putBoolean(OPTIONS_ORIENTATION, false);
		        }
		    }
		});
		
		TextView versionTextView = (TextView) findViewById(0x7f06000a);
		versionTextView.setText("RL_SDK Version " + RedLaserExtras.getRedLaserSDKVersion());

    }
	
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		prefsEditor.commit();
	}
	
	
}