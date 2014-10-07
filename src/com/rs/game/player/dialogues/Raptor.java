package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;

public class Raptor extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendOptionsDialogue("What do you want to see?",	"Monster Teleports", "Boss Teleports");		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (componentId) {
			case OPTION_1:
				stage = 0;
				break;
			case OPTION_2:
				stage = 1;
				break;
		}
		if (stage == 0) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { player.getDisplayName(), "Monsters please." },
				IS_PLAYER, player.getIndex(), 13959);
			stage = 2;
		} else if (stage == 2) {
			sendOptionsDialogue("Where would you like to go?", "Rock Crabs", "Taverly Dungeon", "Slayer Tower", "Ice Strykewyrms", "Forinthry Dungeon");
			stage = 4;
		} else if (stage == 4) {
			if (componentId == OPTION_1){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2684, 3725, 0));
			}else if (componentId == OPTION_2){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2884, 9798, 0));
			}else if (componentId == OPTION_3){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3427, 3539, 0));
			}else if (componentId == OPTION_4){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3434, 5651, 0));
			}else if (componentId == OPTION_5){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3037, 10170, 0));
			}	
		}
		else if (stage == 1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { player.getDisplayName(), "Bosses please." },
				IS_PLAYER, player.getIndex(), 9827);
			stage = 3;
		} else if (stage == 3) {
			sendOptionsDialogue("Where would you like to go?", "Bandos", "Armadyl", "Saradomin", "Zamorak", "More Options");
			stage = 5;
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2864, 5354, 2));
				player.getControlerManager().startControler("GodWars");
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2839, 5296, 2));
				player.getControlerManager().startControler("GodWars");
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2907, 5265, 0));
				player.getControlerManager().startControler("GodWars");
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2925, 5331, 2));
				player.getControlerManager().startControler("GodWars");
			} else if (componentId == OPTION_5) {
				stage = 7;
				sendOptionsDialogue("Where would you like to go?", "Nex", "Corporeal Beast", "Tormented Demons", "King Black Dragon", "More Options");
			}
		} else if (stage == 7) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2911, 5205, 0));
			} else if (componentId == OPTION_2)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2970, 4384, 0));
			else if (componentId == OPTION_3)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2604, 5748, 0));
			else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2273, 4682, 0));
			} else if (componentId == OPTION_5) {
				stage = 9;
				sendOptionsDialogue("Where would you like to go?", "Kalphite Queen", "Dagannoth kings", "More Options");
			}
		} else if (stage == 9) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3508, 9493, 0));
				player.getControlerManager().startControler("DuelControler");
			} else if (componentId == OPTION_2)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2900, 4449, 0));
			else if (componentId == OPTION_3) {
				stage = 5;
				sendOptionsDialogue("Where would you like to go?", "Bandos", "Armadyl", "Saradomin", "Zamorak", "More Options");
			}
		}
	}
	
	@Override
	public void finish() {

	}

}