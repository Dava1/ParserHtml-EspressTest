package espresstest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.runtime.Product;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {
private String url ="https://www.espressif.com/en/products";
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("Hello RCP World!");
		return IApplication.EXIT_OK;
	}
	
	@Override
	public void stop() {
		// nothing to do
	}
}
