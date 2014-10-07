package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.actions.Slayer;
import com.rs.game.player.actions.Slayer.Master;
import com.rs.game.player.actions.Slayer.SlayerTask;

public class AssignTask extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		Master master = player.getSlayerMaster();
		SlayerTask task = player.getSlayerTask();
			if (player.getSlayerTask() != null) {
				sendNPCDialogue(npcId, 9827, "You're currently assigned to kill "+ task.getName().toLowerCase()+ " only " + task.getAmount()+ " more to go.");
			} else {
				Slayer.submitRandomTask(player);
				sendNPCDialogue(npcId, 9827, "Your new task is to kill "+ player.getSlayerTask().getName().toLowerCase() + ".");
			}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		end();
	}	

	@Override
	public void finish() {

	}

}
