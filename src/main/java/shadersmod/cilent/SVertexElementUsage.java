package shadersmod.client;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/SVertexElementUsage.class */
public enum SVertexElementUsage {
    POSITION(b.a),
    NORMAL(b.b),
    COLOR(b.c),
    UV(b.d),
    MATRIX(b.e),
    BLEND_WEIGHT(b.f),
    PADDING(b.g),
    MIDUV("Mid UV"),
    TANGENT("Tangent"),
    ENTITY("Entity");

    private final String displayName;
    private final b usage;

    SVertexElementUsage(b vUsageIn) {
        this.displayName = vUsageIn.a();
        this.usage = vUsageIn;
    }

    SVertexElementUsage(String displayNameIn) {
        this.displayName = displayNameIn;
        this.usage = b.g;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public b getUsage() {
        return this.usage;
    }

    /* renamed from: shadersmod.client.SVertexElementUsage$1 */
    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/SVertexElementUsage$1.class */
    static /* synthetic */ class C00021 {

        /* renamed from: $SwitchMap$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage */
        static final /* synthetic */ int[] f1x6d449843 = new int[b.values().length];

        static {
            try {
                f1x6d449843[b.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1x6d449843[b.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1x6d449843[b.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1x6d449843[b.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1x6d449843[b.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1x6d449843[b.f.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1x6d449843[b.g.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static SVertexElementUsage toSVEUsage(b usage) {
        switch (C00021.f1x6d449843[usage.ordinal()]) {
            case 1:
                return POSITION;
            case 2:
                return NORMAL;
            case 3:
                return COLOR;
            case Shaders.ProgramSkyBasic /* 4 */:
                return UV;
            case Shaders.ProgramSkyTextured /* 5 */:
                return MATRIX;
            case Shaders.ProgramClouds /* 6 */:
                return BLEND_WEIGHT;
            case 7:
                return PADDING;
            default:
                return null;
        }
    }
}
