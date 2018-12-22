package fr.groupe40.projet.util.constants;

import javafx.scene.paint.Color;

public enum ColorAI {
	AI_0 (Color.RED),
	AI_1 (Color.INDIGO),
	AI_2(Color.GOLD);
	
	
	
	
	private final Color color;
	private ColorAI(final Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
