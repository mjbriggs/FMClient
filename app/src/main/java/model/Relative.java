package model;

public class Relative {
    private Person relative;
    private String relationship;

    public Relative(Person relative, String relationship){
        this.relative = relative;
        this.relationship = relationship;
    }

    public Person getRelative() {
        return relative;
    }

    public void setRelative(Person relative) {
        this.relative = relative;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
