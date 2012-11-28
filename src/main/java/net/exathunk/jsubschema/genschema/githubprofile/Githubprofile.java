package net.exathunk.jsubschema.genschema.githubprofile;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojects.PropertiesProjects;
import net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojects.PropertiesProjectsLike;

public class Githubprofile implements Cloneable, Serializable, GithubprofileLike {

    private String email;

    private PropertiesProjectsLike projects;

    @Override
    public boolean hasEmail() {
        return null != email;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean hasProjects() {
        return null != projects;
    }

    @Override
    public PropertiesProjectsLike getProjects() {
        return projects;
    }

    @Override
    public void setProjects(PropertiesProjectsLike projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Githubprofile{ ");
        if (email != null) sb.append("email='").append(email).append("', ");
        if (projects != null) sb.append("projects='").append(projects).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof GithubprofileLike) {
            GithubprofileLike other = (GithubprofileLike) o;
            if (email == null) { if (other.hasEmail()) { return false; } }
            else if (!email.equals(other.getEmail())) { return false; }
            if (projects == null) { if (other.hasProjects()) { return false; } }
            else if (!projects.equals(other.getProjects())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (email == null ? 0 : email.hashCode());
        result = 31 * result + (projects == null ? 0 : projects.hashCode());
        return result;
    }

    public Set<String> diff(GithubprofileLike other) {
            Set<String> s = new TreeSet<String>();
            if (email == null) { if (other == null || other.hasEmail()) { s.add("email"); } }
            else if (!email.equals(other.getEmail())) { s.add("email"); }
            if (projects == null) { if (other == null || other.hasProjects()) { s.add("projects"); } }
            else if (!projects.equals(other.getProjects())) { s.add("projects"); }
            return s;
    }

}
