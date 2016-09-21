package homework.softuni.bg.homework4_1spotifyservice.model;

/**
 * Created by sogodi on 7.9.2016 г..
 */
public class SpotifyModel {
  private int id;
  private String title;
  private String artist;
  private String url;

  public SpotifyModel(int id, String title, String artist, String url) {
    this.id = id;
    this.title = title;
    this.artist = artist;
    this.url = url;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getUrl() { return url; }

  public void setUrl(String url) { this.url = url;}

}
