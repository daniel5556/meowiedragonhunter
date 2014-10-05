package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.PlayerLook;

public class MakeOverMage extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendOptionsDialogue("What do you wish to change?",	"Gender", "Clothing", "Hair Style");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (componentId) {
			case OPTION_1:
				end();
				PlayerLook.openMageMakeOver(player);
				break;
			case OPTION_2:
				end();
				PlayerLook.openThessaliasMakeOver(player);
				break;
			case OPTION_3:
				end();
				PlayerLook.openHairdresserSalon(player);
				break;		
			default:
				end();
			}
	}

	@Override
	public void finish() {

	}
}
