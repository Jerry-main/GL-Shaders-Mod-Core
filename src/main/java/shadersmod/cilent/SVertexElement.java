package shadersmod.client;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/SVertexElement.class */
public class SVertexElement extends cdz {
    SVertexElementUsage sveUsage;

    public SVertexElement(int indexIn, a typeIn, b usageIn, int count) {
        this(indexIn, typeIn, SVertexElementUsage.toSVEUsage(usageIn), count);
    }

    public SVertexElement(int indexIn, a typeIn, SVertexElementUsage sveUsageIn, int count) {
        super(indexIn, typeIn, sveUsageIn.getUsage(), count);
        this.sveUsage = sveUsageIn;
    }

    public final SVertexElementUsage getSVEUsage() {
        return this.sveUsage;
    }

    public boolean equals(Object p1) {
        if (this == p1) {
            return true;
        }
        if (p1 != null && getClass() == p1.getClass()) {
            SVertexElement ep1 = (SVertexElement) p1;
            return c() == ep1.c() && d() == ep1.d() && a() == ep1.a() && this.sveUsage == ep1.sveUsage;
        }
        return false;
    }

    public int hashCode() {
        int i = a().hashCode();
        return (((((i * 31) + getSVEUsage().hashCode()) * 31) + d()) * 31) + c();
    }
}
