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

	private int npcId;

	@Override
	public void start() {
		/*if (Settings.ECONOMY) {
			player.getPackets().sendGameMessage("The Master of Monster Teleports is in no mood to talk to you.");
			end();
			return;
		}*/
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Hello, I can teleport you to any monsters, would you like to go somewhere?" }, IS_NPC, npcId, 13959);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(), "Sure, why not." },
					IS_PLAYER, player.getIndex(), 13959);
			stage = 1;
		} else if (stage == 1) {
			sendOptionsDialogue("Where would you like to go?", "Rock Crabs", "Taverly Dungeon", "Slayer Tower", "Ice Strykewyrms", "Forinthry Dungeon");
			stage = 2;
		} else if (stage == 2) {
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
	}
	@Override
	public void finish() {

	}
}