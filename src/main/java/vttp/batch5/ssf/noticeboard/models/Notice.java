package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
  @NotBlank(message = "Mandatory")
  @Size(min = 3, max = 128, message = "The Notice title's length must be between 3 and 128 characters.")
  private String title;

  @NotBlank(message = "Mandatory")
  @Email
  private String poster;

  @NotNull(message = "Mandatory")
  @Future(message = "Must be in future")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date postDate;

  @NotNull(message = "Mandatory")
  @Size(min=1,max=5,message="Must include at least 1 category")
  private List<String> categories;

  @NotBlank(message = "Mandatory")
  private String text;

  public Notice() {
  }

  public Notice(List<String> categories, Date postDate, String poster, String text, String title) {
    this.categories = categories;
    this.postDate = postDate;
    this.poster = poster;
    this.text = text;
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getPostDate() {
    return postDate;
  }

  public void setPostDate(Date postDate) {
    this.postDate = postDate;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }
}
