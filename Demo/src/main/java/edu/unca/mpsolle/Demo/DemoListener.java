package edu.unca.mpsolle.Demo;

import java.text.MessageFormat;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

/*
 * This is a sample event listener
 */
public class DemoListener implements Listener {
    private final Demo plugin;

    /*
     * This listener needs to know about the plugin which it came from
     */
    public DemoListener(Demo plugin) {
        // Register the listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        
        this.plugin = plugin;
    }

    /*
     * Send the sample message to all players that join
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(this.plugin.getConfig().getString("sample.message"));
    	plugin.setMetadata(event.getPlayer(), "prepare", false, plugin);
    }
    
    /*
     * Another example of a event handler. This one will give you the name of
     * the entity you interact with, if it is a Creature it will give you the
     * creature Id.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        final EntityType entityType = event.getRightClicked().getType();

        event.getPlayer().sendMessage(MessageFormat.format(
                "You interacted with a {0} it has an id of {1}",
                entityType.getName(),
                entityType.getTypeId()));
    }
	@EventHandler(priority = EventPriority.LOW)
	public void tntEvent(PlayerInteractEvent tnt) {
		Player jim = tnt.getPlayer();
		
		if (tnt.getAction() == Action.LEFT_CLICK_BLOCK && jim.getItemInHand().getType() == Material.DIAMOND_SWORD) {
			Block d = tnt.getClickedBlock();
			
			if (d != null) {
				Location loc = d.getLocation();
				int health = jim.getHealth();
				loc.getWorld().createExplosion(loc, 1, true) ;
				jim.setHealth(health);
				tnt.getPlayer().sendMessage("You have exploded the ground in front of you!");
				plugin.logger.info(tnt.getPlayer() + " created an explosion at  " + loc);
			}
		}	
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void arrowEvent(ProjectileHitEvent arrow) {
		Player jim = (Player) arrow.getEntity().getShooter();
		
		if (arrow.getEntityType() == EntityType.ARROW ){
				Location loc = arrow.getEntity().getLocation();
				jim.sendMessage("You are now where your arrow fell!");
				plugin.logger.info(arrow.getEntity().getShooter() + " shot an arrow to  " + loc);
				Location starting = jim.getLocation();
				jim.teleport(loc);
				starting.getWorld().strikeLightning(starting);
				World world = jim.getWorld();
				world.playEffect(starting, Effect.SMOKE, 4);
				world.playEffect(loc, Effect.SMOKE, 4);
		}	
	
	}
}
