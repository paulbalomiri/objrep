package eventmanagement.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//TODO: Using Compiler libs this might be reduced to RetentionPolicy.CLASS
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ConstructorArgumentRequirements {
	Class<?> [] value();
}
