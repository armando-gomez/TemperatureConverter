package com.armandogomez.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	private boolean isFahrenheit;
	private RadioGroup rg;
	private TextView input_degree_symbol;
	private TextView input_label_text_view;
	private TextView convert_label_text_view;
	private TextView convert_degree_symbol;
	private EditText input_edit_text;
	private TextView convert_text_view;
	private char symbol = (char) 0x00B0;
	private ArrayList<String> convert_history;
	private TextView history_text_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		isFahrenheit = true;

		input_label_text_view = findViewById(R.id.input_label_text_view);
		input_degree_symbol = findViewById(R.id.input_label_degree_symbol);

		convert_label_text_view = findViewById(R.id.convert_label_text_view);
		convert_degree_symbol = findViewById(R.id.convert_label_degree_symbol);

		input_edit_text = findViewById(R.id.input_edit_text);
		convert_text_view = findViewById(R.id.convert_text_view);

		input_label_text_view.setText("Fahrenheit Degrees:");
		convert_label_text_view.setText("Celsius Degrees:");

		input_degree_symbol.setText( symbol + "F");
		convert_degree_symbol.setText(symbol + "C");

		convert_history = new ArrayList();
		history_text_view = findViewById(R.id.history_text_view);
		history_text_view.setMovementMethod(new ScrollingMovementMethod());

		rg = findViewById(R.id.radio_group_temperature_scales);

		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.radio_fahrenheit:
						isFahrenheit = true;
						input_label_text_view.setText("Fahrenheit Degrees:");
						input_degree_symbol.setText( symbol + "F");
						convert_label_text_view.setText("Celsius Degrees:");
						convert_degree_symbol.setText(symbol + "C");
						break;
					case R.id.radio_celsius:
						isFahrenheit = false;
						input_label_text_view.setText("Celsius Degrees:");
						input_degree_symbol.setText( symbol + "C");
						convert_label_text_view.setText("Fahrenheit Degrees:");
						convert_degree_symbol.setText(symbol + "F");
						break;
				}
			}
		});

	}

	public void convertTemp(View v) {
		input_edit_text.clearFocus();
		if(isFahrenheit) {
			double start_temp = Double.parseDouble(input_edit_text.getText().toString());
			double convert_temp = (start_temp - 32.0)/1.8;
			convert_text_view.setText(String.format("%.1f", convert_temp));
			updateHistory(start_temp, "F", convert_temp, "C");
		} else {
			double start_temp = Double.parseDouble(input_edit_text.getText().toString());
			double convert_temp = (start_temp * 1.8) + 32.0;
			convert_text_view.setText(String.format("%.1f", convert_temp));
			updateHistory(start_temp, "C", convert_temp, "F");
		}
	}

	public void updateHistory(double start, String start_scale, double end, String end_scale) {
		String entry = String.format("%s to %s: %.1f -> %.1f%n", start_scale, end_scale, start, end);
		convert_history.add(entry);
		printHistory();
	}

	public void printHistory() {
		StringBuilder sb = new StringBuilder();
		for(String s: convert_history) {
			sb.append(s);
		}
		history_text_view.setText(sb);
	}

	public void clearHistory(View v) {
		history_text_view.setText("");
		convert_history = new ArrayList<>();
	}
}
