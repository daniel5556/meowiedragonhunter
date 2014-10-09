package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.player.actions.Slayer;
import com.rs.game.player.actions.Slayer.Master;
import com.rs.game.player.actions.Slayer.SlayerTask;


public class PlayerPanel {

	public static void handleButtons(Player player, int componentId) {
		if (componentId == 2) {
			player.getPackets().sendGameMessage("Ability to change password coming soon...",true);
			return;
		}
		else if (componentId == 4) {
			player.getPackets().sendGameMessage("Titles coming soon!",true);
			return;
		}
		else if (componentId == 6) {
			SlayerTask task = player.getSlayerTask();
			if (player.getSlayerTask() != null) {
				player.getPackets().sendGameMessage("You're currently assigned to kill "+ task.getName().toLowerCase()+ " only " + task.getAmount()+ " more to go.",true);
			} else {
				player.getPackets().sendGameMessage("You're not currently hunting anything. Please visit a slayer master.",true);
			return;
			}
		}
		else if (componentId == 8) {
			player.getPackets().sendGameMessage("Coming soon...",true);
			return;
		}
		else if (componentId == 10) {
			player.getPackets().sendGameMessage("You currently have "+ player.Loyaltypoints +" Loyalty Points.");
			return;
		}
		else if (componentId == 12) {
			player.getPackets().sendGameMessage("You currently have "+ player.credits +" Credits.");
			return;
		}
		else if (componentId == 14) {
			player.getPackets().sendGameMessage("You currently have "+ player.pkpoints +" Pk Points.");
			return;
		}
	}
}