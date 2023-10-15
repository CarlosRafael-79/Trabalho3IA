package trabalhoia;

public class Partition {
    public Integer attribute;
    public Integer group;
    public String definition;
    public String criteria;

    public Partition(Integer attribute, Integer group, String criteria, String definition) {
        this.attribute = attribute;
        this.group = group;
        this.criteria = criteria;
        this.definition = definition;
    }
}
