package model;

import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Sample persistent model.
 *
 * @author Vyacheslav Rusakov
 * @since 17.06.2016
 */
public class Sample {

    @Id
    private String id;
    @Version
    private Long version;
    private String name;
    private int amount;

    public Sample() {
    }

    public Sample(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
