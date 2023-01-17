package aurora.app;

import java.awt.Color;
import javax.swing.*;
import aurora.app.frames.MainWindow;
import com.formdev.flatlaf.FlatDarculaLaf;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Main {

	public static void main(String[] args) {

		try {
			FlatDarculaLaf.setup();
			UIManager.put("TabbedPane.showTabSeparators", false);
			UIManager.put("TabbedPane.selectedBackground", new Color(0, 0, 0, 13));
			UIManager.put("Component.focusWidth", 0);
			UIManager.put("Component.innerFocusWidth", 0);
		} catch (Exception ex) {
			System.err.println("Failed to load theme");
		}

		//CodeWindow window = new CodeWindow();
		MainWindow main = new MainWindow();
		startDiscordRPC();
	}

	public static void startDiscordRPC() {
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
				.setReadyEventHandler((user) -> {
					System.out.println("Welcome " + user.username + "#" + user.discriminator + "!");
				}).setDisconnectedEventHandler((errorCode, message) -> {
					int i = JOptionPane.showConfirmDialog(null, "O Aurora foi forçado a se desconectar do Discord pelo seguinte motivo:" +
							"\n" + message + "" +
							"\nError Code: " + errorCode + "" +
							"\n\nVocê deseja que o Aurora tente se reconectar?", "Reconectar ao Discord", 1);
					if (i == 1) {
						DiscordRPC.discordClearPresence();
						DiscordRPC.discordRunCallbacks();
					}
				}).build();
		DiscordRPC.discordInitialize("771079260525690900", handlers, true);
	}

	public static void update(String first, String second){
		DiscordRichPresence rich = new DiscordRichPresence
				.Builder(first)
				.setDetails(second)
				.setBigImage("aurora512", AppConstants.APP_NAME+" v"+AppConstants.VERSION)
				.build();
		DiscordRPC.discordUpdatePresence(rich);
	}

	public static void updateWithParty(String first, String second, String party, int min, int max, String secret){
		DiscordRichPresence rich = new DiscordRichPresence
				.Builder(first)
				.setDetails(second)
				.setParty(party, min, max)
				.setSecrets(party, secret)
				.setBigImage("aurora512", AppConstants.APP_NAME+" v"+AppConstants.VERSION)
				.build();
		DiscordRPC.discordUpdatePresence(rich);
	}
}
