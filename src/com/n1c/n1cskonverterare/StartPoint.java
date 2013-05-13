package com.n1c.n1cskonverterare;

import java.text.DecimalFormat;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
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

public class StartPoint extends SherlockActivity {
	
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
		final ArrayAdapter<CharSequence> massaEnheterAdapter = ArrayAdapter.createFromResource(StartPoint.this, R.array.massaEnheter, android.R.layout.simple_spinner_item);
		final ArrayAdapter<CharSequence> temperaturAdapter = ArrayAdapter.createFromResource(StartPoint.this, R.array.temperaturEnheter, android.R.layout.simple_spinner_item);
		
		//Set ArrayAdapter Dropdown
		setDropDown(volymEnheterAdapter);
		setDropDown(streckaEnheterAdapter);
		setDropDown(tidEnheterAdapter);
		setDropDown(hastighetEnheterAdapter);
		setDropDown(areaEnheterAdapter);
		setDropDown(massaEnheterAdapter);
		setDropDown(valueTypeAdapter);
		setDropDown(temperaturAdapter);
		
		//Standard konverterare
		valueTypeSpinner.setAdapter(valueTypeAdapter);

		//Skapa olika konverterare
		valueTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
		
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == 0){
					//want to convert volumes
					spinner1.setAdapter(volymEnheterAdapter);
					spinner2.setAdapter(volymEnheterAdapter);
					
					explained = getString(R.string.volume_explained);
				}
				/* else if (arg2 == 1){
					//want to convert area
					spinner1.setAdapter(areaEnheterAdapter);
					spinner2.setAdapter(areaEnheterAdapter);
					
					explained = getString(R.string.area_explained);
				}
				else if (arg2 == 2){
					// want to convert length
					spinner1.setAdapter(streckaEnheterAdapter);
					spinner2.setAdapter(streckaEnheterAdapter);
					
					explained = getString(R.string.length_explained);
				} */
				else if (arg2 == 1){
					//want to convert speeds
					spinner1.setAdapter(hastighetEnheterAdapter);
					spinner2.setAdapter(hastighetEnheterAdapter);
					
					explained = getString(R.string.speeds_explained);
				}
				else if (arg2 == 2){
					//want to convert weight 
					spinner1.setAdapter(massaEnheterAdapter);
					spinner2.setAdapter(massaEnheterAdapter);
					
					explained = getString(R.string.weight_explained);
				}
				else if (arg2 == 3){
					//want to convert time
					spinner1.setAdapter(tidEnheterAdapter);
					spinner2.setAdapter(tidEnheterAdapter);
					
					explained = getString(R.string.time_explained);
				}
				else if (arg2 == 4){
					//want to convert temperatures
					spinner1.setAdapter(temperaturAdapter);
					spinner2.setAdapter(temperaturAdapter);
					
					explained = getString(R.string.temperature_explained);
				}
				spinner2.setSelection(1);
				explainTextView.setText(explained);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}	
		});
		
		
		//Counter
		//Listens for clicks on the button button
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
	
	public void setDropDown(ArrayAdapter<CharSequence> ArrayAdapter) {
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
			else if (fran.equals("liter")){
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

			//Return the values
			if (till.equals("cm^3")){return df.format(cm);} 
			else if(till.equals("dm^3")){return df.format(dm);}else if(till.equals("gallon")){return df.format(g);}else if(till.equals("liter")){return df.format(l);}
			else {return df.format(m);}
		}
		else if (type.equals("Temperatur")){
			double c, f, k;
			if (fran.equals("C")){
				c = value;
			}
			else if (fran.equals("F")){
				c = (value - 32)/1.8;
			}
			else //(fran.equals("K")){
			{
				c = value - 273.15;
			}
			
			
			//Tabell
			f = c * 1.8 + 32;
			k = c + 273.15;
			
			//Return
			if(till.equals("C")){return df.format(c);}
			else if(till.equals("F")){return df.format(f);}
			else /*(till.equals("K"))*/{return df.format(k);}
		}
		else if (type.equals("Tid")){
			double h, s, min, d, y;
			if (fran.equals("h")){
				s = value * 60 * 60;
			}
			else if (fran.equals("days")){
				s = value * 86400;
			}
			else if (fran.equals("min")){
				s = value * 60;
			}
			else if (fran.equals("years")){
				s = value * 31556926;
			}
			else {
				s = value;
			}

			//Tabell
			h = s / 3600;
			min = s / 60;
			d = s / 86400;
			y = s / 31556926;
			
			if (till.equals("min")){return df.format(min);} 
			else if(till.equals("h")){return df.format(h);}
			else if(till.equals("years")){return df.format(y);}
			else if(till.equals("days")){return df.format(d);}
			else {return df.format(s);}
		}
		else if (type.equals("Hastighet")){
			double ms, kmh, mph;
			if (fran.equals("m/s")){
				ms = value;
			}
			else if(fran.equals("km/h")){
				ms = value / 3.6;
			}
			else { //miles per hour
				ms = value * 0.44704;
			}
			
			//Tabell
			kmh = ms * 3.6;
			mph = ms / 0.44704;
			
			if (till.equals("miles/h")){return df.format(mph);} else if(till.equals("km/h")){return df.format(kmh);} else {return df.format(ms);}
		} 
		else if (type.equals("Massa")){
			double t, kg, g, hg, mg;
			if (fran.equals("ton")){
				g = value * 1000 * 1000;
			}
			else if (fran.equals("kg")){
				g = value * 1000;
			}
			else if (fran.equals("hg")){
				g = value * 100;
			}
			else if (fran.equals("mg")){
				g = value / 1000;
			}
			else {
				g = value;
			}
			
			//Tabell
			t = g / 1000000;
			kg = g / 1000;
			hg = g / 100;
			mg = g * 1000;
			
			//Returnera värden
			if (till.equals("ton")){return df.format(t);}else if (till.equals("kg")){return df.format(kg);}else if (till.equals("hg")){return df.format(hg);}else if (till.equals("mg")){return df.format(mg);}else {return df.format(g);}
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
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.start_point, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.Om:
			Intent intent = new Intent(this, omAppen.class);
			startActivity(intent);
			return true;
		/*case R.id.prefix:
			Context context = getApplicationContext();
			CharSequence text = "Inställningarna excisterar bara i parallella universum.\n\nInställningar kommer i framtida versioner.";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
			if( v != null) v.setGravity(Gravity.CENTER);
			toast.show();*/
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
