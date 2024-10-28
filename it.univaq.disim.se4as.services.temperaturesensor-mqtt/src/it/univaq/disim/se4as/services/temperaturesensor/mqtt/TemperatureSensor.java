package it.univaq.disim.se4as.services.temperaturesensor.mqtt;

import java.util.Random;

import it.univaq.disim.se4as.services.sensor.api.ISensor;
import it.univaq.disim.se4as.services.sensor.api.Data;

public class TemperatureSensor implements ISensor {
	Data data;
	
	public TemperatureSensor() {
		Random random = new Random();
		//int i = random.nextInt(35);
		int accuracy = random.nextInt(100);

		data = new Data();
		data.setUnit("�C");
		data.setAccuracy(accuracy);		
		new PahoImplCallBack(data).subscribe();
	}
	
	@Override
	public Data getData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	@Override
	public void setData(Data data) {
		this.data=data;
	}

}
