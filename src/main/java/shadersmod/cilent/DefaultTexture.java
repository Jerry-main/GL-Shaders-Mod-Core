package shadersmod.client;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/DefaultTexture.class */
public class DefaultTexture extends cdd {
    public DefaultTexture() {
        m1a(null);
    }

    /* renamed from: a */
    public void m1a(cen resourcemanager) {
        int[] aint = ShadersTex.createAIntImage(1, -1);
        ShadersTex.setupTexture(Dummy.invoke_iTextureObject_getMultiTexID(this), aint, 1, 1, false, false);
    }
}
