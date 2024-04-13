package vttp.batch4.csf.toiletnearme.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

// @Document(indexName="employee", shards=1, replicas = 0, refreshInterval = "-1")
@Document(indexName="employee")
public class ToiletLocation {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String department;
    
    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", department="
                + department + "]";
    }
    public ToiletLocation(String id, String firstName, String lastName, String department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    
}
