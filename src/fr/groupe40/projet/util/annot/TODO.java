package fr.groupe40.projet.util.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)	//Information only kept into the source code
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})	//Usable only with (see previous line)
public @interface TODO {
	String comment() default "idk what had to be done, but there's something";
	String author() default "unknow";
}
