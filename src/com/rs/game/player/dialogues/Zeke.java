package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.utils.ShopsHandler;

public class Zeke extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendOptionsDialogue("What do you wish to buy?",	"Melee items", "Range items", "Mage items", "Misc. Combat Items", "Potions & Food");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (componentId) {
			case OPTION_1:
				ShopsHandler.openShop(player, 34);
				end();
				break;
			case OPTION_2:
				ShopsHandler.openShop(player, 35);
				end();
				break;
			case OPTION_3:
				ShopsHandler.openShop(player, 36);
				end();
				break;
			case OPTION_4:
				ShopsHandler.openShop(player, 37);
				end();
				break;
			case OPTION_5:
				ShopsHandler.openShop(player, 38);
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