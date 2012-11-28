package net.exathunk.jsubschema.genschema.githubprofile;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class GithubprofileFactory implements DomainFactory<Githubprofile> {

    @Override
    public Class<Githubprofile> getDomainClass() {
        return Githubprofile.class;
    }

    @Override
    public Githubprofile makeDomain() {
        return new Githubprofile();
    }

}
