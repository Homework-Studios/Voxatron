package engine.scripting;

public class StoredScriptValue<Type> {

    public String identifier;
    public Type value;

    public StoredScriptValue(String identifier, Type value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Type getValue() {
        return value;
    }

    public void setValue(Type value) {
        this.value = value;
    }

}
