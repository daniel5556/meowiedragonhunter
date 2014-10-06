package com.rs.game.player.content.farming;

import java.util.Iterator;
import java.io.Serializable;

import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.farming.Seeds.Seed;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.Settings;

public class FarmingSystem implements Serializable {
	
	private static final long serialVersionUID = 4641462661859309514L;
	
	/**
	 * @author Jake | Santa Hat @Rune-Server
	 */
	
	
	/**
	 * A list of all the Farming Allotments / Patches
	 */
	public static int[] farmingPatches = { 8552, 8553, 7848, 8151, 8550, 8551, 7847, 8150 };
	
	private Player player;
	/**
	 * Handles the seeds on patch
	 */
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void handleSeeds(int seedId, WorldObject object) {
		/*if (!canPlantSeeds(player, object)) {
			player.getPackets().sendGameMessage("You must clear the weeds before you may plant some seeds here.");
			return;
		}*/
		for (PatchStatus patch : player.getFarmingPatches()) {
			if (patch.getObjectId() == object.getId()) {
				player.getPackets().sendGameMessage("There is already something growing here.");
				return;
			}
		}
		for (Seeds.Seed seed : Seeds.Seed.values()) {
			if (seedId == seed.getItem().getId()) {
				if (player.getSkills().getLevel(Skills.FARMING) < seed.getLevel()) {
					player.getPackets().sendGameMessage("You need at least "+seed.getLevel()+" Farming in order to plant this.");
					return;
				}
				ObjectDefinitions defs = ObjectDefinitions.getObjectDefinitions(seedId);
				if (!player.getInventory().containsItem(seed.getItem().getId(), seed.getItem().getAmount())) {
					player.getPackets().sendGameMessage("You need at least "+seed.getItem().getAmount()+" "+defs.name+"'s.");
					return;
				}				
				for (int i = 0; i < seed.getSuitablePatch().length; i++) {
					if (seed.getSuitablePatch()[i] == object.getId()) {
						player.getPackets().sendGameMessage("You plant some "+defs.name+"'s.");
						player.getSkills().addXp(Skills.FARMING, seed.getPlantXP());
						player.getInventory().deleteItem(seed.getItem());
						player.setNextAnimation(new Animation(2291));
						player.getFarmingPatches().add(new PatchStatus(object.getId(), object.getDefinitions().configFileId, seed.getConfigValues()[0], seed.getConfigValues()[4], "Some "+defs.name+"'s have been planted here."));
						startGrowth(object, seed.getTime() / 2);
					}
				}
			}
		}
	}

	/**
	 * Is the Patch Raked? Can the Player plant seeds?
	 */
	private boolean canPlantSeeds(WorldObject object) {		
		for (WorldObject o : player.getRakedPatches()) {			
			if (object.getId() == o.getId()) 
				return true;
		}
		return false;
	}
	
	
	/**
	 * Starts the growth
	 * 5 Stages of Growth
	 */
	private void startGrowth(final WorldObject object, int time) {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (player.getFarmingPatches().size() == 0) { stop();
					return;	
				}
				PatchStatus status = null;
				for (PatchStatus patch : player.getFarmingPatches()) {
					if (patch.getConfigId() == object.getDefinitions().configFileId) {
						status = patch;
					}
				}
				if (status == null) { stop(); 
					return;
				}
				if ((status.getConfigValue() + 1) == status.getMaxConfigValue()) {
					player.getPackets().sendConfigByFile(status.getConfigId(), status.getMaxConfigValue());
					player.getPackets().sendGameMessage("<col=ff0000>[Farming] Your crops have fully grown.");
					stop();
				} else {
					player.getFarmingPatches().add(new PatchStatus(object.getId(), object.getDefinitions().configFileId, status.getConfigValue() + 1, status.getMaxConfigValue(), status.getInspectText()));
					player.getFarmingPatches().remove(status);
					player.getPackets().sendConfigByFile(status.getConfigId(), status.getConfigValue() + 1);
				}
			}
		}, 0, time);
	}
	
	/**
	 * Should the Player Rake or Harvest?
	 */
	public void executeAction(WorldObject object) {
		if (canHarvest(object)) 
			harvestCrops(object);
		else 
			rake(object);
	}
	
	/**
	 * Harvest's The Crops
	 */
	private void harvestCrops(WorldObject object) {
		if (canHarvest(object)) {
			player.getPackets().sendConfigByFile(object.getDefinitions().configFileId, 0);
			sendItems(object);
			for (Iterator<PatchStatus> patches = player.getFarmingPatches().iterator(); patches.hasNext();) {
				PatchStatus patch = patches.next();
				if (patch.getConfigId() == object.getDefinitions().configFileId) 
					patches.remove(); //Removes the Crops
			}
			for (Iterator<WorldObject> rakedPatches = player.getRakedPatches().iterator(); rakedPatches.hasNext();) {
				WorldObject rakedPatch = rakedPatches.next();
				if (rakedPatch.getId() == object.getId()) 
					rakedPatches.remove(); //Removes the Raked Patch
			}
		}
	}
	
	/**
	 * Sends the Farming crops to the Players Inventory
	 */
	private void sendItems(WorldObject object) {
		for (PatchStatus patch : player.getFarmingPatches()) {
			if (patch.getObjectId() == object.getId()) {
				for (Seeds.Seed seed : Seed.values()) {
					if (seed.getConfigValues()[4] == patch.getMaxConfigValue()) {
						ObjectDefinitions defs = ObjectDefinitions.getObjectDefinitions(seed.getProduce().getId());
						player.setNextAnimation(new Animation(2286));
						player.getInventory().addItem(seed.getProduce());
						player.getSkills().addXp(Skills.FARMING, seed.getHarvestXP());
						player.getPackets().sendGameMessage("You harvest the "+defs.name+"'s.");
					}
				}
			}
		}
	}
	/**
	 * Can the Player Harvest?
	 */
	public boolean canHarvest(WorldObject object) {
		for (PatchStatus patch : player.getFarmingPatches()) {
			if (patch.getConfigId() == object.getDefinitions().configFileId) {
				if ((patch.getConfigValue() + 1) == patch.getMaxConfigValue()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Sends the Configs Upon Login
	 */
	public void sendPatchOnLogin() {
		player.getRakedPatches().clear();
		for (PatchStatus patch : player.getFarmingPatches()) {
			continueGrowth();
		}
	}
	/**
	 * Continues the Growth of the crops when the player logs back in.
	 */
	public void continueGrowth() {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (player.getFarmingPatches().size() == 0) { stop();
					return;	
				}
				for (PatchStatus patch : player.getFarmingPatches()) {
					if ((patch.getConfigValue() + 1) == patch.getMaxConfigValue()) {
						player.getPackets().sendConfigByFile(patch.getConfigId(), patch.getMaxConfigValue());
						player.getPackets().sendGameMessage("[Farming] Your crops have fully grown.");
						stop();
					} else {
						player.getFarmingPatches().add(new PatchStatus(patch.getObjectId(), patch.getConfigId(), patch.getConfigValue() + 1, patch.getMaxConfigValue(), patch.getInspectText()));
						player.getPackets().sendConfigByFile(patch.getConfigId(), patch.getConfigValue() + 1);
						player.getFarmingPatches().remove(patch);
					}	
				}
			}
		}, 0, 10);
	}

	/**
	 * Rakes the patch
	 */
	private void rake(final WorldObject object) {
		if (!player.getInventory().containsItem(5341, 1)) {
			player.getPackets().sendGameMessage("You'll need a rake to get rid of the weeds.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			int configValue;
			@Override
			public void run() {
				player.lock();
				if (loop == 0 || loop == 3 || loop == 6) {	
					configValue++;
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(object.getDefinitions().configFileId, configValue);
					player.getInventory().addItem(6055, 1);
					player.getSkills().addXp(Skills.FARMING, 1);
				} else if (loop == 6){
					player.getRakedPatches().add(object);
					player.getPackets().sendGameMessage("You successfully clear all the weeds.");
				} else if (loop >= 7) {
					player.unlock();
					stop();
				}
				
			loop++;
			}
		}, 0, 1);
	}
	
	/**
	 * Right Click Patch - Inspect Option - Call this in ObjectHandler - Option2
	 */
	public void inspectPatch(WorldObject object) {
		if (player.getFarmingPatches().size() == 0) {
			player.getDialogueManager().startDialogue("SimpleMessage", "There is currently nothing growing here.");
			return;
		}
		for (PatchStatus patch : player.getFarmingPatches()) {
			if (object.getId() == patch.getObjectId()) {
				player.getDialogueManager().startDialogue("SimpleMessage", ""+patch.getInspectText());
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "There is currently nothing growing here.");
			}
		}
	}
	
}