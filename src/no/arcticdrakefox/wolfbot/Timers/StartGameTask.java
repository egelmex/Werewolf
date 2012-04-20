package no.arcticdrakefox.wolfbot.Timers;

import java.util.TimerTask;

import no.arcticdrakefox.wolfbot.management.GameCore;
import no.arcticdrakefox.wolfbot.management.PlayerList;
import no.arcticdrakefox.wolfbot.model.WolfBotModel;

public class StartGameTask extends TimerTask {

	private WolfBotModel model;
	private PlayerList players;
	
	public StartGameTask (WolfBotModel model)
	{
		this.model = model;
		this.players = model.getPlayers();
	}
	
	@Override
	public void run()
	{
		players.reset();
		players.assignRoles();
		model.getWolfBot ().sendRoleMessages();
		model.getWolfBot ().setMode(model.getChannel (), "+m");
		
		// Verify that we have enough players to start during the night:
		if (model.isStartOnNight() && players.getList().size() < 4)
		{
			model.setStartOnNight(false);
			model.sendIrcMessage(model.getChannel(), "Night start disabled - not enough players (Need 4+)");
		}
		
		if (model.isStartOnNight())
		{
			GameCore.startNight (model);
		}
		else
		{
			GameCore.startDay(model);
		}
	}

}
