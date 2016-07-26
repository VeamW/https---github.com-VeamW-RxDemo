package com.arif.cptest.models;

/**
 * Created by arifnadeem on 11/2/15.
 */
public class Movie {

    private String Released;

    private String Type;

    private String imdbVotes;

    private String Runtime;

    private String Response;

    private String Poster;

    private String imdbID;

    private String Country;

    private String Title;

    private String imdbRating;

    private String Year;

    private String Rated;

    private String Actors;

    private String Plot;

    private String Metascore;

    private String Writer;

    private String Genre;

    private String Language;

    private String Awards;

    private String Director;

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
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

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String metascore) {
        Metascore = metascore;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (Released != null ? !Released.equals(movie.Released) : movie.Released != null)
            return false;
        if (Type != null ? !Type.equals(movie.Type) : movie.Type != null) return false;
        if (imdbVotes != null ? !imdbVotes.equals(movie.imdbVotes) : movie.imdbVotes != null)
            return false;
        if (Runtime != null ? !Runtime.equals(movie.Runtime) : movie.Runtime != null) return false;
        if (Response != null ? !Response.equals(movie.Response) : movie.Response != null)
            return false;
        if (Poster != null ? !Poster.equals(movie.Poster) : movie.Poster != null) return false;
        if (imdbID != null ? !imdbID.equals(movie.imdbID) : movie.imdbID != null) return false;
        if (Country != null ? !Country.equals(movie.Country) : movie.Country != null) return false;
        if (Title != null ? !Title.equals(movie.Title) : movie.Title != null) return false;
        if (imdbRating != null ? !imdbRating.equals(movie.imdbRating) : movie.imdbRating != null)
            return false;
        if (Year != null ? !Year.equals(movie.Year) : movie.Year != null) return false;
        if (Rated != null ? !Rated.equals(movie.Rated) : movie.Rated != null) return false;
        if (Actors != null ? !Actors.equals(movie.Actors) : movie.Actors != null) return false;
        if (Plot != null ? !Plot.equals(movie.Plot) : movie.Plot != null) return false;
        if (Metascore != null ? !Metascore.equals(movie.Metascore) : movie.Metascore != null)
            return false;
        if (Writer != null ? !Writer.equals(movie.Writer) : movie.Writer != null) return false;
        if (Genre != null ? !Genre.equals(movie.Genre) : movie.Genre != null) return false;
        if (Language != null ? !Language.equals(movie.Language) : movie.Language != null)
            return false;
        if (Awards != null ? !Awards.equals(movie.Awards) : movie.Awards != null) return false;
        return !(Director != null ? !Director.equals(movie.Director) : movie.Director != null);

    }

    @Override
    public int hashCode() {
        int result = Released != null ? Released.hashCode() : 0;
        result = 31 * result + (Type != null ? Type.hashCode() : 0);
        result = 31 * result + (imdbVotes != null ? imdbVotes.hashCode() : 0);
        result = 31 * result + (Runtime != null ? Runtime.hashCode() : 0);
        result = 31 * result + (Response != null ? Response.hashCode() : 0);
        result = 31 * result + (Poster != null ? Poster.hashCode() : 0);
        result = 31 * result + (imdbID != null ? imdbID.hashCode() : 0);
        result = 31 * result + (Country != null ? Country.hashCode() : 0);
        result = 31 * result + (Title != null ? Title.hashCode() : 0);
        result = 31 * result + (imdbRating != null ? imdbRating.hashCode() : 0);
        result = 31 * result + (Year != null ? Year.hashCode() : 0);
        result = 31 * result + (Rated != null ? Rated.hashCode() : 0);
        result = 31 * result + (Actors != null ? Actors.hashCode() : 0);
        result = 31 * result + (Plot != null ? Plot.hashCode() : 0);
        result = 31 * result + (Metascore != null ? Metascore.hashCode() : 0);
        result = 31 * result + (Writer != null ? Writer.hashCode() : 0);
        result = 31 * result + (Genre != null ? Genre.hashCode() : 0);
        result = 31 * result + (Language != null ? Language.hashCode() : 0);
        result = 31 * result + (Awards != null ? Awards.hashCode() : 0);
        result = 31 * result + (Director != null ? Director.hashCode() : 0);
        return result;
    }
}
