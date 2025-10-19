package shadersmod.client;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/SVertexFormat.class */
public class SVertexFormat {
    public static final int vertexSizeBlock = 14;
    public static final int vertexSizeBlockVanilla = 7;
    public static final int vertexSizeItem = 13;
    public static final int vertexSizeItemVanilla = 7;
    public static final SVertexElement MIDUV_2F = new SVertexElement(0, a.a, SVertexElementUsage.MIDUV, 2);
    public static final SVertexElement TANGENT_4S = new SVertexElement(0, a.e, SVertexElementUsage.TANGENT, 4);
    public static final SVertexElement ENTITY_4S = new SVertexElement(0, a.e, SVertexElementUsage.ENTITY, 4);
    public static final SVertexElement PADDING_2S = new SVertexElement(0, a.e, SVertexElementUsage.PADDING, 2);

    public static void onDefaultVertexFormatsClinit() {
        cdw.a.a();
        cdw.a.a(cdw.m).a(cdw.n).a(cdw.o).a(cdw.p).a(cdw.q).a(cdw.r).a(MIDUV_2F).a(TANGENT_4S).a(ENTITY_4S);
        cdw.b.a();
        cdw.b.a(cdw.m).a(cdw.n).a(cdw.o).a(PADDING_2S).a(cdw.q).a(cdw.r).a(MIDUV_2F).a(TANGENT_4S).a(ENTITY_4S);
        cdw.c.a(MIDUV_2F).a(TANGENT_4S);
    }

    public static void onAddElement(cdy vertexFormat, cdz element) {
        if (element instanceof SVertexElement) {
            int offset = vertexFormat.g();
            switch (((SVertexElement) element).getSVEUsage()) {
                case TANGENT:
                    Dummy.put_vertexFormat_tangentElementOffset(vertexFormat, offset);
                    break;
                case ENTITY:
                    Dummy.put_vertexFormat_entityElementOffset(vertexFormat, offset);
                    break;
                case MIDUV:
                    Dummy.put_vertexFormat_miduvElementOffset(vertexFormat, offset);
                    break;
            }
        }
    }
}
