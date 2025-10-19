package shadersmod.client;

import java.nio.ByteBuffer;
import java.util.List;
import org.lwjgl.opengl.GL20;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/SVertexBufferDrawer.class */
public class SVertexBufferDrawer extends buj {
    /* renamed from: a */
    public void m14a(bui vertexBuffer) {
        if (vertexBuffer.h() > 0) {
            cdy vertexformat = vertexBuffer.g();
            int stride = vertexformat.g();
            ByteBuffer bytebuffer = vertexBuffer.f();
            List<cdz> list = vertexformat.h();
            for (int i = 0; i < list.size(); i++) {
                SVertexElement vertexelement = (SVertexElement) list.get(i);
                SVertexElementUsage usage = vertexelement.getSVEUsage();
                int type = vertexelement.a().c();
                int index = vertexelement.d();
                bytebuffer.position(vertexformat.d(i));
                switch (C00011.$SwitchMap$shadersmod$client$SVertexElementUsage[usage.ordinal()]) {
                    case 1:
                        buq.b(vertexelement.c(), type, stride, bytebuffer);
                        buq.q(32884);
                        break;
                    case 2:
                        cig.l(cig.q + index);
                        buq.a(vertexelement.c(), type, stride, bytebuffer);
                        buq.q(32888);
                        cig.l(cig.q);
                        break;
                    case 3:
                        buq.c(vertexelement.c(), type, stride, bytebuffer);
                        buq.q(32886);
                        break;
                    case Shaders.ProgramSkyBasic /* 4 */:
                        buq.a(type, stride, bytebuffer);
                        buq.q(32885);
                        break;
                    case Shaders.ProgramSkyTextured /* 5 */:
                        GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, vertexelement.c(), type, false, stride, bytebuffer);
                        GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
                        break;
                    case Shaders.ProgramClouds /* 6 */:
                        GL20.glVertexAttribPointer(Shaders.tangentAttrib, vertexelement.c(), type, false, stride, bytebuffer);
                        GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
                        break;
                    case 7:
                        GL20.glVertexAttribPointer(Shaders.entityAttrib, vertexelement.c(), type, false, stride, bytebuffer);
                        GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
                        break;
                }
            }
            buq.f(vertexBuffer.i(), 0, vertexBuffer.h());
            for (int i2 = 0; i2 < list.size(); i2++) {
                SVertexElement element = (SVertexElement) list.get(i2);
                SVertexElementUsage usage2 = element.getSVEUsage();
                int index2 = element.d();
                switch (C00011.$SwitchMap$shadersmod$client$SVertexElementUsage[usage2.ordinal()]) {
                    case 1:
                        buq.p(32884);
                        break;
                    case 2:
                        cig.l(cig.q + index2);
                        buq.p(32888);
                        cig.l(cig.q);
                        break;
                    case 3:
                        buq.p(32886);
                        buq.I();
                        break;
                    case Shaders.ProgramSkyBasic /* 4 */:
                        buq.p(32885);
                        break;
                    case Shaders.ProgramSkyTextured /* 5 */:
                        GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
                        break;
                    case Shaders.ProgramClouds /* 6 */:
                        GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
                        break;
                    case 7:
                        GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
                        break;
                }
            }
        }
        vertexBuffer.b();
    }

    /* renamed from: shadersmod.client.SVertexBufferDrawer$1 */
    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/SVertexBufferDrawer$1.class */
    static /* synthetic */ class C00011 {
        static final /* synthetic */ int[] $SwitchMap$shadersmod$client$SVertexElementUsage = new int[SVertexElementUsage.values().length];

        static {
            try {
                $SwitchMap$shadersmod$client$SVertexElementUsage[SVertexElementUsage.POSITION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$shadersmod$client$SVertexElementUsage[SVertexElementUsage.UV.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$shadersmod$client$SVertexElementUsage[SVertexElementUsage.COLOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$shadersmod$client$SVertexElementUsage[SVertexElementUsage.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$shadersmod$client$SVertexElementUsage[SVertexElementUsage.MIDUV.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$shadersmod$client$SVertexElementUsage[SVertexElementUsage.TANGENT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$shadersmod$client$SVertexElementUsage[SVertexElementUsage.ENTITY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }
}
