package com.arif.cptest.models;

import java.util.List;

/**
 * Created by arifnadeem on 11/2/15.
 */
public class SearchResults {

    private List<Search> Search;

    public List<Search> getSearch() {
        return Search;
    }

    public void setSearch(List<Search> search) {
        Search = search;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchResults that = (SearchResults) o;

        return !(Search != null ? !Search.equals(that.Search) : that.Search != null);

    }

    @Override
    public int hashCode() {
        return Search != null ? Search.hashCode() : 0;
    }
}
