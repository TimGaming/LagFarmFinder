/*
 * Author: John "Ubertweakstor" Board
 * Date: 21/12/12 14:10
 * Description: XRay Detector Plugin
 */

package ubertweakstor.lagfarmfinder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LagFarmFinder extends JavaPlugin {
    
    static final Logger log = Logger.getLogger("Minecraft");

    /*
     * Name: onEnable Description: Called when plugin is enabled. Returns: None
     * Parameters: None Requirements: None
     */
    @Override
    public void onEnable() {
        log.info("Enabled.");
    }

    /*
     * Name: onDisable Description: Called when plugin is disabled. Returns:
     * None Parameters: None Requirements: None
     */
    @Override
    public void onDisable() {
        log.info("Disabled.");
    }

    // =====[Util]=====//

    /*
     * Name: onCommand Description: Called when a command has been executed
     * Returns: boolean Parameters: CommandSender sender, Command cmd, String
     * label, String[] args Requirements: None
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player ply = (Player) sender;
        if (label.equalsIgnoreCase("mobcount")){
            if (getServer().getPlayer(args[0])==null){
                ply.sendMessage(ChatColor.RED+"ERROR: Player Not Online");
                return true;
            }
            try{
                Integer.parseInt(args[1]);
            }catch (Exception ex){
                ply.sendMessage(ChatColor.RED+"Radius not valid.");
                return true;
            }
            ply.sendMessage(ChatColor.BLUE+"Number of mobs in a "+String.valueOf(args[1])+
                    " radius around "+ChatColor.BLUE+args[0]+": "+
                    ChatColor.RED+String.valueOf(getNumberOfEntitiesNear(getServer().getPlayer(args[0]), Integer.valueOf(args[1]))));
            return true;
        } else if(label.equalsIgnoreCase("findlag")){            
            HashMap<Player, Integer> top = new HashMap<Player, Integer>();
            for(Player p: getServer().getOnlinePlayers()){
                if (args[0] != null){
                    top.put(ply, getNumberOfEntitiesNear(p, Integer.valueOf(args[0])));
                }                
                else{
                    top.put(ply, getNumberOfEntitiesNear(p, 80));
                }
            }
            
            
            int maxValueInMap=(Collections.max(top.values()));  // This will return max value in the Hashmap
            for (Entry<Player, Integer> entry : top.entrySet()) {  // Itrate through hashmap
                if (entry.getValue()==maxValueInMap) {
                    ply.sendMessage(ChatColor.RED+entry.getKey().getName()+": "+String.valueOf(entry.getValue()));
                }
            }
            
        }
        return true;
    }
    
    public int getNumberOfEntitiesNear(Player p, int radius){
        try{
            return p.getNearbyEntities(radius, radius, radius).size();
        } catch (Exception ex){
            return -1;
        }        
    }
}
