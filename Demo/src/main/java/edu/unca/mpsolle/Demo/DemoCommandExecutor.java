package edu.unca.mpsolle.Demo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.logging.Logger;


import com.google.common.base.Joiner;

/*
 * This is a sample CommandExectuor
 */
public class DemoCommandExecutor implements CommandExecutor {
	private final Demo plugin;

	/*
	 * This command executor needs to know about its plugin from which it came
	 * from
	 */
	public DemoCommandExecutor(Demo plugin) {
		this.plugin = plugin;
	}

	/*
	 * On command set the sample message
	 */
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			return false;
		} else if (!(sender instanceof Player)) {
			return false;
			// the cake will appear on the ground but not
			// necessarily where the player is looking
		} else if (args[0].equalsIgnoreCase("cake")) {
			Player fred = (Player) sender;
			Location loc = fred.getLocation();
			World w = loc.getWorld();
			loc.setX(loc.getX() + 1);
			Block b = w.getBlockAt(loc);
			b.setTypeId(92);
			return true;
			// the stored message now always begins with
			// the word "message"--do you know how to easily
			// fix that problem?
		} else if (args[0].equalsIgnoreCase("message")
				&& sender.hasPermission("demo.message")) {
			this.plugin.getConfig().set("sample.message",
					Joiner.on(' ').join(args));
			return true;
		} else if (args[0].equalsIgnoreCase("damage") 
				&& sender.hasPermission("demo.damage")) {				
				Player fred = (Player) sender;
				ItemStack weapon = fred.getItemInHand();	
				Material type = weapon.getType();
				sender.sendMessage(ChatColor.RED + "You just did damage with " + type);
				plugin.logger.info("Weapon = " + weapon);									
				return true;
		} else if (args[0].equalsIgnoreCase("prepare")) {			
				Player fred = (Player) sender;
				
				/*
				 * Data Example
				 */
					//If the player has permission for the prepare command (currently set to everyone)
					//then it sets "prepare" to true and allows the person to run the command
					if(sender.hasPermission("demo.prepare")) {
							plugin.setMetadata(fred, "prepare", true, plugin);
					} else {
						//Else the player is sent a message containing why they can't use the command
							sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");;
						}
				
				//Sets itemCode variables
				int itemCode = 261;
				int itemCode2 = 262;
				int itemCode3 = 276;
				
				//Set's each variable to be of type "itemstack"
				ItemStack myItem = new ItemStack(itemCode);
				ItemStack myItem3 = new ItemStack(itemCode3);
				ItemStack myItem2 = new ItemStack(itemCode2, 64);
				
				//Prints message
				sender.sendMessage(ChatColor.RED + "Warning: The first item in your inventory just got deleted!");
				
				//Sets main weapon to Diamond Sword and adds a bow and 64 arrows to inventory
				fred.setItemInHand(myItem);
				fred.getInventory().addItem(myItem2);
				fred.getInventory().addItem(myItem3);
				plugin.logger.info("Successfully ran 'prepare'");	
			return true;
			
		} else if (args[0].equalsIgnoreCase("kick")
				&& sender.hasPermission("demo.kick")) {
			Player fred = plugin.getServer().getPlayer(args[1]);
			if (fred != null) {
				fred.kickPlayer("you've been kicked off!");
				sender.sendMessage(ChatColor.RED + args[1] + " was kicked off");
				plugin.logger.info(args[1] + " has been kicked off");
			} else {
				sender.sendMessage(ChatColor.RED + args[1]
						+ " is not logged on");
			}
			return true;
		} else {
			return false;
		}
	}


}
