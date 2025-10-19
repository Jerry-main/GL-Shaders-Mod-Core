package shadersmod.client;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/ClippingHelperShadow.class */
public class ClippingHelperShadow extends bxz {
    private static ClippingHelperShadow instance = new ClippingHelperShadow();
    int shadowClipPlaneCount;
    float[] frustumTest = new float[6];
    float[][] shadowClipPlanes = new float[10][4];
    float[] matInvMP = new float[16];
    float[] vecIntersection = new float[4];

    /* renamed from: b */
    public boolean m0b(double x1, double y1, double z1, double x2, double y2, double z2) {
        for (int index = 0; index < this.shadowClipPlaneCount; index++) {
            float[] plane = this.shadowClipPlanes[index];
            if (dot4(plane, x1, y1, z1) <= 0.0d && dot4(plane, x2, y1, z1) <= 0.0d && dot4(plane, x1, y2, z1) <= 0.0d && dot4(plane, x2, y2, z1) <= 0.0d && dot4(plane, x1, y1, z2) <= 0.0d && dot4(plane, x2, y1, z2) <= 0.0d && dot4(plane, x1, y2, z2) <= 0.0d && dot4(plane, x2, y2, z2) <= 0.0d) {
                return false;
            }
        }
        return true;
    }

    private double dot4(float[] plane, double x, double y, double z) {
        return (plane[0] * x) + (plane[1] * y) + (plane[2] * z) + plane[3];
    }

    private double dot3(float[] vecA, float[] vecB) {
        return (vecA[0] * vecB[0]) + (vecA[1] * vecB[1]) + (vecA[2] * vecB[2]);
    }

    public static bxz getInstance() {
        instance.init();
        return instance;
    }

    private void normalizePlane(float[] plane) {
        float length = ri.c((plane[0] * plane[0]) + (plane[1] * plane[1]) + (plane[2] * plane[2]));
        plane[0] = plane[0] / length;
        plane[1] = plane[1] / length;
        plane[2] = plane[2] / length;
        plane[3] = plane[3] / length;
    }

    private void normalize3(float[] plane) {
        float length = ri.c((plane[0] * plane[0]) + (plane[1] * plane[1]) + (plane[2] * plane[2]));
        if (length == 0.0f) {
            length = 1.0f;
        }
        plane[0] = plane[0] / length;
        plane[1] = plane[1] / length;
        plane[2] = plane[2] / length;
    }

    private void assignPlane(float[] plane, float a, float b, float c, float d) {
        float length = (float) Math.sqrt((a * a) + (b * b) + (c * c));
        plane[0] = a / length;
        plane[1] = b / length;
        plane[2] = c / length;
        plane[3] = d / length;
    }

    private void copyPlane(float[] dst, float[] src) {
        dst[0] = src[0];
        dst[1] = src[1];
        dst[2] = src[2];
        dst[3] = src[3];
    }

    private void cross3(float[] out, float[] a, float[] b) {
        out[0] = (a[1] * b[2]) - (a[2] * b[1]);
        out[1] = (a[2] * b[0]) - (a[0] * b[2]);
        out[2] = (a[0] * b[1]) - (a[1] * b[0]);
    }

    private void addShadowClipPlane(float[] plane) {
        float[][] fArr = this.shadowClipPlanes;
        int i = this.shadowClipPlaneCount;
        this.shadowClipPlaneCount = i + 1;
        copyPlane(fArr[i], plane);
    }

    private float length(float x, float y, float z) {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    private float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
        return length(x1 - x2, y1 - y2, z1 - z2);
    }

    private void makeShadowPlane(float[] shadowPlane, float[] positivePlane, float[] negativePlane, float[] vecSun) {
        cross3(this.vecIntersection, positivePlane, negativePlane);
        cross3(shadowPlane, this.vecIntersection, vecSun);
        normalize3(shadowPlane);
        float dotPN = (float) dot3(positivePlane, negativePlane);
        float dotSN = (float) dot3(shadowPlane, negativePlane);
        float disSN = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * dotSN, negativePlane[1] * dotSN, negativePlane[2] * dotSN);
        float disPN = distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * dotPN, negativePlane[1] * dotPN, negativePlane[2] * dotPN);
        float k1 = disSN / disPN;
        float dotSP = (float) dot3(shadowPlane, positivePlane);
        float disSP = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * dotSP, positivePlane[1] * dotSP, positivePlane[2] * dotSP);
        float disNP = distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * dotPN, positivePlane[1] * dotPN, positivePlane[2] * dotPN);
        float k2 = disSP / disNP;
        shadowPlane[3] = (positivePlane[3] * k1) + (negativePlane[3] * k2);
    }

    public void init() {
        float[] matPrj = this.b;
        float[] matMdv = this.c;
        float[] matMP = this.d;
        System.arraycopy(Shaders.faProjection, 0, matPrj, 0, 16);
        System.arraycopy(Shaders.faModelView, 0, matMdv, 0, 16);
        SMath.multiplyMat4xMat4(matMP, matMdv, matPrj);
        assignPlane(this.a[0], matMP[3] - matMP[0], matMP[7] - matMP[4], matMP[11] - matMP[8], matMP[15] - matMP[12]);
        assignPlane(this.a[1], matMP[3] + matMP[0], matMP[7] + matMP[4], matMP[11] + matMP[8], matMP[15] + matMP[12]);
        assignPlane(this.a[2], matMP[3] + matMP[1], matMP[7] + matMP[5], matMP[11] + matMP[9], matMP[15] + matMP[13]);
        assignPlane(this.a[3], matMP[3] - matMP[1], matMP[7] - matMP[5], matMP[11] - matMP[9], matMP[15] - matMP[13]);
        assignPlane(this.a[4], matMP[3] - matMP[2], matMP[7] - matMP[6], matMP[11] - matMP[10], matMP[15] - matMP[14]);
        assignPlane(this.a[5], matMP[3] + matMP[2], matMP[7] + matMP[6], matMP[11] + matMP[10], matMP[15] + matMP[14]);
        float[] vecSun = Shaders.shadowLightPositionVector;
        float test0 = (float) dot3(this.a[0], vecSun);
        float test1 = (float) dot3(this.a[1], vecSun);
        float test2 = (float) dot3(this.a[2], vecSun);
        float test3 = (float) dot3(this.a[3], vecSun);
        float test4 = (float) dot3(this.a[4], vecSun);
        float test5 = (float) dot3(this.a[5], vecSun);
        this.shadowClipPlaneCount = 0;
        if (test0 >= 0.0f) {
            float[][] fArr = this.shadowClipPlanes;
            int i = this.shadowClipPlaneCount;
            this.shadowClipPlaneCount = i + 1;
            copyPlane(fArr[i], this.a[0]);
            if (test0 > 0.0f) {
                if (test2 < 0.0f) {
                    float[][] fArr2 = this.shadowClipPlanes;
                    int i2 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i2 + 1;
                    makeShadowPlane(fArr2[i2], this.a[0], this.a[2], vecSun);
                }
                if (test3 < 0.0f) {
                    float[][] fArr3 = this.shadowClipPlanes;
                    int i3 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i3 + 1;
                    makeShadowPlane(fArr3[i3], this.a[0], this.a[3], vecSun);
                }
                if (test4 < 0.0f) {
                    float[][] fArr4 = this.shadowClipPlanes;
                    int i4 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i4 + 1;
                    makeShadowPlane(fArr4[i4], this.a[0], this.a[4], vecSun);
                }
                if (test5 < 0.0f) {
                    float[][] fArr5 = this.shadowClipPlanes;
                    int i5 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i5 + 1;
                    makeShadowPlane(fArr5[i5], this.a[0], this.a[5], vecSun);
                }
            }
        }
        if (test1 >= 0.0f) {
            float[][] fArr6 = this.shadowClipPlanes;
            int i6 = this.shadowClipPlaneCount;
            this.shadowClipPlaneCount = i6 + 1;
            copyPlane(fArr6[i6], this.a[1]);
            if (test1 > 0.0f) {
                if (test2 < 0.0f) {
                    float[][] fArr7 = this.shadowClipPlanes;
                    int i7 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i7 + 1;
                    makeShadowPlane(fArr7[i7], this.a[1], this.a[2], vecSun);
                }
                if (test3 < 0.0f) {
                    float[][] fArr8 = this.shadowClipPlanes;
                    int i8 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i8 + 1;
                    makeShadowPlane(fArr8[i8], this.a[1], this.a[3], vecSun);
                }
                if (test4 < 0.0f) {
                    float[][] fArr9 = this.shadowClipPlanes;
                    int i9 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i9 + 1;
                    makeShadowPlane(fArr9[i9], this.a[1], this.a[4], vecSun);
                }
                if (test5 < 0.0f) {
                    float[][] fArr10 = this.shadowClipPlanes;
                    int i10 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i10 + 1;
                    makeShadowPlane(fArr10[i10], this.a[1], this.a[5], vecSun);
                }
            }
        }
        if (test2 >= 0.0f) {
            float[][] fArr11 = this.shadowClipPlanes;
            int i11 = this.shadowClipPlaneCount;
            this.shadowClipPlaneCount = i11 + 1;
            copyPlane(fArr11[i11], this.a[2]);
            if (test2 > 0.0f) {
                if (test0 < 0.0f) {
                    float[][] fArr12 = this.shadowClipPlanes;
                    int i12 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i12 + 1;
                    makeShadowPlane(fArr12[i12], this.a[2], this.a[0], vecSun);
                }
                if (test1 < 0.0f) {
                    float[][] fArr13 = this.shadowClipPlanes;
                    int i13 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i13 + 1;
                    makeShadowPlane(fArr13[i13], this.a[2], this.a[1], vecSun);
                }
                if (test4 < 0.0f) {
                    float[][] fArr14 = this.shadowClipPlanes;
                    int i14 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i14 + 1;
                    makeShadowPlane(fArr14[i14], this.a[2], this.a[4], vecSun);
                }
                if (test5 < 0.0f) {
                    float[][] fArr15 = this.shadowClipPlanes;
                    int i15 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i15 + 1;
                    makeShadowPlane(fArr15[i15], this.a[2], this.a[5], vecSun);
                }
            }
        }
        if (test3 >= 0.0f) {
            float[][] fArr16 = this.shadowClipPlanes;
            int i16 = this.shadowClipPlaneCount;
            this.shadowClipPlaneCount = i16 + 1;
            copyPlane(fArr16[i16], this.a[3]);
            if (test3 > 0.0f) {
                if (test0 < 0.0f) {
                    float[][] fArr17 = this.shadowClipPlanes;
                    int i17 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i17 + 1;
                    makeShadowPlane(fArr17[i17], this.a[3], this.a[0], vecSun);
                }
                if (test1 < 0.0f) {
                    float[][] fArr18 = this.shadowClipPlanes;
                    int i18 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i18 + 1;
                    makeShadowPlane(fArr18[i18], this.a[3], this.a[1], vecSun);
                }
                if (test4 < 0.0f) {
                    float[][] fArr19 = this.shadowClipPlanes;
                    int i19 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i19 + 1;
                    makeShadowPlane(fArr19[i19], this.a[3], this.a[4], vecSun);
                }
                if (test5 < 0.0f) {
                    float[][] fArr20 = this.shadowClipPlanes;
                    int i20 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i20 + 1;
                    makeShadowPlane(fArr20[i20], this.a[3], this.a[5], vecSun);
                }
            }
        }
        if (test4 >= 0.0f) {
            float[][] fArr21 = this.shadowClipPlanes;
            int i21 = this.shadowClipPlaneCount;
            this.shadowClipPlaneCount = i21 + 1;
            copyPlane(fArr21[i21], this.a[4]);
            if (test4 > 0.0f) {
                if (test0 < 0.0f) {
                    float[][] fArr22 = this.shadowClipPlanes;
                    int i22 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i22 + 1;
                    makeShadowPlane(fArr22[i22], this.a[4], this.a[0], vecSun);
                }
                if (test1 < 0.0f) {
                    float[][] fArr23 = this.shadowClipPlanes;
                    int i23 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i23 + 1;
                    makeShadowPlane(fArr23[i23], this.a[4], this.a[1], vecSun);
                }
                if (test2 < 0.0f) {
                    float[][] fArr24 = this.shadowClipPlanes;
                    int i24 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i24 + 1;
                    makeShadowPlane(fArr24[i24], this.a[4], this.a[2], vecSun);
                }
                if (test3 < 0.0f) {
                    float[][] fArr25 = this.shadowClipPlanes;
                    int i25 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i25 + 1;
                    makeShadowPlane(fArr25[i25], this.a[4], this.a[3], vecSun);
                }
            }
        }
        if (test5 >= 0.0f) {
            float[][] fArr26 = this.shadowClipPlanes;
            int i26 = this.shadowClipPlaneCount;
            this.shadowClipPlaneCount = i26 + 1;
            copyPlane(fArr26[i26], this.a[5]);
            if (test5 > 0.0f) {
                if (test0 < 0.0f) {
                    float[][] fArr27 = this.shadowClipPlanes;
                    int i27 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i27 + 1;
                    makeShadowPlane(fArr27[i27], this.a[5], this.a[0], vecSun);
                }
                if (test1 < 0.0f) {
                    float[][] fArr28 = this.shadowClipPlanes;
                    int i28 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i28 + 1;
                    makeShadowPlane(fArr28[i28], this.a[5], this.a[1], vecSun);
                }
                if (test2 < 0.0f) {
                    float[][] fArr29 = this.shadowClipPlanes;
                    int i29 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i29 + 1;
                    makeShadowPlane(fArr29[i29], this.a[5], this.a[2], vecSun);
                }
                if (test3 < 0.0f) {
                    float[][] fArr30 = this.shadowClipPlanes;
                    int i30 = this.shadowClipPlaneCount;
                    this.shadowClipPlaneCount = i30 + 1;
                    makeShadowPlane(fArr30[i30], this.a[5], this.a[3], vecSun);
                }
            }
        }
    }
}
