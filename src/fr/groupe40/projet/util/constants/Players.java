package fr.groupe40.projet.util.constants;

import fr.groupe40.projet.client.User;

public final class Players {

	/*	PLAYERS	*/
	//public static final int max_number_of_ai = 1;
	public static final int neutral = 0, player = 1, ai = -1;
	
	public static final User neutral_user = new User(neutral, 0);
	public static final User event_user = new User(neutral, -2);
	public static final User ai_user = new User(ai, -1);
	public static final User human_user = new User(player, 1);
	public static final User sun_user = new User(neutral, -666);

}
