package com.ebay.rlsample;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ebay.redlasersdk.BarcodeResult;
import com.ebay.redlasersdk.BarcodeScanActivity;
import com.ebay.redlasersdk.RedLaserExtras;

public class RLSample extends Activity 
{
	private String udid;
	SharedPreferences prefs;
	SharedPreferences.Editor prefsEditor;
	
	BarcodeListAdapter listAdapter;
	boolean isLaunchingAnotherActivity = false;
	
	private static final String SAVED_INSTANCE_LIST = "BarcodeList";
	
    @SuppressWarnings(value = "unchecked")
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setTitle("RedLaser Sample App");
		setContentView(0x7f030001);
		
		// by default enable all barcode types 
		prefs = this.getSharedPreferences(Options.OPTIONS_PREFERENCE, MODE_PRIVATE);
		prefsEditor = prefs.edit();
		if (!prefs.contains(Options.OPTIONS_UPCE))
			prefsEditor.putBoolean(Options.OPTIONS_UPCE, true);
		if (!prefs.contains(Options.OPTIONS_EAN13))
			prefsEditor.putBoolean(Options.OPTIONS_EAN13, true);
		if (!prefs.contains(Options.OPTIONS_EAN5))
			prefsEditor.putBoolean(Options.OPTIONS_EAN5, true);
		if (!prefs.contains(Options.OPTIONS_EAN2))
			prefsEditor.putBoolean(Options.OPTIONS_EAN2, true);
		if (!prefs.contains(Options.OPTIONS_EAN8))
			prefsEditor.putBoolean(Options.OPTIONS_EAN8, true);
		if (!prefs.contains(Options.OPTIONS_STICKY))
			prefsEditor.putBoolean(Options.OPTIONS_STICKY, true);
		if (!prefs.contains(Options.OPTIONS_QRCODE))
			prefsEditor.putBoolean(Options.OPTIONS_QRCODE, true);
		if (!prefs.contains(Options.OPTIONS_CODE128))
			prefsEditor.putBoolean(Options.OPTIONS_CODE128, true);
		if (!prefs.contains(Options.OPTIONS_CODE39))
			prefsEditor.putBoolean(Options.OPTIONS_CODE39, true);
		if (!prefs.contains(Options.OPTIONS_CODE93))
			prefsEditor.putBoolean(Options.OPTIONS_CODE93, true);
		if (!prefs.contains(Options.OPTIONS_DATAMATRIX))
			prefsEditor.putBoolean(Options.OPTIONS_DATAMATRIX, true);
		if (!prefs.contains(Options.OPTIONS_RSS14))
			prefsEditor.putBoolean(Options.OPTIONS_RSS14, true);
		if (!prefs.contains(Options.OPTIONS_ITF))
			prefsEditor.putBoolean(Options.OPTIONS_ITF, true);
		if (!prefs.contains(Options.OPTIONS_CODABAR))
			prefsEditor.putBoolean(Options.OPTIONS_CODABAR, true);
		prefsEditor.commit();
		
		
		TextView udidView = (TextView) findViewById(0x7f060003);
		udid = RedLaserExtras.getDeviceID(getContentResolver());
		udidView.setText("UDID: "+udid);
		
		ArrayList<BarcodeResult> savedValues = null;
        if (savedInstanceState != null)
        {
        	savedValues = (ArrayList<BarcodeResult>) savedInstanceState.getSerializable(SAVED_INSTANCE_LIST);
        }
        
        ListView barcodeList = (ListView) findViewById(0x7f060005);
		listAdapter = new BarcodeListAdapter(this, savedValues);
		barcodeList.setAdapter(listAdapter);
		
    }
    
    @Override
   protected void onResume()
    {
    	super.onResume();
    	
		Button singleScanButton = (Button)findViewById(0x7f060006);
		Button multiScanButton = (Button)findViewById(0x7f060007);
		Button optionsButton = (Button)findViewById(0x7f060008);
		Button clearListButton = (Button)findViewById(0x7f060004);
		
		singleScanButton.setOnClickListener(singleScanButtonListener);
		multiScanButton.setOnClickListener(multiScanButtonListener);
		optionsButton.setOnClickListener(optionsButtonListener);
		clearListButton.setOnClickListener(clearListButtonListener);

		isLaunchingAnotherActivity = false;
    }
	
	protected void onSaveInstanceState(Bundle outState)
	{
		outState.putSerializable(SAVED_INSTANCE_LIST, listAdapter.getResultList());
	}
	
	OnClickListener singleScanButtonListener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			try {
				if (!isLaunchingAnotherActivity)
				{
					isLaunchingAnotherActivity = true;
					Intent scanIntent = new Intent(RLSample.this,RLSampleScannerActivity.class);
					scanIntent.putExtra(RLSampleScannerActivity.INTENT_MULTI_SCAN, false);
					startActivityForResult(scanIntent,1);
					Log.d("RLSample", "Starting RedLaser Scan.");
				}
			} catch(Exception e)
			{
				Log.d("RLSample",e.getLocalizedMessage()+" "+e.getCause());
			}
		}
	};
	
	OnClickListener multiScanButtonListener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			try {
				if (!isLaunchingAnotherActivity)
				{
					isLaunchingAnotherActivity = true;
					Intent scanIntent = new Intent(RLSample.this,RLSampleScannerActivity.class);
					scanIntent.putExtra(RLSampleScannerActivity.INTENT_MULTI_SCAN, true);
					startActivityForResult(scanIntent,1);
				}
			} catch(Exception e)
			{
				Log.d("RLSample",e.getLocalizedMessage()+" "+e.getCause());
			}
		}
	};
	
	OnClickListener optionsButtonListener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			if (!isLaunchingAnotherActivity)
			{
				isLaunchingAnotherActivity = true;
				Intent optionsIntent = new Intent(RLSample.this, Options.class);
				startActivity(optionsIntent);
			}
		}
	};
	
	OnClickListener clearListButtonListener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			listAdapter.clearList();
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		// We came from the scanning activity; the return intent contains a RESULT_EXTRA key
		// whose value is an ArrayList of BarcodeResult objects that we found while scanning.
		// Get the list of objects and add them to our list view.
		if (resultCode == RESULT_OK) 
		{			
			ArrayList<BarcodeResult> barcodes = data.getParcelableArrayListExtra(BarcodeScanActivity.RESULT_EXTRA);
			if (barcodes != null)
			{
				listAdapter.addBarcodes(barcodes);	
			}
		}
	}

}