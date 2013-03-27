package com.ebay.rlsample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ebay.redlasersdk.BarcodeResult;
import com.ebay.redlasersdk.BarcodeScanActivity;

public class RLSampleScannerActivity extends BarcodeScanActivity 
{	
	public static final String INTENT_MULTI_SCAN = "intent_multi_scan";
		
	int mCurrentApiVersion = android.os.Build.VERSION.SDK_INT;    

	// The viewfinderView is the rectangle in the center showing the user where to
	// place the barcode. FoundText shows how many barcodes have been found. hintText
	// is used to guide the user through multi-step decode processes.
	View viewfinderView = null;
	Button doneButton;
	Button requestImageButton;
	Button toggleTorchButton;
	TextView hintTextView;
	TextView foundTextView;
		
	private boolean isMultiScan = false;
	private boolean isInRange = false;
	
	Display mDisplay;
	String mRequestedOrientation;
	
	SharedPreferences prefs;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) 
    {    	    	
    	prefs = this.getSharedPreferences(Options.OPTIONS_PREFERENCE, MODE_PRIVATE);
    	if (!prefs.getBoolean(Options.OPTIONS_ORIENTATION, false))
    	{
    		mRequestedOrientation = BarcodeScanActivity.PREF_ORIENTATION_PORTRAIT;
    	} else
    	{
    		mRequestedOrientation = BarcodeScanActivity.PREF_ORIENTATION_LANDSCAPE;
    	}
    	
    	super.onCreate(icicle);
    	
    	 // Here we can set what types of barcode to scan
        enabledTypes.setUpce(prefs.getBoolean(Options.OPTIONS_UPCE, true));
        enabledTypes.setEan2(prefs.getBoolean(Options.OPTIONS_EAN2, false));
        enabledTypes.setEan5(prefs.getBoolean(Options.OPTIONS_EAN5, false));
        enabledTypes.setEan8(prefs.getBoolean(Options.OPTIONS_EAN8, false));
        enabledTypes.setEan13(prefs.getBoolean(Options.OPTIONS_EAN13, false));
        enabledTypes.setQRCode(prefs.getBoolean(Options.OPTIONS_QRCODE, false));
        enabledTypes.setCode128(prefs.getBoolean(Options.OPTIONS_CODE128, false));
        enabledTypes.setCode39(prefs.getBoolean(Options.OPTIONS_CODE39, false));
        enabledTypes.setCode93(prefs.getBoolean(Options.OPTIONS_CODE93, false));
        enabledTypes.setDataMatrix(prefs.getBoolean(Options.OPTIONS_DATAMATRIX, false));
        enabledTypes.setITF(prefs.getBoolean(Options.OPTIONS_ITF, false));
        enabledTypes.setRSS14(prefs.getBoolean(Options.OPTIONS_RSS14, false));
        enabledTypes.setSticky(prefs.getBoolean(Options.OPTIONS_STICKY, false));
        enabledTypes.setCodabar(prefs.getBoolean(Options.OPTIONS_CODABAR, false)); 
        
        // To Remove the Title bar from RedLaserSDK Activity, add the NoTitleBar Theme in your Android Manifest.
        // Remove the Status Bar from this window.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        mDisplay = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        
        // use different layout depending on whether phone is pre-2.2
        // pre-2.2 devices can only use the camera in landscape and must use rotated components 
        // to make the scan screen appear in portrait
    	if (mRequestedOrientation.equals(BarcodeScanActivity.PREF_ORIENTATION_PORTRAIT))
    	{
            if (mCurrentApiVersion >= 8)
            {
        		setContentView(0x7f030005);
            } else
            {
            	setContentView(0x7f030006);
            }
    	} else
    	{
    		setContentView(0x7f030004);
    	}
       
        // Find and position the viewfinder view
        viewfinderView = findViewById(0x7f06001c);
        if (mCurrentApiVersion >= 8)
        {
        	LayoutParams params = new LayoutParams((int)(mDisplay.getWidth() * .75f), 
        			(int)(mDisplay.getHeight() * .33f));
        	params.gravity = Gravity.CENTER;
        	viewfinderView.setLayoutParams(params);
        }
                
    	isMultiScan = false;
        if (this.getIntent().getBooleanExtra(INTENT_MULTI_SCAN, false) == true)
        {
        	isMultiScan = true;
        }
        
        doneButton = (Button) findViewById(0x7f060023);
        doneButton.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		doneScanning();
			}
        });
        
        requestImageButton = (Button) findViewById(0x7f060022);
        requestImageButton.setOnClickListener(new OnClickListener()
        {
			public void onClick(View v) 
			{
				requestImageData();
			}
        });
        
        toggleTorchButton = (Button) findViewById(0x7f060021);
        toggleTorchButton.setOnClickListener(new OnClickListener()
        {

			public void onClick(View v) 
			{
				boolean torch = getTorch();
				setTorch(!torch);
			}
        });
        
        hintTextView = (TextView) findViewById(0x7f06001b);
        foundTextView = (TextView) findViewById(0x7f06001d);
    }
 
	/** Because the camera gets initialized in super.onResume(), we can't find out
	 *  whether the torch is available until after super.onResume() completes.
	 *  So, we enable or disable the 'Light' button here instead of in onCreate().
	 */
	protected void onResume()
	{
		super.onResume();
		
		toggleTorchButton.setEnabled(isTorchAvailable());
	}

    /** Camera error callback. Implement this method to handle any camera 
     *  errors thrown by Android OS.
     */
	@Override
	protected void onError(int error) 
	{
		Log.d("RLSampleScannerActivity", "Received an error from the Camera.");	
	}
	
	/** Called by the SDK repeatedly while scanning is happening. This method 
	 *  override is how your app can find out about the status of the scanning
	 *  session in real time.
	 */
	@Override
	protected void onScanStatusUpdate(Map<String, Object> scanStatus) 
	{
		String foundText = "0 Barcodes Found";
		
		// Retrieve the set of found barcodes from the HashMap
	    @SuppressWarnings(value = "unchecked")
		Set<BarcodeResult> allResults = (Set<BarcodeResult>) scanStatus.get(Status.STATUS_FOUND_BARCODES);
		
		if (allResults != null && allResults.size() > 0)
		{
			// Show how many barcodes we've found
			foundText = allResults.size() + " Barcodes Found";

			if (!isMultiScan)
			{
				// If we're in single scan mode and we've found something, we're done.
				doneScanning();
			}
		}
		
		// change color of viewfinder box if in-range status changes
		Boolean inRangeStatus = (Boolean) scanStatus.get(Status.STATUS_IN_RANGE);
		if (inRangeStatus != null)
		{
			if (isInRange != inRangeStatus.booleanValue())
			{
				isInRange = inRangeStatus.booleanValue();
				if (isInRange)
				{
					viewfinderView.setBackgroundDrawable(getResources().getDrawable(0x7f020003));
				} else
				{
					viewfinderView.setBackgroundDrawable(getResources().getDrawable(0x7f020004));
				}
			}
		}
		
		foundTextView.setText(foundText);
		
		// Guidance. This is for recognizing long barcodes in parts by swiping the device across
		// the barcode. See the documentaiton for more details.
		Integer guidanceLevel = (Integer) scanStatus.get(Status.STATUS_GUIDANCE);
		if (guidanceLevel == null)
		{
			hintTextView.setText("");
		} else
		{
			if (guidanceLevel.intValue() == 1)
			{
				hintTextView.setText(getResources().getText(0x7f050005));
			} else if (guidanceLevel.intValue() == 2)
			{
				BarcodeResult partialBarcode = (BarcodeResult) scanStatus.get(Status.STATUS_PARTIAL_BARCODE);
				String partialString = partialBarcode.barcodeString;
				hintTextView.setText(partialString);
			}
		} 
		
		// This status string will only appear in response to a call to requestImageData().
		if (scanStatus.get(Status.STATUS_CAMERA_SNAPSHOT) != null)
		{
			// imageData will always be a JPEG image passed in a byte array. Depending on 
			// device hardware and API level, the image may be a preview frame that has
			// been JPEG compressed, or it may be a JPEG taken from the Camera's picture-taking
			// apparatus.
			byte[] imageData = (byte[]) scanStatus.get(Status.STATUS_CAMERA_SNAPSHOT);
			
			// Write the image data out to a file. Use a command like:
			//     adb pull /mnt/sdcard/Android/data/com.ebay.rlsample/files/PreviewCapture.jpg
			// to look at what gets captured.
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED))
			{
				FileOutputStream output = null;
				try {
					// Since this is a demo, just keep overwriting this one file
					File imageDir = new File(Environment.getExternalStorageDirectory(), 
							"Android/data/com.ebay.rlsample/files");
					imageDir.mkdirs();
					File imageFile = new File(imageDir, "PreviewCapture.jpg");
					output = new FileOutputStream(imageFile, false);
					output.write(imageData, 0, imageData.length);
					output.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/** BarcodeScanActivity queries this method during onCreate() to determine which
	 *  orientation it should use.
	 */
	@Override
	public String getOrientationSetting() 
	{
		if (!prefs.getBoolean(Options.OPTIONS_ORIENTATION, false))
		{
			return PREF_ORIENTATION_PORTRAIT;
		}
		else
		{
			return PREF_ORIENTATION_LANDSCAPE;
		}
	}    
}