package hu.yajwfs.model;


import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("genrelist")
    private GenreList genreList;

    private String name;
    private long id;
    @SerializedName("parentid")
    private long parentId;
    @SerializedName("haschildren")
    private boolean hasChildren;
    private int count;

    public Genre() {
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getParentId() {
        return parentId;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public int getCount() {
        return count;
    }

    public GenreList getGenreList() {
        return genreList;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreList=" + genreList +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", parentId=" + parentId +
                ", hasChildren=" + hasChildren +
                ", count=" + count +
                '}';
    }
}
