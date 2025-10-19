package shadersmod.client;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import shadersmod.common.SMCEnvironment;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/ShadersRender.class */
public class ShadersRender {
    public static void setFrustrumPosition(bxy frustrum, double x, double y, double z) {
        frustrum.a(x, y, z);
    }

    public static void setupTerrain(buw renderGlobal, ve viewEntity, double partialTicks, bxw camera, int frameCount, boolean playerSpectator) {
        renderGlobal.a(viewEntity, partialTicks, camera, frameCount, playerSpectator);
    }

    public static void updateChunks(buw renderGlobal, long finishTimeNano) {
        renderGlobal.a(finishTimeNano);
    }

    public static void beginTerrainSolid() {
        if (Shaders.isRenderingWorld) {
            Shaders.fogEnabled = true;
            Shaders.useProgram(7);
        }
    }

    public static void beginTerrainCutoutMipped() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(7);
        }
    }

    public static void beginTerrainCutout() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(7);
        }
    }

    public static void endTerrain() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(3);
        }
    }

    public static void beginTranslucent() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.usedDepthBuffers >= 2) {
                GL13.glActiveTexture(33995);
                Shaders.checkGLError("pre copy depth");
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
                Shaders.checkGLError("copy depth");
                GL13.glActiveTexture(33984);
            }
            Shaders.useProgram(12);
        }
    }

    public static void endTranslucent() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(3);
        }
    }

    public static void renderHand0(buo er, float par1, int par2) {
        if (!Shaders.isShadowPass) {
            ail item = Shaders.itemToRender != null ? Shaders.itemToRender.c() : null;
            aou block = item instanceof agz ? ((agz) item).d() : null;
            if (!(item instanceof agz) || !(block instanceof aou) || block.f() == amk.a) {
                Shaders.readCenterDepth();
                GL11.glDisable(3042);
                Shaders.beginHand();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                Dummy.invoke_entityRenderer_renderHand(er, par1, par2);
                Shaders.endHand();
                Shaders.isHandRendered = true;
            }
        }
    }

    public static void renderHand1(buo er, float par1, int par2) {
        if (!Shaders.isShadowPass && !Shaders.isHandRendered) {
            Shaders.readCenterDepth();
            GL11.glEnable(3042);
            Shaders.beginHand();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Dummy.invoke_entityRenderer_renderHand(er, par1, par2);
            Shaders.endHand();
            Shaders.isHandRendered = true;
        }
    }

    public static void renderItemFP(bus itemRenderer, float par1) {
        GL11.glDepthMask(true);
        GL11.glDepthFunc(519);
        GL11.glPushMatrix();
        IntBuffer drawBuffers = Shaders.activeDrawBuffers;
        Shaders.setDrawBuffers(Shaders.drawBuffersNone);
        Shaders.renderItemPass1DepthMask = true;
        itemRenderer.a(par1);
        Shaders.renderItemPass1DepthMask = false;
        Shaders.setDrawBuffers(drawBuffers);
        GL11.glPopMatrix();
        GL11.glDepthFunc(515);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        itemRenderer.a(par1);
    }

    public static void renderFPOverlay(buo er, float par1, int par2) {
        if (!Shaders.isShadowPass) {
            Shaders.beginFPOverlay();
            Dummy.invoke_entityRenderer_renderHand(er, par1, par2);
            Shaders.endFPOverlay();
        }
    }

    public static void beginBlockDamage() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(11);
            if (Shaders.programsID[11] == Shaders.programsID[7]) {
                Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
                GL11.glDepthMask(false);
            }
        }
    }

    public static void endBlockDamage() {
        if (Shaders.isRenderingWorld) {
            GL11.glDepthMask(true);
            Shaders.useProgram(3);
        }
    }

    public static void renderShadowMap(buo entityRenderer, int pass, float partialTicks, long finishTimeNano) {
        if (Shaders.usedShadowDepthBuffers > 0) {
            int i = Shaders.shadowPassCounter - 1;
            Shaders.shadowPassCounter = i;
            if (i <= 0) {
                bhz mc = bhz.z();
                mc.B.c("shadow pass");
                buw renderGlobal = mc.g;
                Shaders.isShadowPass = true;
                Shaders.shadowPassCounter = Shaders.shadowPassInterval;
                Shaders.preShadowPassThirdPersonView = mc.t.aw;
                mc.t.aw = 1;
                Shaders.checkGLError("pre shadow");
                GL11.glMatrixMode(5889);
                GL11.glPushMatrix();
                GL11.glMatrixMode(5888);
                GL11.glPushMatrix();
                mc.B.c("shadow clear");
                GL30.glBindFramebuffer(36160, Shaders.sfb);
                Shaders.checkGLError("shadow bind sfb");
                Shaders.useProgram(30);
                mc.B.c("shadow camera");
                Dummy.invoke_entityRenderer_setupCameraTransform(entityRenderer, partialTicks, 2);
                Shaders.setCameraShadow(partialTicks);
                bht.a(mc.h, mc.t.aw == 2);
                Shaders.checkGLError("shadow camera");
                GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
                Shaders.checkGLError("shadow drawbuffers");
                GL11.glReadBuffer(0);
                Shaders.checkGLError("shadow readbuffer");
                GL30.glFramebufferTexture2D(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
                if (Shaders.usedShadowColorBuffers != 0) {
                    GL30.glFramebufferTexture2D(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
                }
                Shaders.checkFramebufferStatus("shadow fb");
                GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glClear(Shaders.usedShadowColorBuffers != 0 ? 16640 : 256);
                Shaders.checkGLError("shadow clear");
                mc.B.c("shadow frustum");
                bxz clippingHelper = ClippingHelperShadow.getInstance();
                mc.B.c("shadow culling");
                bxy frustum = new bxy(clippingHelper);
                ve viewEntity = mc.aa();
                double viewPosX = viewEntity.M + ((viewEntity.p - viewEntity.M) * partialTicks);
                double viewPosY = viewEntity.N + ((viewEntity.q - viewEntity.N) * partialTicks);
                double viewPosZ = viewEntity.O + ((viewEntity.r - viewEntity.O) * partialTicks);
                frustum.a(viewPosX, viewPosY, viewPosZ);
                buq.j(7425);
                buq.k();
                buq.c(515);
                buq.a(true);
                buq.a(true, true, true, true);
                buq.q();
                mc.B.c("shadow prepareterrain");
                mc.N().a(cdn.g);
                mc.B.c("shadow setupterrain");
                int frameCount = Dummy.get_entityRenderer_frameCount(entityRenderer);
                Dummy.put_entityRenderer_frameCount(entityRenderer, frameCount + 1);
                renderGlobal.a(viewEntity, partialTicks, frustum, frameCount, mc.h.y());
                mc.B.c("shadow updatechunks");
                mc.B.c("shadow terrain");
                buq.n(5888);
                buq.G();
                buq.d();
                renderGlobal.a(amk.a, partialTicks, 2, viewEntity);
                Shaders.checkGLError("shadow terrain solid");
                buq.e();
                renderGlobal.a(amk.b, partialTicks, 2, viewEntity);
                Shaders.checkGLError("shadow terrain cutoutmipped");
                mc.N().b(cdn.g).b(false, false);
                renderGlobal.a(amk.c, partialTicks, 2, viewEntity);
                Shaders.checkGLError("shadow terrain cutout");
                mc.N().b(cdn.g).a();
                buq.j(7424);
                buq.a(516, 0.1f);
                buq.n(5888);
                buq.H();
                buq.G();
                mc.B.c("shadow entities");
                if (SMCEnvironment.hasForge) {
                    Dummy.invoke_forgeHooksClient_setRenderPass(0);
                }
                renderGlobal.a(viewEntity, frustum, partialTicks);
                Shaders.checkGLError("shadow entities");
                buq.n(5888);
                buq.H();
                buq.a(true);
                buq.l();
                buq.q();
                buq.a(770, 771, 1, 0);
                buq.a(516, 0.1f);
                if (Shaders.usedShadowDepthBuffers >= 2) {
                    GL13.glActiveTexture(33989);
                    Shaders.checkGLError("pre copy shadow depth");
                    GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
                    Shaders.checkGLError("copy shadow depth");
                    GL13.glActiveTexture(33984);
                }
                buq.l();
                buq.a(true);
                mc.N().a(cdn.g);
                buq.j(7425);
                Shaders.checkGLError("shadow pre-translucent");
                GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
                Shaders.checkGLError("shadow drawbuffers pre-translucent");
                Shaders.checkFramebufferStatus("shadow pre-translucent");
                mc.B.c("shadow translucent");
                renderGlobal.a(amk.d, partialTicks, 2, viewEntity);
                Shaders.checkGLError("shadow translucent");
                if (SMCEnvironment.hasForge) {
                    bhx.b();
                    Dummy.invoke_forgeHooksClient_setRenderPass(1);
                    renderGlobal.a(viewEntity, frustum, partialTicks);
                    Dummy.invoke_forgeHooksClient_setRenderPass(-1);
                    bhx.a();
                    Shaders.checkGLError("shadow entities 1");
                }
                buq.j(7424);
                buq.a(true);
                buq.q();
                buq.l();
                GL11.glFlush();
                Shaders.checkGLError("shadow flush");
                Shaders.isShadowPass = false;
                mc.t.aw = Shaders.preShadowPassThirdPersonView;
                mc.B.c("shadow postprocess");
                if (Shaders.hasGlGenMipmap) {
                    if (Shaders.usedShadowDepthBuffers >= 1) {
                        if (Shaders.shadowMipmapEnabled[0]) {
                            GL13.glActiveTexture(33988);
                            GL11.glBindTexture(3553, Shaders.sfbDepthTextures.get(0));
                            GL30.glGenerateMipmap(3553);
                            GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
                        }
                        if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
                            GL13.glActiveTexture(33989);
                            GL11.glBindTexture(3553, Shaders.sfbDepthTextures.get(1));
                            GL30.glGenerateMipmap(3553);
                            GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
                        }
                        GL13.glActiveTexture(33984);
                    }
                    if (Shaders.usedShadowColorBuffers >= 1) {
                        if (Shaders.shadowColorMipmapEnabled[0]) {
                            GL13.glActiveTexture(33997);
                            GL11.glBindTexture(3553, Shaders.sfbColorTextures.get(0));
                            GL30.glGenerateMipmap(3553);
                            GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
                        }
                        if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
                            GL13.glActiveTexture(33998);
                            GL11.glBindTexture(3553, Shaders.sfbColorTextures.get(1));
                            GL30.glGenerateMipmap(3553);
                            GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
                        }
                        GL13.glActiveTexture(33984);
                    }
                }
                Shaders.checkGLError("shadow postprocess");
                GL30.glBindFramebuffer(36160, Shaders.dfb);
                GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
                Shaders.activeDrawBuffers = null;
                mc.N().a(cdn.g);
                Shaders.useProgram(7);
                GL11.glMatrixMode(5888);
                GL11.glPopMatrix();
                GL11.glMatrixMode(5889);
                GL11.glPopMatrix();
                GL11.glMatrixMode(5888);
                Shaders.checkGLError("shadow end");
            }
        }
    }

    public static void preRenderChunkLayer() {
        if (cig.f()) {
            GL11.glEnableClientState(32885);
            GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
        }
    }

    public static void postRenderChunkLayer() {
        if (cig.f()) {
            GL11.glDisableClientState(32885);
            GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
        }
    }

    public static void setupArrayPointersVbo() {
        GL11.glVertexPointer(3, 5126, 56, 0L);
        GL11.glColorPointer(4, 5121, 56, 12L);
        GL11.glTexCoordPointer(2, 5126, 56, 16L);
        cig.l(cig.r);
        GL11.glTexCoordPointer(2, 5122, 56, 24L);
        cig.l(cig.q);
        GL11.glNormalPointer(5120, 56, 28L);
        GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);
        GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);
        GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
    }

    public static void beaconBeamBegin() {
        Shaders.useProgram(2);
    }

    public static void beaconBeamBegin1() {
    }

    public static void beaconBeamEnableBlend() {
        GL30.glEnablei(3042, 0);
    }

    public static void beaconBeamBegin2() {
        Shaders.useProgram(2);
    }

    public static void beaconBeamEnd() {
        GL30.glDisablei(3042, 0);
    }

    public static void layerArmorBaseDrawEnchantedGlintBegin() {
        Shaders.useProgram(17);
    }

    public static void layerArmorBaseDrawEnchantedGlintEnd() {
        Shaders.useProgram(16);
    }
}
