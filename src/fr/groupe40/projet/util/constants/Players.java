package fr.groupe40.projet.util.constants;

import fr.groupe40.projet.client.User;

/**
 * Contains many constants about players, for usage.
 * But in a later version, it could be nice to change the way that users are managed
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public final class Players {

	public static final int neutral_faction = 0, human_faction = 1, ai_faction = -1;
	public static final int pirate_id = -666;
	public static final int event_id = -2;
	public static final int sun_id = -3;
	
	public static final User pirate_user = new User(neutral_faction, pirate_id);
	public static final User event_user = new User(neutral_faction, event_id);
	public static final User sun_user = new User(neutral_faction, sun_id);
	public static final User neutral_user = new User(neutral_faction, 0);
	public static final User ai_user = new User(ai_faction, -1);
	public static final User human_user = new User(human_faction, 1);

}
