package shadersmod.client;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/SVertexBuffer.class */
public class SVertexBuffer {
    long[] entityData = new long[10];
    int entityDataIndex = 0;
    boolean calcNormalEnabled;

    public SVertexBuffer() {
        this.entityData[0] = 0;
        this.calcNormalEnabled = false;
    }

    public static void onVertexBufferInit(bui vb) {
        Dummy.put_vertexBuffer_sVertexBuffer(vb, new SVertexBuffer());
    }

    public static void onVertexBufferBegin(bui vb) {
    }

    public static void enableCalcNormal(bui vb) {
        SVertexBuffer svb = Dummy.get_vertexBuffer_sVertexBuffer(vb);
        svb.calcNormalEnabled = true;
    }

    public static void onVertexBufferReset(bui vb) {
        SVertexBuffer svb = Dummy.get_vertexBuffer_sVertexBuffer(vb);
        svb.entityDataIndex = 0;
        svb.entityData[0] = 0;
    }

    public void pushEntity(long data) {
        this.entityDataIndex++;
        this.entityData[this.entityDataIndex] = data;
    }

    public void popEntity() {
        this.entityData[this.entityDataIndex] = 0;
        this.entityDataIndex--;
    }

    public static void pushEntity(awr blockState, et blockPos, amw blockAccess, bui wrr) {
        aou block = blockState.u();
        int blockID = aou.a(block);
        int renderType = block.a(block.t()).ordinal();
        int meta = block.e(blockState);
        int dataLo = ((renderType & 65535) << 16) + (blockID & 65535);
        int dataHi = meta & 65535;
        Dummy.get_vertexBuffer_sVertexBuffer(wrr).pushEntity((dataHi << 32) + dataLo);
    }

    public static void popEntity(bui wrr) {
        Dummy.get_vertexBuffer_sVertexBuffer(wrr).popEntity();
    }

    public static boolean popEntity(boolean value, bui wrr) {
        Dummy.get_vertexBuffer_sVertexBuffer(wrr).popEntity();
        return value;
    }

    public static void beginAddVertexData(bui vb, int[] data) {
        cdy vertexFormat = vb.g();
        if (Dummy.get_vertexFormat_entityElementOffset(vertexFormat) > 0) {
            SVertexBuffer svb = Dummy.get_vertexBuffer_sVertexBuffer(vb);
            long eData = svb.entityData[svb.entityDataIndex];
            for (int pos = Dummy.get_vertexFormat_entityElementOffset(vertexFormat) / 4; pos + 1 < data.length; pos += 14) {
                data[pos] = (int) eData;
                data[pos + 1] = (int) (eData >> 32);
            }
        }
    }

    public static void endAddVertexData(bui vb) {
    }

    public static void endEndVertex(bui vb) {
        cdy vertexFormat = vb.g();
        if (Dummy.get_vertexBuffer_vertexElementIndex(vb) != 0) {
            Dummy.put_vertexBuffer_vertexElementIndex(vb, 0);
            Dummy.put_vertexBuffer_vertexElement(vb, vertexFormat.c(0));
            SVertexBuffer svb = Dummy.get_vertexBuffer_sVertexBuffer(vb);
            if (Dummy.get_vertexFormat_entityElementOffset(vertexFormat) > 0) {
                long eData = svb.entityData[svb.entityDataIndex];
                int pos = ((vb.h() - 1) * vertexFormat.f()) + (Dummy.get_vertexFormat_entityElementOffset(vertexFormat) / 4);
                Dummy.get_vertexBuffer_rawIntBuffer(vb).put(pos, (int) eData);
                Dummy.get_vertexBuffer_rawIntBuffer(vb).put(pos + 1, (int) (eData >> 32));
            }
            if (svb.calcNormalEnabled && (vb.h() & 3) == 0 && vb.i() == 7) {
                calcNormal(vb, (vb.h() - 4) * vertexFormat.f());
            }
        }
    }

    public static void calcNormal(bui vb, int baseIndex) {
        cdy vertexFormat = vb.g();
        FloatBuffer floatBuffer = Dummy.get_vertexBuffer_rawFloatBuffer(vb);
        IntBuffer intBuffer = Dummy.get_vertexBuffer_rawIntBuffer(vb);
        int vertexSize = vertexFormat.f();
        int offsetUV = vertexFormat.b(0) / 4;
        int offsetNormal = vertexFormat.c() / 4;
        int offsetTangent = Dummy.get_vertexFormat_tangentElementOffset(vertexFormat) / 4;
        int offsetMidUV = Dummy.get_vertexFormat_miduvElementOffset(vertexFormat) / 4;
        float v0x = floatBuffer.get(baseIndex + (0 * vertexSize));
        float v0y = floatBuffer.get(baseIndex + (0 * vertexSize) + 1);
        float v0z = floatBuffer.get(baseIndex + (0 * vertexSize) + 2);
        float v0u = floatBuffer.get(baseIndex + (0 * vertexSize) + offsetUV);
        float v0v = floatBuffer.get(baseIndex + (0 * vertexSize) + offsetUV + 1);
        float v1x = floatBuffer.get(baseIndex + (1 * vertexSize));
        float v1y = floatBuffer.get(baseIndex + (1 * vertexSize) + 1);
        float v1z = floatBuffer.get(baseIndex + (1 * vertexSize) + 2);
        float v1u = floatBuffer.get(baseIndex + (1 * vertexSize) + offsetUV);
        float v1v = floatBuffer.get(baseIndex + (1 * vertexSize) + offsetUV + 1);
        float v2x = floatBuffer.get(baseIndex + (2 * vertexSize));
        float v2y = floatBuffer.get(baseIndex + (2 * vertexSize) + 1);
        float v2z = floatBuffer.get(baseIndex + (2 * vertexSize) + 2);
        float v2u = floatBuffer.get(baseIndex + (2 * vertexSize) + offsetUV);
        float v2v = floatBuffer.get(baseIndex + (2 * vertexSize) + offsetUV + 1);
        float v3x = floatBuffer.get(baseIndex + (3 * vertexSize));
        float v3y = floatBuffer.get(baseIndex + (3 * vertexSize) + 1);
        float v3z = floatBuffer.get(baseIndex + (3 * vertexSize) + 2);
        float v3u = floatBuffer.get(baseIndex + (3 * vertexSize) + offsetUV);
        float v3v = floatBuffer.get(baseIndex + (3 * vertexSize) + offsetUV + 1);
        float x1 = v2x - v0x;
        float y1 = v2y - v0y;
        float z1 = v2z - v0z;
        float x2 = v3x - v1x;
        float y2 = v3y - v1y;
        float z2 = v3z - v1z;
        float vnx = (y1 * z2) - (y2 * z1);
        float vny = (z1 * x2) - (z2 * x1);
        float vnz = (x1 * y2) - (x2 * y1);
        float lensq = (vnx * vnx) + (vny * vny) + (vnz * vnz);
        float mult = ((double) lensq) != 0.0d ? (float) (1.0d / Math.sqrt(lensq)) : 1.0f;
        float vnx2 = vnx * mult;
        float vny2 = vny * mult;
        float vnz2 = vnz * mult;
        float x12 = v1x - v0x;
        float y12 = v1y - v0y;
        float z12 = v1z - v0z;
        float u1 = v1u - v0u;
        float v1 = v1v - v0v;
        float x22 = v2x - v0x;
        float y22 = v2y - v0y;
        float z22 = v2z - v0z;
        float u2 = v2u - v0u;
        float v2 = v2v - v0v;
        float d = (u1 * v2) - (u2 * v1);
        float r = d != 0.0f ? 1.0f / d : 1.0f;
        float tan1x = ((v2 * x12) - (v1 * x22)) * r;
        float tan1y = ((v2 * y12) - (v1 * y22)) * r;
        float tan1z = ((v2 * z12) - (v1 * z22)) * r;
        float tan2x = ((u1 * x22) - (u2 * x12)) * r;
        float tan2y = ((u1 * y22) - (u2 * y12)) * r;
        float tan2z = ((u1 * z22) - (u2 * z12)) * r;
        float lensq2 = (tan1x * tan1x) + (tan1y * tan1y) + (tan1z * tan1z);
        float mult2 = ((double) lensq2) != 0.0d ? (float) (1.0d / Math.sqrt(lensq2)) : 1.0f;
        float tan1x2 = tan1x * mult2;
        float tan1y2 = tan1y * mult2;
        float tan1z2 = tan1z * mult2;
        float lensq3 = (tan2x * tan2x) + (tan2y * tan2y) + (tan2z * tan2z);
        float mult3 = ((double) lensq3) != 0.0d ? (float) (1.0d / Math.sqrt(lensq3)) : 1.0f;
        float tan2x2 = tan2x * mult3;
        float tan2y2 = tan2y * mult3;
        float tan2z2 = tan2z * mult3;
        float tan3x = (vnz2 * tan1y2) - (vny2 * tan1z2);
        float tan3y = (vnx2 * tan1z2) - (vnz2 * tan1x2);
        float tan3z = (vny2 * tan1x2) - (vnx2 * tan1y2);
        float tan1w = ((tan2x2 * tan3x) + (tan2y2 * tan3y)) + (tan2z2 * tan3z) < 0.0f ? -1.0f : 1.0f;
        int bnx = ((int) (vnx2 * 127.0f)) & 255;
        int bny = ((int) (vny2 * 127.0f)) & 255;
        int bnz = ((int) (vnz2 * 127.0f)) & 255;
        int packedNormal = (bnz << 16) + (bny << 8) + bnx;
        intBuffer.put(baseIndex + (0 * vertexSize) + offsetNormal, packedNormal);
        intBuffer.put(baseIndex + (1 * vertexSize) + offsetNormal, packedNormal);
        intBuffer.put(baseIndex + (2 * vertexSize) + offsetNormal, packedNormal);
        intBuffer.put(baseIndex + (3 * vertexSize) + offsetNormal, packedNormal);
        int packedTan1xy = (((int) (tan1x2 * 32767.0f)) & 65535) + ((((int) (tan1y2 * 32767.0f)) & 65535) << 16);
        int packedTan1zw = (((int) (tan1z2 * 32767.0f)) & 65535) + ((((int) (tan1w * 32767.0f)) & 65535) << 16);
        intBuffer.put(baseIndex + (0 * vertexSize) + offsetTangent, packedTan1xy);
        intBuffer.put(baseIndex + (0 * vertexSize) + offsetTangent + 1, packedTan1zw);
        intBuffer.put(baseIndex + (1 * vertexSize) + offsetTangent, packedTan1xy);
        intBuffer.put(baseIndex + (1 * vertexSize) + offsetTangent + 1, packedTan1zw);
        intBuffer.put(baseIndex + (2 * vertexSize) + offsetTangent, packedTan1xy);
        intBuffer.put(baseIndex + (2 * vertexSize) + offsetTangent + 1, packedTan1zw);
        intBuffer.put(baseIndex + (3 * vertexSize) + offsetTangent, packedTan1xy);
        intBuffer.put(baseIndex + (3 * vertexSize) + offsetTangent + 1, packedTan1zw);
        int midU = Float.floatToRawIntBits((((v0u + v1u) + v2u) + v3u) / 4.0f);
        int midV = Float.floatToRawIntBits((((v0v + v1v) + v2v) + v3v) / 4.0f);
        intBuffer.put(baseIndex + (0 * vertexSize) + offsetMidUV, midU);
        intBuffer.put(baseIndex + (0 * vertexSize) + offsetMidUV + 1, midV);
        intBuffer.put(baseIndex + (1 * vertexSize) + offsetMidUV, midU);
        intBuffer.put(baseIndex + (1 * vertexSize) + offsetMidUV + 1, midV);
        intBuffer.put(baseIndex + (2 * vertexSize) + offsetMidUV, midU);
        intBuffer.put(baseIndex + (2 * vertexSize) + offsetMidUV + 1, midV);
        intBuffer.put(baseIndex + (3 * vertexSize) + offsetMidUV, midU);
        intBuffer.put(baseIndex + (3 * vertexSize) + offsetMidUV + 1, midV);
    }

    public static void calcNormalChunkLayer(bui wrr) {
    }

    public static void drawArrays(int drawMode, int first, int count, bui vb) {
        if (count != 0) {
            cdy vf = vb.g();
            int vertexSizeByte = vf.g();
            if (vertexSizeByte == 56) {
                ByteBuffer bb = vb.f();
                bb.position(Dummy.get_vertexFormat_miduvElementOffset(vf));
                GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, vertexSizeByte, bb);
                bb.position(Dummy.get_vertexFormat_tangentElementOffset(vf));
                GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, vertexSizeByte, bb);
                bb.position(Dummy.get_vertexFormat_entityElementOffset(vf));
                GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, vertexSizeByte, bb);
                bb.position(0);
                GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
                GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
                GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
                GL11.glDrawArrays(drawMode, first, count);
                GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
                GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
                GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
                return;
            }
            GL11.glDrawArrays(drawMode, first, count);
        }
    }

    public static void startTexturedQuad(bui vb) {
    }
}
