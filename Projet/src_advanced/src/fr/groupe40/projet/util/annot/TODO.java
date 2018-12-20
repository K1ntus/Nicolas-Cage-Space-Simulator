package fr.groupe40.projet.util.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Any helpful annotation to manage the code
 * I guess his name his saying everything,
 * but it's there to indicated few things to do
 * later in code
 */
@Retention(RetentionPolicy.SOURCE)	//Information only kept into the source code
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})	//Usable only with (see previous line)
public @interface TODO {
	/**
	 * The string resuming what had to be done
	 */
	String comment() default "idk what had to be done, but there's something";
	
	/**
	 * Author of the annotation
	 * It's not really important yet
	 */
	String author() default "unknow";
}
