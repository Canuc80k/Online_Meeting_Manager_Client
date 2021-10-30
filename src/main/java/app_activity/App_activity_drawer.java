package app_activity;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.PieStyler.LabelType;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.colors.BaseSeriesColors;

import init.Font_init;

public class App_activity_drawer {
	private static final String APP_ACTIVITY_DATA_SPLIT_SIGNAL = "_ -.,1sdSA22efa,.- _";
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	private static PieChart pieChart;
	
	public synchronized void draw_pieChart_tofile(String data, String file_path) throws Exception {
		buildPieChart(data);
		drawAndSaveToFile(file_path);
	}
	
	public static synchronized void buildPieChart(String data) {		
		pieChart = new PieChartBuilder().width(WIDTH).height(HEIGHT).title("Bản Thống Kê").theme(ChartTheme.GGPlot2).build();
	    Color[] sliceColors =
	            new Color[] {
	              new Color(224, 68, 14),
	              new Color(230, 105, 62),
	              new Color(236, 143, 110),
	              new Color(243, 180, 159),
	              new Color(246, 199, 182)
	            };
	    pieChart.getStyler().setSeriesColors(sliceColors);
	    pieChart.getStyler().setLabelType(LabelType.Value);
	    pieChart.getStyler().setLabelsDistance(.82);
	    pieChart.getStyler().setDecimalPattern("#");
	    pieChart.getStyler().setChartTitleFont(Font_init.SanFranciscoDisplay_Medium.deriveFont(30f));
	    pieChart.getStyler().setLegendFont(Font_init.SanFranciscoDisplay_Medium.deriveFont(20f));
	    pieChart.getStyler().setToolTipFont(Font_init.SanFranciscoDisplay_Medium.deriveFont(20f));
	    pieChart.getStyler().setToolTipsAlwaysVisible(false);
	    
	    pieChart.getStyler().setSeriesColors(new BaseSeriesColors().getSeriesColors());
	    
		List<String> data_list = Arrays.asList(data.trim().split("\n"));
		for (int i = 0; i < data_list.size(); i ++) {
			String name = Arrays.asList(data_list.get(i).split(APP_ACTIVITY_DATA_SPLIT_SIGNAL)).get(0).trim();
			Double value = Double.parseDouble(Arrays.asList(data_list.get(i).split(APP_ACTIVITY_DATA_SPLIT_SIGNAL)).get(1).trim());
			
			pieChart.addSeries(name, value);
		}
	}
	
	public static synchronized void drawAndSaveToFile(String file_path) throws Exception {
		BitmapEncoder.saveBitmap(pieChart, file_path, BitmapFormat.PNG);
	}
}
