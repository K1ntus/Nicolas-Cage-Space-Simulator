package fr.groupe40.projet.util.constants;

import fr.groupe40.projet.client.User;

public final class Players {

	public static final int neutral = 0, player = 1, ai = -1;
	
	public static final User neutral_user = new User(neutral, 0);
	public static final User ai_user = new User(ai, -1);
	public static final User human_user = new User(player, 1);

	public static final int pirate_id = -666;
	public static final int event_id = -2;
	public static final int sun_id = -3;
	public static final User pirate_user = new User(neutral, -666);
	public static final User event_user = new User(neutral, -2);
	public static final User sun_user = new User(neutral, -3);

}
