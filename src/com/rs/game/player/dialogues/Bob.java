package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.utils.ShopsHandler;

public class Bob extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendOptionsDialogue("What do you wish to buy?",	"Tools", "Farming & Herblore", "Misc. Skill Items", "Outfits");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (componentId) {
			case OPTION_1:
				ShopsHandler.openShop(player, 39);
				end();
				break;
			case OPTION_2:
				ShopsHandler.openShop(player, 40);
				end();
				break;
			case OPTION_3:
				ShopsHandler.openShop(player, 41);
				end();
				break;
			case OPTION_4:
				ShopsHandler.openShop(player, 42);
				end();
				break;
			case OPTION_5:
				ShopsHandler.openShop(player, 43);
				end();
				break;			
			default:
				end();
			}
	}

	@Override
	public void finish() {

	}

}