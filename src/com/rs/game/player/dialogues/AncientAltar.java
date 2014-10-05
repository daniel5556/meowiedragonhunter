package com.rs.game.player.dialogues;

public class AncientAltar extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Which spellbook would you like?", "Normal", "Ancient", "Lunar");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			sendDialogue("The altar removes all recollection of other spellbooks.");
			player.getPackets().sendGameMessage("Your mind clears.",true);
			player.getCombatDefinitions().setSpellBook(0);
			end();
		} else if (componentId == OPTION_2) {
			sendDialogue("Zaros grips your mind empowering you with Ancient Spells.");
			player.getPackets().sendGameMessage("Your mind is gripped by the power of Zaros.",true);
			player.getCombatDefinitions().setSpellBook(1);
			end();
		} else if (componentId == OPTION_3) {
			sendDialogue("The teachings of the Lunar Mages grip your mind.");
			player.getPackets().sendGameMessage("Your mind is gripped by the Lunar teachings.",true);
			player.getCombatDefinitions().setSpellBook(2);
			end();
		} else {
			end();
		}
	}

	@Override
	public void finish() {

	}

}
