package com.n1c.n1cskonverterare;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
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
	String explained;
	TextView explainTextView, outputTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		
		//Skapa spinner
		valueTypeSpinner = (Spinner) findViewById(R.id.valueTypeSpinner);	
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		editTextInValue = (EditText) findViewById(R.id.inValue);
		
		//
		explainTextView = (TextView) findViewById(R.id.explain);
		outputTextView = (TextView) findViewById(R.id.output);
		
		
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
					
					explained = "1m^3 = 1000dm^3 = 1*10^6cm^3  0.26gallons = 1liter = 1dm^3";
				}
				/* else if (arg2 == 1){
					//om 1 så vill vi konvertera areor
					spinner1.setAdapter(areaEnheterAdapter);
					spinner2.setAdapter(areaEnheterAdapter);
					
					explained = "1m^2 = 100dm^2 = 10000 cm^2";
				}
				else if (arg2 == 2){
					// Om 2 vill vi konvertera Streckor
					spinner1.setAdapter(streckaEnheterAdapter);
					spinner2.setAdapter(streckaEnheterAdapter);
					
					explained = "1m = 10dm = 100cm";
				}
				else if (arg2 == 3){
					//Om 3 vill vi konvertera hastigheter
					spinner1.setAdapter(hastighetEnheterAdapter);
					spinner2.setAdapter(hastighetEnheterAdapter);
					
					explained = "1km/h = 1/3.6m/s\nc = 2.99herpm/s";
				} */
				else {
					//om 4 så konverteras Tid
					spinner1.setAdapter(tidEnheterAdapter);
					spinner2.setAdapter(tidEnheterAdapter);
					
					explained = "1h = 60min = 3600s";
				}
				spinner2.setSelection(1);
				explainTextView.setText(explained);
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
				if (editTextInValue.getText().toString().matches(".*\\d.*")){
					double value = Double.parseDouble(editTextInValue.getText().toString());
					String output = Calculate(valueTypeSpinner.getItemAtPosition(valueTypeSpinner.getSelectedItemPosition()).toString(), enhet1, enhet2, value).replace("E", "*10^");
					Toast toast = Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT);
					toast.show();
					outputTextView.setText(output + " " + enhet2);
				}
				else {
					Context context = getApplicationContext();
					CharSequence text = "Inget värde angivet";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				
			}
		});
	}
	
	public void setDropDown(ArrayAdapter ArrayAdapter) {
		ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	
	public String Calculate(String type, String fran, String till, double value) {
		DecimalFormat df = new DecimalFormat("###.####");
		if (type.equals("Volym")){
			double m, dm, cm, l, g;
			//konvertera "fran" till alla möjliga enheter 
			if (fran.equals("m^3")){
				m = value;
				dm = m * 1000;
				cm = dm * 1000;
				g = dm * 0.26;
				l = dm;
			}
			else if (fran.equals("gallon")){
				g = value;
				dm = 1/0.26 * value;
				l = dm;
				m = dm / 1000;
				cm = dm * 1000;
			}
			else if (fran.equals("dm^3")){
				dm = value;
				m = dm / 1000;
				cm = dm * 1000;
				g = dm * 0.26;
				l = dm;
			}
			else { //cm
				cm = value;
				dm = cm / 1000;
				m = dm / 1000;
				g = dm * 0.26;
				l = dm;
			}
			//convert to string
			df.format(cm);
			df.format(dm);
			df.format(m);
			df.format(g);
			df.format(l);
			
			//Return the values
			if (till.equals("cm^3")){return Double.toString(cm);} 
			else if(till.equals("dm^3")){return Double.toString(dm);}else if(till.equals("gallon")){return Double.toString(g);}else if(till.equals("liter")){return Double.toString(l);}
			else {return Double.toString(m);}
		}
		
		else if (type.equals("Tid")){
			double h, s, min;
			if (fran.equals("h")){
				h = value;
				min = h * 60;
				s = min * 60;
			}
			else if (fran.equals("min")){
				min = value;
				s = min * 60;
				h = min / 80;
			}
			else {
				s = value;
				min = s / 60;
				h = min / 60;
			}
			
			if (till.equals("s")){return df.format(s);} else if(till.equals("h")){return df.format(h);}else {return df.format(min);}
		}
		else {
			return "inte redo";
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
			return true;
		case R.id.prefix:
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
