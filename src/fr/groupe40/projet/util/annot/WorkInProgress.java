package fr.groupe40.projet.util.annot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation simply made for a better view and management of the code
 *
 */
@Retention(RetentionPolicy.SOURCE)	//Information only kept into the source code
@Target({	//TARGET|=> Method -> method, Type -> Class, -> Field -> field
		ElementType.PACKAGE,
		ElementType.TYPE, 
		ElementType.METHOD, 
		ElementType.FIELD, 
	})	//Usable only with ...
@Documented
public @interface WorkInProgress {
	
	/**
	 * Is this enabled for testing ? Had to work further on it
	 */
	public boolean enabled() default true;
	/**
	 * Any useful comment
	 */
	String comment() default "Currently in Progress";
	/**
	 * Who has made this annotation
	 */
	String author() default "unknow";
	
}
