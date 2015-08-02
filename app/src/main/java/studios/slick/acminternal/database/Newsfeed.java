package studios.slick.acminternal.database;

import com.orm.SugarRecord;

/**
 * Created by Darshan on 17/06/15.
 */
public class Newsfeed extends SugarRecord<Newsfeed> {

    public String postName;
    public String postContent;
    public String postImageUrl;

    public Newsfeed(String postName, String postContent, String postImageUrl) {
        this.postName = postName;
        this.postContent = postContent;
        this.postImageUrl = postImageUrl;
    }

    public String getPostName() {
        return postName;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }
}
