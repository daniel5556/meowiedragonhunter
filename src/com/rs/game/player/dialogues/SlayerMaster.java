package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.actions.Slayer;
import com.rs.game.player.actions.Slayer.Master;
import com.rs.game.player.actions.Slayer.SlayerTask;
import com.rs.utils.ShopsHandler;

public class SlayerMaster extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		Master master = player.getSlayerMaster();
		if (master == null)
			player.setSlayerMaster(Master.SPRIA);
		sendEntityDialogue(SEND_1_TEXT_CHAT,new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,"Good day, How may I help you?" }, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		Master master = player.getSlayerMaster();	
		if (stage == -1) {			
			if (player.getSlayerTask() != null) {
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,"How many monsters do I have left?","What do you have in your shop?", "Give me a tip.", "Please cancel my task","Nothing, Nevermind.");
				stage = 0;
			} 
			else {
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,"Please give me a task.","What do you have in your shop?", "Give me a tip.","Nothing, Nevermind.");					
				stage = 1;
			}
		} 
		if (componentId == OPTION_1) {
			SlayerTask task = player.getSlayerTask();
			if (npcId == master.getMaster() && stage == 0 ) {
				sendNPCDialogue(npcId, 9827, "You're current assigned to kill "+ task.getName().toLowerCase()+ " only " + task.getAmount()+ " more to go.");
			} 
			else if (stage == 0 ) {
				sendNPCDialogue(npcId, 9827, "I'm not your slayer master, i don't have that information");
				end();
			}
			else {
				Slayer.submitRandomTask(player);
				sendNPCDialogue(npcId, 9827, "Your new task is to kill "+ player.getSlayerTask().getName().toLowerCase() + ".");
				stage = -1;
			}				
		} 
		else if (componentId == OPTION_2) {
			sendNPCDialogue(npcId, 9827, "I have multiple items for sale.");					
			ShopsHandler.openShop(player, 29);
			end();
		} 
		else if (componentId == OPTION_3) {
			sendNPCDialogue(npcId, 9827, "I currently dont have any tips for you now.");
			stage = -1;
		} 
		else if (componentId == OPTION_4 && stage == 0) {
			player.setSlayerTask(null);
			sendNPCDialogue(npcId, 9827, "Your task has been cleared");
			stage = -1;
		} 
		else if (componentId == OPTION_5 || componentId == OPTION_4 && stage == 1) {
			end();
		}
	}	

	@Override
	public void finish() {

	}

}
