package group12.whosplayin;

import android.media.Image;

/**
 * Created by twohyjr on 3/5/16.
 */
public class Achievement {

    private String title = "";
    private String description = "";
    private Image image = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
