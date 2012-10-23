package edu.unca.mpsolle.Demo;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class Demo extends JavaPlugin {
	/*
	 * This is called when your plug-in is enabled
	 */
	DemoLogger logger;

	@Override
	public void onEnable() {
		// create a logger and use it
		logger = new DemoLogger(this);
		logger.info("plugin enabled");

		// save the configuration file
		saveDefaultConfig();

		// Create the SampleListener
		new DemoListener(this);

		// set the command executor for sample
		this.getCommand("demo").setExecutor(new DemoCommandExecutor(this));
	}

	/*
	 * This is called when your plug-in shuts down
	 */
	@Override
	public void onDisable() {
		logger.info("plugin disabled");

	}

}
