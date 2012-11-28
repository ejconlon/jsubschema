package net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojectsitems;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class PropertiesProjectsItems implements Cloneable, Serializable, PropertiesProjectsItemsLike {

    private String lastCommit;

    private Long numCommits;

    private String title;

    private String url;

    @Override
    public boolean hasLastCommit() {
        return null != lastCommit;
    }

    @Override
    public String getLastCommit() {
        return lastCommit;
    }

    @Override
    public void setLastCommit(String lastCommit) {
        this.lastCommit = lastCommit;
    }

    @Override
    public boolean hasNumCommits() {
        return null != numCommits;
    }

    @Override
    public Long getNumCommits() {
        return numCommits;
    }

    @Override
    public void setNumCommits(Long numCommits) {
        this.numCommits = numCommits;
    }

    @Override
    public boolean hasTitle() {
        return null != title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean hasUrl() {
        return null != url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PropertiesProjectsItems{ ");
        if (lastCommit != null) sb.append("lastCommit='").append(lastCommit).append("', ");
        if (numCommits != null) sb.append("numCommits='").append(numCommits).append("', ");
        if (title != null) sb.append("title='").append(title).append("', ");
        if (url != null) sb.append("url='").append(url).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof PropertiesProjectsItemsLike) {
            PropertiesProjectsItemsLike other = (PropertiesProjectsItemsLike) o;
            if (lastCommit == null) { if (other.hasLastCommit()) { return false; } }
            else if (!lastCommit.equals(other.getLastCommit())) { return false; }
            if (numCommits == null) { if (other.hasNumCommits()) { return false; } }
            else if (!numCommits.equals(other.getNumCommits())) { return false; }
            if (title == null) { if (other.hasTitle()) { return false; } }
            else if (!title.equals(other.getTitle())) { return false; }
            if (url == null) { if (other.hasUrl()) { return false; } }
            else if (!url.equals(other.getUrl())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (lastCommit == null ? 0 : lastCommit.hashCode());
        result = 31 * result + (numCommits == null ? 0 : numCommits.hashCode());
        result = 31 * result + (title == null ? 0 : title.hashCode());
        result = 31 * result + (url == null ? 0 : url.hashCode());
        return result;
    }

    public Set<String> diff(PropertiesProjectsItemsLike other) {
            Set<String> s = new TreeSet<String>();
            if (lastCommit == null) { if (other == null || other.hasLastCommit()) { s.add("lastCommit"); } }
            else if (!lastCommit.equals(other.getLastCommit())) { s.add("lastCommit"); }
            if (numCommits == null) { if (other == null || other.hasNumCommits()) { s.add("numCommits"); } }
            else if (!numCommits.equals(other.getNumCommits())) { s.add("numCommits"); }
            if (title == null) { if (other == null || other.hasTitle()) { s.add("title"); } }
            else if (!title.equals(other.getTitle())) { s.add("title"); }
            if (url == null) { if (other == null || other.hasUrl()) { s.add("url"); } }
            else if (!url.equals(other.getUrl())) { s.add("url"); }
            return s;
    }

}
