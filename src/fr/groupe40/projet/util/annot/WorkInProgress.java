package fr.groupe40.projet.util.annot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//TARGET|=> Method -> method, Type -> Class, -> Field -> field
@Retention(RetentionPolicy.SOURCE)	//Information only kept into the source code
@Target({
		ElementType.PACKAGE,
		ElementType.TYPE, 
		ElementType.METHOD, 
		ElementType.FIELD, 
	})	//Usable only with ...
@Documented
public @interface WorkInProgress {
	
	public boolean enabled() default true;
	String comment() default "Currently in Progress";
	String author() default "unknow";
	
}
