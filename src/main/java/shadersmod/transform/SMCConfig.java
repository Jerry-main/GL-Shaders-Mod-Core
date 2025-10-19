package shadersmod.transform;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/SMCConfig.class */
public class SMCConfig {
    static final boolean devName = false;
    static final boolean srgName = false;
    static final boolean obfName = true;

    public static Namer getNamer() {
        return new NamerObf();
    }
}
