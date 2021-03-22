package m2info.ter.decofo.classes;

import java.io.Serializable;

public class PasswordChange implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ancienMdp;

    private String nvMdp;

    public PasswordChange() {

    }

    public String getAncienMdp() {
        return ancienMdp;
    }

    public void setAncienMdp(String ancienMdp) {
        this.ancienMdp = ancienMdp;
    }

    public String getNvMdp() {
        return nvMdp;
    }

    public void setNvMdp(String nvMdp) {
        this.nvMdp = nvMdp;
    }
}
