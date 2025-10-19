package shadersmod.client;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/MultiTexID.class */
public class MultiTexID {
    public int base;
    public int norm;
    public int spec;

    public MultiTexID(int baseTex, int normTex, int specTex) {
        this.base = baseTex;
        this.norm = normTex;
        this.spec = specTex;
    }
}
