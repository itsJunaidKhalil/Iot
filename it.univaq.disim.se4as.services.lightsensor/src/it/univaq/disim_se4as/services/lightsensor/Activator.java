package it.univaq.disim_se4as.services.lightsensor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import it.univaq.disim.se4as.services.sensor.api.ISensor;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		ISensor lightSensor = new LightSensor();
		context.registerService(ISensor.class.getName(), lightSensor , null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
