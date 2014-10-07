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
			player.getPackets().sendGameMessage("Coming soon...",true);
			return;
		}
		else if (componentId == 4) {
			player.getPackets().sendGameMessage("Coming soon...",true);
			return;
		}
		else if (componentId == 6) {
			player.getPackets().sendGameMessage("Coming soon...",true);
			return;
		}
		else if (componentId == 8) {
			player.getPackets().sendGameMessage("Coming soon...",true);
			return;
		}
		else if (componentId == 10) {
			SlayerTask task = player.getSlayerTask();
			player.getPackets().sendGameMessage("You're currently assigned to kill "+ task.getName().toLowerCase()+ " only " + task.getAmount()+ " more to go.",true);
			return;
		}
		else if (componentId == 12) {
			player.getPackets().sendGameMessage("You currently have "+ player.credits +" credits");
			return;
		}
		else if (componentId == 14) {
			player.getPackets().sendGameMessage("Coming soon...",true);
			return;
		}
	}
}