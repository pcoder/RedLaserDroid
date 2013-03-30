using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Android.Util;
using System.Collections.Generic;

namespace TestApplication
{
    [Activity(Label = "TestApplication", MainLauncher = true, Icon = "@drawable/icon")]
    public class Activity1 : Activity
    {
        int count = 1;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            // Get our button from the layout resource,
            // and attach an event to it
            Button button = FindViewById<Button>(Resource.Id.MyButton);

            button.Click += delegate {
                Intent scanIntent = new Intent(this, Java.Lang.Class.ForName("com.ebay.rlsample.RLSampleScannerActivity"));
                scanIntent.PutExtra("intent_multi_scan", false);
                StartActivityForResult(scanIntent,1);
                button.Text = string.Format("{0} clicks!", count++); 
            };
        }

        protected override void OnActivityResult(int requestCode, Result resultCode, Android.Content.Intent data)
        {
            if (resultCode == Result.Ok)
            {
                var barcode = data.GetStringExtra("com.ebay.redlasersdk.barcode");
                var barcodeType = data.GetStringExtra("com.ebay.redlasersdk.barcodetype");
                Log.Debug("TESTApp", "BARCODE: " + barcode);
                new AlertDialog.Builder(this).SetTitle("Scan Result").SetMessage(barcodeType + ": " + barcode)
                .SetNegativeButton("OK", (Dialog, whichButton) => { }).Show();
            }
        }
    }
}

