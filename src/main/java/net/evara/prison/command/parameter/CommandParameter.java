package net.evara.prison.command.parameter;

import lombok.Builder;
import lombok.Getter;
import net.evara.prison.command.provider.CommandProvider;

import java.util.List;

@Builder
@Getter
public class CommandParameter {

    private final String name;
    private final int index;
    private final boolean optional;
    private final boolean varArg;
    private final String defaultValue;
    private final String description;
    private final CommandProvider<?> provider;

    public List<String> tabComplete(){
        return this.provider.tabComplete();
    }

    public String toString(){
        if(isOptional()){
            return "[" + getName() + "]";
        } else {
            return "<" + getName() + ">";
        }
    }

}
