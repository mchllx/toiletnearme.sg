package vttp.batch4.csf.toiletnearme.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

// @Document(indexName="employee", shards=1, replicas = 0, refreshInterval = "-1")
// @Document(indexName="toiletLocation")
public class Coordinates {

    // @Id
    private String id;
    private String title;
    private Double lat;
    private Double lng;
    private String content;

    public Coordinates() {
    }
    
    public Coordinates(String id, String title, Double lat, Double lng, String content) {
        this.id = id;
        this.title = title;
        this.lat = lat;
        this.lng = lng;
        this.content = content;
    }
    @Override
    public String toString() {
        return "Coordinates [id=" + id + ", title=" + title + ", lat=" + lat + ", lng=" + lng + ", content="
                + content + "]";
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
 
}
