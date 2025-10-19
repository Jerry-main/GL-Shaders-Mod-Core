package shadersmod.client;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/STexturedQuad.class */
public class STexturedQuad {
    public static void draw(bqj quad, bui renderer, float scale) {
        bhc vec01 = quad.a[1].a.a(quad.a[0].a);
        bhc vec21 = quad.a[1].a.a(quad.a[2].a);
        bhc vecNormal = vec21.c(vec01).a();
        float fx = (float) vecNormal.b;
        float fy = (float) vecNormal.c;
        float fz = (float) vecNormal.d;
        if (Dummy.get_texturedQuad_invertNormal(quad)) {
            fx = -fx;
            fy = -fy;
            fz = -fz;
        }
        renderer.a(7, cdw.c);
        for (int i = 0; i < 4; i++) {
            brb positiontexturevertex = quad.a[i];
            renderer.b(positiontexturevertex.a.b * scale, positiontexturevertex.a.c * scale, positiontexturevertex.a.d * scale).a(positiontexturevertex.b, positiontexturevertex.c).c(fx, fy, fz).d();
        }
        SVertexBuffer.calcNormal(renderer, 0);
        bvc.a().b();
    }
}
