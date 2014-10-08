package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;

public class TalentScout extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		/*if (Settings.ECONOMY) {
			player.getPackets().sendGameMessage("The Master of Skilling Teleports is in no mood to talk to you.");
			end();
			return;
		}*/
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Hello, I can teleport you to any Skilling Location, would you like to go somewhere?" }, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(), "Sure, why not." },
					IS_PLAYER, player.getIndex(), 9827);
			stage = 1;
		} else if (stage == 1) {
			sendOptionsDialogue("Where would you like to go?", "RuneSpan",
					"Mining Guild", "Living Rock Cavern", "Gnome Agility Course", "More Options");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3801, 3552, 0));
			}else if (componentId == OPTION_2){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3042, 9743, 0));
			}else if (componentId == OPTION_3){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3654, 5115, 0));
			}else if (componentId == OPTION_4){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2478, 3438, 0));
			}else if (componentId == OPTION_5) {
				stage = 3;
				sendOptionsDialogue("Where would you like to go?", "Barbarian Agility Course", "...", "...", "...", "More Options");
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2552, 3554, 0));
			} else if (componentId == OPTION_2){
			
			}else if (componentId == OPTION_3){
			
			}else if (componentId == OPTION_4){
				
			}else if (componentId == OPTION_5){
				stage = 2;
				sendOptionsDialogue("Where would you like to go?", "RuneSpan",
					"Mining Guild", "Living Rock Cavern", "Gnome Agility Course", "More Options");
			}
		}
	}

	@Override
	public void finish() {

	}
}