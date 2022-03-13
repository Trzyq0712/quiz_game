package server;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private Integer energyConsumption;
    private String picturePath;

    /**
     * Constructor for an Activity
     * @param description - description of the activity
     * @param energyConsumption - energy consumption in watt-hours
     * @param picturePath - path to the image file of the activity
     */
    public Activity(String description, Integer energyConsumption, String picturePath) {
        this.description = description;
        this.energyConsumption = energyConsumption;
        this.picturePath = picturePath;
    }

    /**
     * Constructor for object mappers
     */
    @SuppressWarnings("unused")
    public Activity() {
        // for object mapper
    }


    /**
     * Compare whether two instances of a Activity are equal
     * All fields have to be equal for equality
     * @param obj - to be compared with
     * @return - whether the two objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Get a hash code of the Activity instance
     * @return - the hash code
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Get a string representation of the Activity instance
     * It is given in a multiline format
     * @return - the string representation of PlayerScore
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    /**
     * Getter for activity id
     * @return the activity id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for activity id
     * @param id - new id to be set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for activity description
     * @return the activity description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for activity description
     * @param description - new description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for energy consumption of the activity
     * @return the energy consumption in watt-hours
     */
    public Integer getEnergyConsumption() {
        return energyConsumption;
    }

    /**
     * Setter for energy consumption of the activity
     * @param energyConsumption - new energy consumption value in watt-hours
     */
    public void setEnergyConsumption(Integer energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    /**
     * Getter for the path of the picture of the activity
     * @return the file path to the picture
     */
    public String getPicturePath() {
        return picturePath;
    }

    /**
     * Setter for the path of the picture of the activity
     * @param picturePath - new picture path to be set
     */
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
