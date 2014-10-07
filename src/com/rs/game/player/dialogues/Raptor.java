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
		sendOptionsDialogue("What do you want to see?", "Monster Teleports", "Boss Teleports");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			switch (componentId) {
			case OPTION_1:
				sendPlayerDialogue(9827, "Monsters please");
				stage = 0;
				break;
			case OPTION_2:
				sendPlayerDialogue(9827, "Bosses please");
				stage = 2;
				break;
			}
			break;
		case 0:
			sendOptionsDialogue("Where would you like to go?", "Rock Crabs", "Taverly Dungeon", "Slayer Tower", "Ice Strykewyrms", "Forinthry Dungeon");
			stage = 1;
			break;
		case 1:
			switch (componentId) {
			case OPTION_1:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2684, 3725, 0));
				break;
			case OPTION_2:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2884, 9798, 0));
				break;
			case OPTION_3:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3427, 3539, 0));
				break;
			case OPTION_4:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3434, 5651, 0));
				break;
			case OPTION_5:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3037, 10170, 0));
				break;
			}
			break;
		case 2:
			sendOptionsDialogue("Where would you like to go?", "Bandos", "Armadyl", "Saradomin", "Zamorak", "More Options");
			stage = 3;
			break;
		case 3:
			switch (componentId) {
			case OPTION_1:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2864, 5354, 2));
				player.getControlerManager().startControler("GodWars");
				break;
			case OPTION_2:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2839, 5296, 2));
				player.getControlerManager().startControler("GodWars");
				break;
			case OPTION_3:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2907, 5265, 0));
				player.getControlerManager().startControler("GodWars");
				break;
			case OPTION_4:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2925, 5331, 2));
				player.getControlerManager().startControler("GodWars");
				break;
			case OPTION_5:
				sendOptionsDialogue("Where would you like to go?", "Nex", "Corporeal Beast", "Tormented Demons", "King Black Dragon", "More Options");
				stage = 4;
				break;
			}
			break;
		case 4:
			switch (componentId) {
			case OPTION_1:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2911, 5205, 0));
				break;
			case OPTION_2:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2970, 4384, 0));
				break;
			case OPTION_3:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2604, 5748, 0));
				break;
			case OPTION_4:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2273, 4682, 0));
				break;
			case OPTION_5:
				sendOptionsDialogue("Where would you like to go?", "Kalphite Queen", "Dagannoth kings", "More Options");
				stage = 5;
				break;
			}
			break;
		case 5:
			switch (componentId) {
			case OPTION_1:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3508, 9493, 0));
				break;
			case OPTION_2:
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2900, 4449, 0));
				break;
			case OPTION_3:
				sendOptionsDialogue("Where would you like to go?", "Bandos", "Armadyl", "Saradomin", "Zamorak", "More Options");
				stage = 3;
				break;
			}
		}
	}

	@Override
	public void finish() {

	}

}