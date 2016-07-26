package com.arif.cptest.models;

/**
 * Created by arifnadeem on 11/2/15.
 */
public class Search {

    private String Year;

    private String Type;

    private String Poster;

    private String imdbID;

    private String Title;

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Search search = (Search) o;

        if (Year != null ? !Year.equals(search.Year) : search.Year != null) return false;
        if (Type != null ? !Type.equals(search.Type) : search.Type != null) return false;
        if (Poster != null ? !Poster.equals(search.Poster) : search.Poster != null) return false;
        if (imdbID != null ? !imdbID.equals(search.imdbID) : search.imdbID != null) return false;
        return !(Title != null ? !Title.equals(search.Title) : search.Title != null);

    }

    @Override
    public int hashCode() {
        int result = Year != null ? Year.hashCode() : 0;
        result = 31 * result + (Type != null ? Type.hashCode() : 0);
        result = 31 * result + (Poster != null ? Poster.hashCode() : 0);
        result = 31 * result + (imdbID != null ? imdbID.hashCode() : 0);
        result = 31 * result + (Title != null ? Title.hashCode() : 0);
        return result;
    }

}
