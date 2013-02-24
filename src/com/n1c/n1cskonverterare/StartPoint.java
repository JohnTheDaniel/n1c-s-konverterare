package com.n1c.n1cskonverterare;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StartPoint extends Activity {
	
	Spinner spinner2, spinner1, valueTypeSpinner;
	EditText editTextInValue;
	LinearLayout bottomButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		
		//Skapa spinner
		valueTypeSpinner = (Spinner) findViewById(R.id.valueTypeSpinner);	
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		editTextInValue = (EditText) findViewById(R.id.inValue);
		
		
		//Set upp en ArrayAdapter till alla spinner-värden
		final ArrayAdapter<CharSequence> valueTypeAdapter = ArrayAdapter.createFromResource(StartPoint.this, R.array.valueType, R.layout.spinner_layout);
		final ArrayAdapter<CharSequence> volymEnheterAdapter = ArrayAdapter.createFromResource(StartPoint.this, R.array.volymEnheter, android.R.layout.simple_spinner_item);
		final ArrayAdapter<CharSequence> streckaEnheterAdapter = ArrayAdapter.createFromResource(StartPoint.this, R.array.streckaEnheter, android.R.layout.simple_spinner_item);
		final ArrayAdapter<CharSequence> tidEnheterAdapter = ArrayAdapter.createFromResource(StartPoint.this, R.array.tidEnheter, android.R.layout.simple_spinner_item);
		final ArrayAdapter<CharSequence> hastighetEnheterAdapter = ArrayAdapter.createFromResource(StartPoint.this, R.array.hastighetEnheter, android.R.layout.simple_spinner_item);
		final ArrayAdapter<CharSequence> areaEnheterAdapter = ArrayAdapter.createFromResource(StartPoint.this, R.array.areaEnheter, android.R.layout.simple_spinner_item);
		
		//Set ArrayAdapter Dropdown
		setDropDown(volymEnheterAdapter);
		setDropDown(streckaEnheterAdapter);
		setDropDown(tidEnheterAdapter);
		setDropDown(hastighetEnheterAdapter);
		setDropDown(areaEnheterAdapter);
		setDropDown(valueTypeAdapter);
		
		//Standard konverterare
		valueTypeSpinner.setAdapter(valueTypeAdapter);

		//Skapa olika konverterare
		valueTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == 0){
					//Om 0 så vill vi konvertera volymer
					spinner1.setAdapter(volymEnheterAdapter);
					spinner2.setAdapter(volymEnheterAdapter);
				}
				else if (arg2 == 1){
					//om 1 så vill vi konvertera areor
					spinner1.setAdapter(areaEnheterAdapter);
					spinner2.setAdapter(areaEnheterAdapter);
				}
				else if (arg2 == 2){
					// Om 2 vill vi konvertera Streckor
					spinner1.setAdapter(streckaEnheterAdapter);
					spinner2.setAdapter(streckaEnheterAdapter);
				}
				else if (arg2 == 3){
					//Om 3 vill vi konvertera hastigheter
					spinner1.setAdapter(hastighetEnheterAdapter);
					spinner2.setAdapter(hastighetEnheterAdapter);
				}
				else {
					//om 4 så konverteras Tid
					spinner1.setAdapter(tidEnheterAdapter);
					spinner2.setAdapter(tidEnheterAdapter);
				}
				spinner2.setSelection(1);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}	
		});
		
		
		//Räknaren
		//Lyssna efter klick på den undre knappen
		bottomButton = (LinearLayout) findViewById(R.id.bottomButton);
		bottomButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String enhet1 = spinner1.getItemAtPosition(spinner1.getSelectedItemPosition()).toString();
				String enhet2 = spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString();
				if (editTextInValue.getText().toString().matches("\\d")){
					double value = Double.parseDouble(editTextInValue.getText().toString());
					String output = Calculate(valueTypeSpinner.getItemAtPosition(valueTypeSpinner.getSelectedItemPosition()).toString(), enhet1, enhet2, value);
				}
				else {
					Context context = getApplicationContext();
					CharSequence text = "Inget värde angivet";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				Context context = getApplicationContext();
				CharSequence text = enhet1 + " konverteras till "+ enhet2;
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();	
			}
		});
	}
	
	public void setDropDown(ArrayAdapter ArrayAdapter) {
		ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	
	public String Calculate(String type, String fran, String till, double value) {
		if (type.equals("Volym")){
			Context context = getApplicationContext();
			CharSequence text = "Gör funktionen för " + type;
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			return null;
		}
		else if (type.equals("Area")){
			Context context = getApplicationContext();
			CharSequence text = "Gör funktion för " + type;
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			return null;
		}
		else {
			return null;
		}
	}
	public String Explaination(String output){
		
		return null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_point, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.Om:
			Intent intent = new Intent(this, omAppen.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
			return true;
		case R.id.action_settings:
			Context context = getApplicationContext();
			CharSequence text = "Inställningarna excisterar bara i parallella universum.\n\nInställningar kommer i framtida versioner.";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
			if( v != null) v.setGravity(Gravity.CENTER);
			toast.show();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
