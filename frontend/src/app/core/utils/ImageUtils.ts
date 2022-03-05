import {PostDto, PostImage} from "../models/profile.models";

export class ImageUtils {

  public static urlsToIMasonryGalleryImage(urls: string[]) {
    return urls.map(url => {
      return {imageUrl: url};
    });
  }

  public static postsToPostImage(posts: PostDto[]): PostImage[] {
    return posts.map(post => {
      return new PostImage(post.photo, post);
    });
  }

}
