package com.n1c.n1cskonverterare;


import android.annotation.SuppressLint;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("NewApi")
public class omAppen extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.omoss_layout);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
            
            TextView t2 = (TextView) findViewById(R.id.brodtext);
            t2.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }


public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
        return true;
    }
    return super.onOptionsItemSelected(item);
}
}