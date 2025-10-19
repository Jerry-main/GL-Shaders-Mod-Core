package shadersmod.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/ShadersTex.class */
public class ShadersTex {
    public static final int defBaseTexColor = 0;
    public static final int defNormTexColor = -8421377;
    public static final int defSpecTexColor = 0;
    public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
    public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
    public static final int initialBufferSize = 1048576;
    public static int[] intArray = new int[initialBufferSize];
    public static Map<Integer, MultiTexID> multiTexMap = new HashMap();
    public static cdn updatingTextureMap = null;
    public static cdo updatingSprite = null;
    public static MultiTexID updatingTex = null;
    public static MultiTexID boundTex = null;
    public static int updatingPage = 0;
    public static String iconName = null;
    public static cen resManager = null;
    static nd resLocation = null;
    static int imageSize = 0;

    public static IntBuffer getIntBuffer(int size) {
        if (intBuffer.capacity() < size) {
            int bufferSize = roundUpPOT(size);
            byteBuffer = BufferUtils.createByteBuffer(bufferSize * 4);
            intBuffer = byteBuffer.asIntBuffer();
        }
        return intBuffer;
    }

    public static int[] getIntArray(int size) {
        if (intArray.length < size) {
            intArray = null;
            intArray = new int[roundUpPOT(size)];
        }
        return intArray;
    }

    public static int roundUpPOT(int x) {
        int i = x - 1;
        int i2 = i | (i >> 1);
        int i3 = i2 | (i2 >> 2);
        int i4 = i3 | (i3 >> 4);
        int i5 = i4 | (i4 >> 8);
        return (i5 | (i5 >> 16)) + 1;
    }

    public static int log2(int x) {
        int log = 0;
        if ((x & (-65536)) != 0) {
            log = 0 + 16;
            x >>= 16;
        }
        if ((x & 65280) != 0) {
            log += 8;
            x >>= 8;
        }
        if ((x & 240) != 0) {
            log += 4;
            x >>= 4;
        }
        if ((x & 12) != 0) {
            log += 2;
            x >>= 2;
        }
        if ((x & 2) != 0) {
            log++;
        }
        return log;
    }

    public static IntBuffer fillIntBuffer(int size, int value) {
        getIntArray(size);
        getIntBuffer(size);
        Arrays.fill(intArray, 0, size, value);
        intBuffer.put(intArray, 0, size);
        return intBuffer;
    }

    public static int[] createAIntImage(int size) {
        int[] aint = new int[size * 3];
        Arrays.fill(aint, 0, size, 0);
        Arrays.fill(aint, size, size * 2, defNormTexColor);
        Arrays.fill(aint, size * 2, size * 3, 0);
        return aint;
    }

    public static int[] createAIntImage(int size, int color) {
        int[] aint = new int[size * 3];
        Arrays.fill(aint, 0, size, color);
        Arrays.fill(aint, size, size * 2, defNormTexColor);
        Arrays.fill(aint, size * 2, size * 3, 0);
        return aint;
    }

    public static MultiTexID getMultiTexID(cdd tex) {
        MultiTexID multiTex = Dummy.get_abstractTexture_multiTex(tex);
        if (multiTex == null) {
            int baseTex = tex.b();
            multiTex = multiTexMap.get(Integer.valueOf(baseTex));
            if (multiTex == null) {
                multiTex = new MultiTexID(baseTex, GL11.glGenTextures(), GL11.glGenTextures());
                multiTexMap.put(Integer.valueOf(baseTex), multiTex);
            }
            Dummy.put_abstractTexture_multiTex(tex, multiTex);
        }
        return multiTex;
    }

    public static void deleteTextures(cdd atex) {
        int texid = Dummy.get_abstractTexture_glTextureId(atex);
        if (texid != -1) {
            GL11.glDeleteTextures(texid);
            Dummy.put_abstractTexture_glTextureId(atex, -1);
        }
        MultiTexID multiTex = Dummy.get_abstractTexture_multiTex(atex);
        if (multiTex != null) {
            Dummy.put_abstractTexture_multiTex(atex, null);
            multiTexMap.remove(Integer.valueOf(multiTex.base));
            GL11.glDeleteTextures(multiTex.norm);
            GL11.glDeleteTextures(multiTex.spec);
            if (multiTex.base != texid) {
                SMCLog.warning("Error : MultiTexID.base mismatch.");
                GL11.glDeleteTextures(multiTex.base);
            }
        }
    }

    public static int deleteMultiTex(cdq tex) {
        if (tex instanceof cdd) {
            deleteTextures((cdd) tex);
            return 0;
        }
        GL11.glDeleteTextures(tex.b());
        return 0;
    }

    public static void bindNSTextures(int normTex, int specTex) {
        if (Shaders.isRenderingWorld && Shaders.activeTexUnit == 33984) {
            GL13.glActiveTexture(33986);
            GL11.glBindTexture(3553, normTex);
            GL13.glActiveTexture(33987);
            GL11.glBindTexture(3553, specTex);
            GL13.glActiveTexture(33984);
        }
    }

    public static void bindNSTextures(MultiTexID multiTex) {
        bindNSTextures(multiTex.norm, multiTex.spec);
    }

    public static void bindTextures(int baseTex, int normTex, int specTex) {
        if (Shaders.isRenderingWorld && Shaders.activeTexUnit == 33984) {
            GL13.glActiveTexture(33986);
            GL11.glBindTexture(3553, normTex);
            GL13.glActiveTexture(33987);
            GL11.glBindTexture(3553, specTex);
            GL13.glActiveTexture(33984);
        }
        GL11.glBindTexture(3553, baseTex);
    }

    public static void bindTextures(MultiTexID multiTex) {
        boundTex = multiTex;
        if (Shaders.isRenderingWorld && Shaders.activeTexUnit == 33984) {
            if (Shaders.configNormalMap) {
                GL13.glActiveTexture(33986);
                GL11.glBindTexture(3553, multiTex.norm);
            }
            if (Shaders.configSpecularMap) {
                GL13.glActiveTexture(33987);
                GL11.glBindTexture(3553, multiTex.spec);
            }
            GL13.glActiveTexture(33984);
        }
        GL11.glBindTexture(3553, multiTex.base);
    }

    public static void bindTexture(cdq tex) {
        if (tex instanceof cdn) {
            Shaders.atlasSizeX = Dummy.get_textureMap_atlasWidth((cdn) tex);
            Shaders.atlasSizeY = Dummy.get_textureMap_atlasHeight((cdn) tex);
            bindTextures(Dummy.invoke_iTextureObject_getMultiTexID(tex));
        } else {
            Shaders.atlasSizeX = 0;
            Shaders.atlasSizeY = 0;
            bindTextures(Dummy.invoke_iTextureObject_getMultiTexID(tex));
        }
    }

    public static void bindTextureMapForUpdateAndRender(cdp tm, nd resLoc) {
        cdn tex = tm.b(resLoc);
        Shaders.atlasSizeX = Dummy.get_textureMap_atlasWidth(tex);
        Shaders.atlasSizeY = Dummy.get_textureMap_atlasHeight(tex);
        MultiTexID multiTexIDInvoke_iTextureObject_getMultiTexID = Dummy.invoke_iTextureObject_getMultiTexID(tex);
        updatingTex = multiTexIDInvoke_iTextureObject_getMultiTexID;
        bindTextures(multiTexIDInvoke_iTextureObject_getMultiTexID);
    }

    public static void bindTextures(int baseTex) {
        MultiTexID multiTex = multiTexMap.get(Integer.valueOf(baseTex));
        bindTextures(multiTex);
    }

    public static void allocTexStorage(int width, int height, int maxLevel) {
        Shaders.checkGLError("pre allocTexStorage");
        int level = 0;
        while ((width >> level) > 0 && (height >> level) > 0) {
            GL11.glTexImage2D(3553, level, 6408, width >> level, height >> level, 0, 32993, 33639, (IntBuffer) null);
            level++;
        }
        GL11.glTexParameteri(3553, 33085, level - 1);
        Shaders.checkGLError("allocTexStorage");
        GL11.glGetError();
    }

    public static int calcMaxLevel(int width, int height) {
        return log2(Math.min(width, height));
    }

    public static void initDynamicTexture(int texID, int width, int height, cde tex) {
        MultiTexID multiTex = Dummy.invoke_iTextureObject_getMultiTexID(tex);
        int[] aint = tex.e();
        int size = width * height;
        Arrays.fill(aint, size, size * 2, defNormTexColor);
        Arrays.fill(aint, size * 2, size * 3, 0);
        int maxLevel = calcMaxLevel(width, height);
        GL11.glBindTexture(3553, multiTex.base);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 33085, maxLevel);
        allocTexStorage(width, height, maxLevel);
        GL11.glBindTexture(3553, multiTex.norm);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 33085, maxLevel);
        allocTexStorage(width, height, maxLevel);
        GL11.glBindTexture(3553, multiTex.spec);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 33085, maxLevel);
        allocTexStorage(width, height, maxLevel);
        GL11.glBindTexture(3553, multiTex.base);
    }

    public static void updateDynamicTexture(int texID, int[] src, int width, int height, cde tex) {
        MultiTexID multiTex = Dummy.invoke_iTextureObject_getMultiTexID(tex);
        GL11.glBindTexture(3553, multiTex.base);
        updateDynTexSubImage1(src, width, height, 0, 0, 0, 0);
        GL11.glBindTexture(3553, multiTex.norm);
        updateDynTexSubImage1(src, width, height, 0, 0, 1, defNormTexColor);
        GL11.glBindTexture(3553, multiTex.spec);
        updateDynTexSubImage1(src, width, height, 0, 0, 2, 0);
        GL11.glBindTexture(3553, multiTex.base);
    }

    public static void updateDynTexSubImage1(int[] src, int width, int height, int posX, int posY, int page, int color) {
        int size = width * height;
        IntBuffer intBuf = getIntBuffer(size);
        int[] aint = getIntArray((size * 4) / 3);
        if (src.length >= size * (page + 1)) {
            System.arraycopy(src, size * page, aint, 0, size);
        } else {
            Arrays.fill(aint, 0, size, color);
        }
        genMipmapAlpha(aint, 0, width, height);
        int offseta = 0;
        for (int level = 0; (width >> level) > 0 && (height >> level) > 0; level++) {
            int lsize = (width >> level) * (height >> level);
            intBuf.clear();
            intBuf.put(aint, offseta, lsize).position(0).limit(lsize);
            GL11.glTexSubImage2D(3553, level, posX >> level, posY >> level, width >> level, height >> level, 32993, 33639, intBuf);
            offseta += lsize;
        }
        intBuf.clear();
    }

    public static cdq createDefaultTexture() {
        cde tex = new cde(1, 1);
        tex.e()[0] = -1;
        tex.d();
        return tex;
    }

    public static void allocateTextureMap(int texID, int mipmapLevels, int width, int height, cdl stitcher, cdn tex) {
        SMCLog.info("allocateTextureMap " + mipmapLevels + " " + width + " " + height + " ");
        updatingTextureMap = tex;
        Dummy.put_textureMap_atlasWidth(tex, width);
        Dummy.put_textureMap_atlasHeight(tex, height);
        MultiTexID multiTex = getMultiTexID(tex);
        updatingTex = multiTex;
        cdr.a(multiTex.base, mipmapLevels, width, height);
        if (Shaders.configNormalMap) {
            cdr.a(multiTex.norm, mipmapLevels, width, height);
        }
        if (Shaders.configSpecularMap) {
            cdr.a(multiTex.spec, mipmapLevels, width, height);
        }
        GL11.glBindTexture(3553, texID);
    }

    public static cdo setSprite(cdo tas) {
        updatingSprite = tas;
        return tas;
    }

    public static String setIconName(String name) {
        iconName = name;
        return name;
    }

    public static void uploadTexSubForLoadAtlas(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) throws IOException {
        cdr.a(data, width, height, xoffset, yoffset, linear, clamp);
        if (Shaders.configNormalMap) {
            int[][] aaint = readImageAndMipmaps(iconName + "_n", width, height, data.length, false, defNormTexColor);
            GL11.glBindTexture(3553, updatingTex.norm);
            cdr.a(aaint, width, height, xoffset, yoffset, linear, clamp);
        }
        if (Shaders.configSpecularMap) {
            int[][] aaint2 = readImageAndMipmaps(iconName + "_s", width, height, data.length, false, 0);
            GL11.glBindTexture(3553, updatingTex.spec);
            cdr.a(aaint2, width, height, xoffset, yoffset, linear, clamp);
        }
        GL11.glBindTexture(3553, updatingTex.base);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [int[], int[][]] */
    public static int[][] readImageAndMipmaps(String name, int width, int height, int numLevels, boolean border, int defColor) throws IOException {
        ?? r0 = new int[numLevels];
        int[] aint = new int[width * height];
        r0[0] = aint;
        boolean goodImage = false;
        nd resLoc = new nd(name);
        BufferedImage image = readImage(new nd(resLoc.b(), String.format("textures/%s%s", resLoc.a(), ".png")));
        if (image != null) {
            int imageWidth = image.getWidth();
            image.getHeight();
            if (imageWidth + (border ? 16 : 0) == width) {
                goodImage = true;
                image.getRGB(0, 0, imageWidth, imageWidth, aint, 0, imageWidth);
            }
        }
        if (!goodImage) {
            Arrays.fill(aint, defColor);
        }
        GL11.glBindTexture(3553, updatingTex.spec);
        int[][] aaint = genMipmapsSimple(r0.length - 1, width, r0);
        return aaint;
    }

    public static BufferedImage readImage(nd resLoc) throws IOException {
        BufferedImage image = null;
        InputStream istr = null;
        try {
            istr = resManager.a(resLoc).b();
            image = ImageIO.read(istr);
        } catch (IOException e) {
        }
        if (istr != null) {
            try {
                istr.close();
            } catch (IOException e2) {
            }
        }
        return image;
    }

    public static int[][] genMipmapsSimple(int maxLevel, int width, int[][] data) {
        for (int level = 1; level <= maxLevel; level++) {
            if (data[level] == null) {
                int cw = width >> level;
                int pw = cw * 2;
                int[] aintp = data[level - 1];
                int[] aintc = new int[cw * cw];
                data[level] = aintc;
                for (int y = 0; y < cw; y++) {
                    for (int x = 0; x < cw; x++) {
                        int ppos = (y * 2 * pw) + (x * 2);
                        aintc[(y * cw) + x] = blend4Simple(aintp[ppos], aintp[ppos + 1], aintp[ppos + pw], aintp[ppos + pw + 1]);
                    }
                }
            }
        }
        return data;
    }

    public static void uploadTexSub(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
        cdr.a(data, width, height, xoffset, yoffset, linear, clamp);
        if (Shaders.configNormalMap || Shaders.configSpecularMap) {
            if (Shaders.configNormalMap) {
                GL11.glBindTexture(3553, updatingTex.norm);
                uploadTexSub1(data, width, height, xoffset, yoffset, 1);
            }
            if (Shaders.configSpecularMap) {
                GL11.glBindTexture(3553, updatingTex.spec);
                uploadTexSub1(data, width, height, xoffset, yoffset, 2);
            }
            GL11.glBindTexture(3553, updatingTex.base);
        }
    }

    public static void uploadTexSub1(int[][] src, int width, int height, int posX, int posY, int page) {
        int size = width * height;
        IntBuffer intBuf = getIntBuffer(size);
        int numLevel = src.length;
        int lw = width;
        int lh = height;
        int px = posX;
        int py = posY;
        for (int level = 0; lw > 0 && lh > 0 && level < numLevel; level++) {
            int lsize = lw * lh;
            int[] aint = src[level];
            intBuf.clear();
            if (aint.length >= lsize * (page + 1)) {
                intBuf.put(aint, lsize * page, lsize).position(0).limit(lsize);
                GL11.glTexSubImage2D(3553, level, px, py, lw, lh, 32993, 33639, intBuf);
            }
            lw >>= 1;
            lh >>= 1;
            px >>= 1;
            py >>= 1;
        }
        intBuf.clear();
    }

    public static int blend4Alpha(int c0, int c1, int c2, int c3) {
        int dv;
        int a0 = (c0 >>> 24) & 255;
        int a1 = (c1 >>> 24) & 255;
        int a2 = (c2 >>> 24) & 255;
        int a3 = (c3 >>> 24) & 255;
        int as = a0 + a1 + a2 + a3;
        int an = (as + 2) / 4;
        if (as != 0) {
            dv = as;
        } else {
            dv = 4;
            a0 = 1;
            a1 = 1;
            a2 = 1;
            a3 = 1;
        }
        int frac = (dv + 1) / 2;
        int color = (an << 24) | (((((((((c0 >>> 16) & 255) * a0) + (((c1 >>> 16) & 255) * a1)) + (((c2 >>> 16) & 255) * a2)) + (((c3 >>> 16) & 255) * a3)) + frac) / dv) << 16) | (((((((((c0 >>> 8) & 255) * a0) + (((c1 >>> 8) & 255) * a1)) + (((c2 >>> 8) & 255) * a2)) + (((c3 >>> 8) & 255) * a3)) + frac) / dv) << 8) | (((((((((c0 >>> 0) & 255) * a0) + (((c1 >>> 0) & 255) * a1)) + (((c2 >>> 0) & 255) * a2)) + (((c3 >>> 0) & 255) * a3)) + frac) / dv) << 0);
        return color;
    }

    public static int blend4Simple(int c0, int c1, int c2, int c3) {
        int color = ((((((((c0 >>> 24) & 255) + ((c1 >>> 24) & 255)) + ((c2 >>> 24) & 255)) + ((c3 >>> 24) & 255)) + 2) / 4) << 24) | ((((((((c0 >>> 16) & 255) + ((c1 >>> 16) & 255)) + ((c2 >>> 16) & 255)) + ((c3 >>> 16) & 255)) + 2) / 4) << 16) | ((((((((c0 >>> 8) & 255) + ((c1 >>> 8) & 255)) + ((c2 >>> 8) & 255)) + ((c3 >>> 8) & 255)) + 2) / 4) << 8) | ((((((((c0 >>> 0) & 255) + ((c1 >>> 0) & 255)) + ((c2 >>> 0) & 255)) + ((c3 >>> 0) & 255)) + 2) / 4) << 0);
        return color;
    }

    public static void genMipmapAlpha(int[] aint, int offset, int width, int height) {
        Math.min(width, height);
        int o2 = offset;
        int w2 = width;
        int h2 = height;
        int o1 = 0;
        int w1 = 0;
        int level = 0;
        while (w2 > 1 && h2 > 1) {
            o1 = o2 + (w2 * h2);
            w1 = w2 / 2;
            int h1 = h2 / 2;
            for (int y = 0; y < h1; y++) {
                int p1 = o1 + (y * w1);
                int p2 = o2 + (y * 2 * w2);
                for (int x = 0; x < w1; x++) {
                    aint[p1 + x] = blend4Alpha(aint[p2 + (x * 2)], aint[p2 + (x * 2) + 1], aint[p2 + w2 + (x * 2)], aint[p2 + w2 + (x * 2) + 1]);
                }
            }
            level++;
            w2 = w1;
            h2 = h1;
            o2 = o1;
        }
        while (level > 0) {
            level--;
            int w22 = width >> level;
            int h22 = height >> level;
            int o22 = o1 - (w22 * h22);
            int p22 = o22;
            for (int y2 = 0; y2 < h22; y2++) {
                for (int x2 = 0; x2 < w22; x2++) {
                    if (aint[p22] == 0) {
                        aint[p22] = aint[o1 + ((y2 / 2) * w1) + (x2 / 2)] & 16777215;
                    }
                    p22++;
                }
            }
            o1 = o22;
            w1 = w22;
        }
    }

    public static void genMipmapSimple(int[] aint, int offset, int width, int height) {
        Math.min(width, height);
        int o2 = offset;
        int w2 = width;
        int h2 = height;
        int o1 = 0;
        int w1 = 0;
        int level = 0;
        while (w2 > 1 && h2 > 1) {
            o1 = o2 + (w2 * h2);
            w1 = w2 / 2;
            int h1 = h2 / 2;
            for (int y = 0; y < h1; y++) {
                int p1 = o1 + (y * w1);
                int p2 = o2 + (y * 2 * w2);
                for (int x = 0; x < w1; x++) {
                    aint[p1 + x] = blend4Simple(aint[p2 + (x * 2)], aint[p2 + (x * 2) + 1], aint[p2 + w2 + (x * 2)], aint[p2 + w2 + (x * 2) + 1]);
                }
            }
            level++;
            w2 = w1;
            h2 = h1;
            o2 = o1;
        }
        while (level > 0) {
            level--;
            int w22 = width >> level;
            int h22 = height >> level;
            int o22 = o1 - (w22 * h22);
            int p22 = o22;
            for (int y2 = 0; y2 < h22; y2++) {
                for (int x2 = 0; x2 < w22; x2++) {
                    if (aint[p22] == 0) {
                        aint[p22] = aint[o1 + ((y2 / 2) * w1) + (x2 / 2)] & 16777215;
                    }
                    p22++;
                }
            }
            o1 = o22;
            w1 = w22;
        }
    }

    public static boolean isSemiTransparent(int[] aint, int width, int height) {
        int size = width * height;
        if ((aint[0] >>> 24) == 255 && aint[size - 1] == 0) {
            return true;
        }
        for (int i = 0; i < size; i++) {
            int alpha = aint[i] >>> 24;
            if (alpha != 0 && alpha != 255) {
                return true;
            }
        }
        return false;
    }

    public static void updateSubTex1(int[] src, int width, int height, int posX, int posY) {
        int level = 0;
        int cw = width;
        int ch = height;
        int cx = posX;
        int i = posY;
        while (true) {
            int cy = i;
            if (cw > 0 && ch > 0) {
                GL11.glCopyTexSubImage2D(3553, level, cx, cy, 0, 0, cw, ch);
                level++;
                cw /= 2;
                ch /= 2;
                cx /= 2;
                i = cy / 2;
            } else {
                return;
            }
        }
    }

    public static void setupTextureMipmap(cdn tex) {
    }

    public static void setupTexture(MultiTexID multiTex, int[] src, int width, int height, boolean linear, boolean clamp) {
        int mmfilter = linear ? 9729 : 9728;
        int wraptype = clamp ? 10496 : 10497;
        int size = width * height;
        IntBuffer intBuf = getIntBuffer(size);
        intBuf.clear();
        intBuf.put(src, 0, size).position(0).limit(size);
        GL11.glBindTexture(3553, multiTex.base);
        GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intBuf);
        GL11.glTexParameteri(3553, 10241, mmfilter);
        GL11.glTexParameteri(3553, 10240, mmfilter);
        GL11.glTexParameteri(3553, 10242, wraptype);
        GL11.glTexParameteri(3553, 10243, wraptype);
        intBuf.put(src, size, size).position(0).limit(size);
        GL11.glBindTexture(3553, multiTex.norm);
        GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intBuf);
        GL11.glTexParameteri(3553, 10241, mmfilter);
        GL11.glTexParameteri(3553, 10240, mmfilter);
        GL11.glTexParameteri(3553, 10242, wraptype);
        GL11.glTexParameteri(3553, 10243, wraptype);
        intBuf.put(src, size * 2, size).position(0).limit(size);
        GL11.glBindTexture(3553, multiTex.spec);
        GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intBuf);
        GL11.glTexParameteri(3553, 10241, mmfilter);
        GL11.glTexParameteri(3553, 10240, mmfilter);
        GL11.glTexParameteri(3553, 10242, wraptype);
        GL11.glTexParameteri(3553, 10243, wraptype);
        GL11.glBindTexture(3553, multiTex.base);
    }

    public static nd getNSMapLocation(nd location, String mapName) {
        String basename = location.a();
        String[] basenameParts = basename.split(".png");
        String basenameNoFileType = basenameParts[0];
        return new nd(location.b(), basenameNoFileType + "_" + mapName + ".png");
    }

    public static void loadNSMap(cen manager, nd location, int width, int height, int[] aint) {
        if (Shaders.configNormalMap) {
            loadNSMap1(manager, getNSMapLocation(location, "n"), width, height, aint, width * height, defNormTexColor);
        }
        if (Shaders.configSpecularMap) {
            loadNSMap1(manager, getNSMapLocation(location, "s"), width, height, aint, width * height * 2, 0);
        }
    }

    public static void loadNSMap1(cen manager, nd location, int width, int height, int[] aint, int offset, int defaultColor) {
        boolean good = false;
        try {
            cem res = manager.a(location);
            BufferedImage bufferedimage = ImageIO.read(res.b());
            if (bufferedimage.getWidth() == width && bufferedimage.getHeight() == height) {
                bufferedimage.getRGB(0, 0, width, height, aint, offset, width);
                good = true;
            }
        } catch (IOException e) {
        }
        if (!good) {
            Arrays.fill(aint, offset, offset + (width * height), defaultColor);
        }
    }

    public static int loadSimpleTexture(int textureID, BufferedImage bufferedimage, boolean linear, boolean clamp, cen resourceManager, nd location, MultiTexID multiTex) {
        int width = bufferedimage.getWidth();
        int height = bufferedimage.getHeight();
        int size = width * height;
        int[] aint = getIntArray(size * 3);
        bufferedimage.getRGB(0, 0, width, height, aint, 0, width);
        loadNSMap(resourceManager, location, width, height, aint);
        setupTexture(multiTex, aint, width, height, linear, clamp);
        return textureID;
    }

    public static void mergeImage(int[] aint, int dstoff, int srcoff, int size) {
    }

    public static int blendColor(int color1, int color2, int factor1) {
        int factor2 = 255 - factor1;
        return ((((((color1 >>> 24) & 255) * factor1) + (((color2 >>> 24) & 255) * factor2)) / 255) << 24) | ((((((color1 >>> 16) & 255) * factor1) + (((color2 >>> 16) & 255) * factor2)) / 255) << 16) | ((((((color1 >>> 8) & 255) * factor1) + (((color2 >>> 8) & 255) * factor2)) / 255) << 8) | ((((((color1 >>> 0) & 255) * factor1) + (((color2 >>> 0) & 255) * factor2)) / 255) << 0);
    }

    public static void loadLayeredTexture(cdi tex, cen manager, List list) {
        int width = 0;
        int height = 0;
        int size = 0;
        int[] image = null;
        for (String s : list) {
            if (s != null) {
                try {
                    nd location = new nd(s);
                    InputStream inputstream = manager.a(location).b();
                    BufferedImage bufimg = ImageIO.read(inputstream);
                    if (size == 0) {
                        width = bufimg.getWidth();
                        height = bufimg.getHeight();
                        size = width * height;
                        image = createAIntImage(size, 0);
                    }
                    int[] aint = getIntArray(size * 3);
                    bufimg.getRGB(0, 0, width, height, aint, 0, width);
                    loadNSMap(manager, location, width, height, aint);
                    for (int i = 0; i < size; i++) {
                        int alpha = (aint[i] >>> 24) & 255;
                        image[(size * 0) + i] = blendColor(aint[(size * 0) + i], image[(size * 0) + i], alpha);
                        image[(size * 1) + i] = blendColor(aint[(size * 1) + i], image[(size * 1) + i], alpha);
                        image[(size * 2) + i] = blendColor(aint[(size * 2) + i], image[(size * 2) + i], alpha);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        setupTexture(Dummy.invoke_iTextureObject_getMultiTexID(tex), image, width, height, false, false);
    }

    static void updateTextureMinMagFilter() {
        cdp texman = bhz.z().N();
        cdq texObj = texman.b(cdn.g);
        if (texObj != null) {
            MultiTexID multiTex = Dummy.invoke_iTextureObject_getMultiTexID(texObj);
            GL11.glBindTexture(3553, multiTex.base);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
            GL11.glBindTexture(3553, multiTex.norm);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
            GL11.glBindTexture(3553, multiTex.spec);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
            GL11.glBindTexture(3553, 0);
        }
    }

    public static cem loadResource(cen manager, nd location) throws IOException {
        resManager = manager;
        resLocation = location;
        return manager.a(location);
    }

    public static int[] loadAtlasSprite(BufferedImage bufferedimage, int startX, int startY, int w, int h, int[] aint, int offset, int scansize) {
        imageSize = w * h;
        bufferedimage.getRGB(startX, startY, w, h, aint, offset, scansize);
        loadNSMap(resManager, resLocation, w, h, aint);
        return aint;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [int[], int[][]] */
    public static int[][] getFrameTexData(int[][] src, int width, int height, int frameIndex) {
        int numLevel = src.length;
        ?? r0 = new int[numLevel];
        for (int level = 0; level < numLevel; level++) {
            int[] sr1 = src[level];
            if (sr1 != null) {
                int frameSize = (width >> level) * (height >> level);
                int[] ds1 = new int[frameSize * 3];
                r0[level] = ds1;
                int srcSize = sr1.length / 3;
                int srcPos = frameSize * frameIndex;
                System.arraycopy(sr1, srcPos, ds1, 0, frameSize);
                int srcPos2 = srcPos + srcSize;
                int dstPos = 0 + frameSize;
                System.arraycopy(sr1, srcPos2, ds1, dstPos, frameSize);
                System.arraycopy(sr1, srcPos2 + srcSize, ds1, dstPos + frameSize, frameSize);
            }
        }
        return r0;
    }

    public static int[][] prepareAF(cdo tas, int[][] src, int width, int height) {
        return src;
    }

    public static void fixTransparentColor(cdo tas, int[] aint) {
    }
}
