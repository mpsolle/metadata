package edu.unca.mpsolle.Demo;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class Demo extends JavaPlugin {
	/*
	 * This is called when your plug-in is enabled
	 */
	DemoLogger logger;
	DemoCommandExecutor executor;

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
	
	/*
	 * Basic Metadata setup
	 */
	
	//Allows the ability to set what is in the meta data field
	public void setMetadata(Player player, String key, Object value,
			Demo plugin) {
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}
	
	//Allows the retrieval of Metadata information
	public Object getMetadata(Player player, String key, Demo plugin) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(plugin.getDescription().getName())) {
				return (value.asBoolean());
			}
		}
		return null;
	}

		// TODO Auto-generated method stub
		
	}

