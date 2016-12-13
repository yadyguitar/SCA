package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GraficaControlador implements Initializable{
	@FXML Button cerrar1;
	@FXML LineChart<? , ?> grafica;
	@FXML CategoryAxis x;
	@FXML NumberAxis xm0;
	@FXML NumberAxis ym0;
	@FXML TableView<Double> datos;
	@FXML Label d10;
	@FXML Label d20;
	@FXML Label d30;
	@FXML Label d21;
	@FXML Label d31;
	@FXML Label d32;
	@FXML Label d43;
	private Double D10 = 0.0, D20 = 0.0, D30=0.0, D21 =0.0,D31 =0.0,D32 =0.0, D43 =0.0; 
	private Double pd10 = 0.0, pd20 =0.0, pd30=0.0, pd21=0.0, pd31=0.0, pd32=0.0, pd43=0.0;
	private Double mm0=0.0, mm1=0.0, mm2=0.0, mm3=0.0;
	int contador = 0;
	ScreensController myController; 
	ProyectoControlador pro = new ProyectoControlador();
	InicioControlador ini = new InicioControlador();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		XYChart.Series series = new XYChart.Series();	
		XYChart.Series series0 = new XYChart.Series();
		XYChart.Series series1 = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();
		XYChart.Series series3 = new XYChart.Series();
		for(int i=10;i<=240;i=i+10){
		
			momentosD(i);
			
			DecimalFormat df = new DecimalFormat("00.0000");
			d10.setText(String.valueOf(df.format(pd10/contador)));
			d20.setText(String.valueOf(df.format(pd20/contador)));
			d30.setText(String.valueOf(df.format(pd30/contador)));
			d21.setText(String.valueOf(df.format(pd21/contador)));
			d31.setText(String.valueOf(df.format(pd31/contador)));
			d32.setText(String.valueOf(df.format(pd32/contador)));
			d43.setText(String.valueOf(df.format(pd43/contador)));
			
			if(pro.momento==0){
				series.getData().add(new XYChart.Data(String.valueOf(i),mm0));
				series.setName("Momento 0");
				grafica.setTitle("Momento 0 (Longitud)");
			}
			if(pro.momento==1){
				series.getData().add(new XYChart.Data(String.valueOf(i),mm1));
				series.setName("Momento 1");
				grafica.setTitle("Momento 1 (Número)");
			}
			if(pro.momento==2){
				series.getData().add(new XYChart.Data(String.valueOf(i),mm2));
				series.setName("Momento 2");
				grafica.setTitle("Momento 2 (Masa)");
			}
			if(pro.momento==3){
				series.getData().add(new XYChart.Data(String.valueOf(i),(mm3)));
				series.setName("Momento 3");
				grafica.setTitle("Momento 3 (Volumen)");
			}
			if(pro.momento==4){
				grafica.setTitle("Todos los momentos");
				series0.getData().add(new XYChart.Data(String.valueOf(i),mm0/100));
				series0.setName("Momento 0 (x10+2");
				series1.getData().add(new XYChart.Data(String.valueOf(i),mm1/1000));
				series1.setName("Momento 1 (x10+3)");
				series2.getData().add(new XYChart.Data(String.valueOf(i),mm2/100000.0));
				series2.setName("Momento 2 (x10+5)");
				series3.getData().add(new XYChart.Data(String.valueOf(i),(mm3/10000000.0)));
				series3.setName("Momento 3 (x10+7)");
				
			}
			//momento 1
			//X - > i   y -> mm0
			//momento 2
			//X -> i   y ->mm1
			
			
			
		}
		if(pro.momento==4)
		{
			grafica.getData().addAll(series0);
			grafica.getData().addAll(series1);
			grafica.getData().addAll(series2);
			grafica.getData().addAll(series3);
			
		}else
			grafica.getData().addAll(series);
	}

	
	public void changeLocale() throws IOException{
		//ScreensController mainContainer = new ScreensController();
        //mainContainer.loadScreen(Framework.screen1ID, Framework.screen1File);
        //mainContainer.setScreen(Framework.screen1ID); 
		Framework.principal.close();
		new Framework().start(new Stage());
	}
	@FXML
	private void CerrarVentana1(){
		Stage stage = (Stage) cerrar1.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	@FXML
	public void tecla(KeyEvent e) throws IOException{
	
		if(e.getCode()==KeyCode.ESCAPE){
			CerrarVentana1();
		}
		
	}
	public void momentosD(int min){
		//-------------------------Cï¿½LCULO DE LOS MOMENTOS DE DISTRIBUCIï¿½N-----------------------------
       
        ArrayList<Integer> areas=new ArrayList<Integer>();
       // System.out.println(ini.t);
        areas.addAll(new Consultas().momentos(min,ini.t));
        if(areas.size()!=0){
        	contador ++;
        	System.out.println("cont: "+contador);
	        ArrayList<Double> d=new ArrayList<Double>();
	        for(int k=0;k<areas.size(); k++)
	        {
	        	
	        	Double r = (Math.sqrt(areas.get(k))/0.005)/40.0;
	        	 d.add(k, r);
	        }
	        Collections.sort(d);
	        //System.out.println("d: " +d);
	        ArrayList<Double> clase1=new ArrayList<Double>();
	        ArrayList<Double> clase2=new ArrayList<Double>();
	        Double inc = (d.get(d.size()-1)-d.get(0))/d.size();
	        System.out.println("inc: "+inc);
	        clase1.add(0, d.get(0));
	        for(int q=1;q<d.size();q++)
	        {
	        	clase1.add(q, clase1.get(q-1)+inc);
	        	clase2.add(q-1,clase1.get(q-1)+inc);
		
	        }
	        if(clase2.size()>0){
	        clase2.add(clase2.size(), clase2.get(clase2.size()-1)+inc);
	        }
	        ArrayList<Double> di=new ArrayList<Double>();
	        ArrayList<Double> di2=new ArrayList<Double>(); 
	        ArrayList<Double> di3=new ArrayList<Double>(); 
	        ArrayList<Double> di4=new ArrayList<Double>(); 
	        Double SD1 = 0.0;
	        Double SD2 = 0.0;
	        Double SD3 = 0.0;
	        Double SD4 = 0.0;
	       for(int q=0;q<clase2.size();q++)
	       {
	    	   di.add(q, (Math.sqrt((clase1.get(q)*clase2.get(q)))));
	    	   SD1=SD1 +di.get(q);
	    	   di2.add(q,Math.pow(di.get(q), 2));
	    	   SD2=SD2 +di2.get(q);
	    	   di3.add(q,Math.pow(di.get(q), 3));
	    	   SD3=SD3 +di3.get(q);
	    	   di4.add(q,Math.pow(di.get(q), 4));
	    	   SD4=SD4 +di4.get(q);
	       }
	       /*System.out.println(di);
	       System.out.println(di2);
	       System.out.println(di3);
	       System.out.println(di4);*/
	       System.out.println("D1: "+SD1+"D2: "+SD2+"D3: "+SD3+"D4: "+SD4);
	       //IMPORTAN----------------------------------
	       if(di.size()>0){
		       D10 = SD1 / di.size();
		       D20 = Math.sqrt((SD2/di.size()));
		       D30 = Math.pow((SD3/di.size()), 1.0/3.0);
		       D21 = SD2 /SD1;
		       D31 = Math.sqrt(SD3/SD1);
		       D32 = SD3/SD2;
		       D43 = SD4/SD3;
	       }
	       pd10 = pd10 + D10;
	       pd20 = pd20 + D20;
	       pd30 = pd30 + D30;
	       pd21 = pd21 + D21;
	       pd31 = pd31 + D31;
	       pd32 = pd32 + D32;
	       pd43 = pd43 + D43;

	       //---------------------------
	       System.out.println("1: "+D10);
	       System.out.println("2: "+D20);
	       System.out.println("3: "+D30);
	       System.out.println("4: "+D21);
	       System.out.println("5: "+D31);
	       System.out.println("6: "+D32);
	       System.out.println("7: "+D43);
	       ArrayList<Double> m0=new ArrayList<Double>(); 
	       ArrayList<Double> m1=new ArrayList<Double>(); 
	       ArrayList<Double> m2=new ArrayList<Double>(); 
	       ArrayList<Double> m3=new ArrayList<Double>(); 
	       Double kv=3.1416/6.0;
	       for(int q=0;q<areas.size();q++)
	       {
	    	   m3.add(q,(areas.get(q)/10.0)/1.588*Math.pow(1000, 3)/kv);
	    	   m2.add(q,m3.get(q)/D32);
	    	   m1.add(q, m2.get(q)/D21);
	    	   m0.add(q, m1.get(q)/D10);
	    	   mm0 = mm0 + m0.get(q);
	    	   mm1 = mm1 + m1.get(q);
	    	   mm2 = mm2 + m2.get(q);
	    	   mm3 = mm3 + m3.get(q);
	    	  
	       }
	       mm3 = mm3/m3.size();
	       mm2 = mm2/m2.size();
	       mm1 = mm1/m1.size();
	       mm0 = mm0/m0.size();
	       //IMPORTAN-----------------------
	      // System.out.println(m3);
	      // System.out.println(m2);
	      // System.out.println(m1);
	      // System.out.println(m0);
	       //IMPORTAN----------------------
        }
		
	}
}