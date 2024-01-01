package main.collection.Metier;

public class Photo {
    private Integer id;
    private String imagePath;

    public Photo(String imagePath) {
        this.imagePath = imagePath;
    }

    public Photo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
