package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCEnvironment;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer.class */
public class STEntityRenderer implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (Names.entityRenderer_disableLightmap.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVdisableLightmap(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.entityRenderer_enableLightmap.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVenableLightmap(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.entityRenderer_updateFogColor.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVupdateFogColor(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.entityRenderer_setFogColorBuffer.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVsetFogColorBuffer(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.entityRenderer_setupFog.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVsetupFog(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.entityRenderer_renderCloudsCheck.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVrenderCloudsCheck(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.entityRenderer_renderHand.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVrenderHand(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.entityRenderer_renderWorld.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVrenderWorld(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.entityRenderer_renderWorldPass.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVrenderWorldPass(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            AccessorGenerator.generateFieldAccessor(this.cv, "frameCount", Names.entityRenderer_frameCount);
            AccessorGenerator.generatePrivateMethodAccessor(this.cv, "renderHand", Names.entityRenderer_renderHand);
            AccessorGenerator.generatePrivateMethodAccessor(this.cv, "setupCameraTransform", Names.entityRenderer_setupCameraTransform);
            super.visitEnd();
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVdisableLightmap.class */
    private static class MVdisableLightmap extends MethodVisitor {
        public MVdisableLightmap(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "disableLightmap", "()V");
            }
            this.mv.visitInsn(opcode);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVenableLightmap.class */
    private static class MVenableLightmap extends MethodVisitor {
        public MVenableLightmap(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "enableLightmap", "()V");
            }
            this.mv.visitInsn(opcode);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVupdateFogColor.class */
    private static class MVupdateFogColor extends MethodVisitor {
        public MVupdateFogColor(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.glStateManager_clearColor.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "setClearColor", "(FFFF)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVsetFogColorBuffer.class */
    private static class MVsetFogColorBuffer extends MethodVisitor {
        public MVsetFogColorBuffer(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(23, 1);
            this.mv.visitVarInsn(23, 2);
            this.mv.visitVarInsn(23, 3);
            this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "setFogColor", "(FFF)V");
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVsetupFog.class */
    private static class MVsetupFog extends MethodVisitor {
        public MVsetupFog(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.glStateManager_setFog.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "setFog", "(I)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderCloudsCheck.class */
    private static class MVrenderCloudsCheck extends MethodVisitor {
        public MVrenderCloudsCheck(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.gameSettings_shouldRenderClouds.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "shouldRenderClouds", "(" + Names.gameSettings_.desc + ")I");
            } else {
                if (Names.renderGlobal_renderClouds.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginClouds", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endClouds", "()V");
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderHand.class */
    private static class MVrenderHand extends MethodVisitor {
        Label la1;
        Label la2;

        public MVrenderHand(MethodVisitor mv) {
            super(262144, mv);
            this.la1 = new Label();
            this.la2 = new Label();
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.equals("org/lwjgl/util/glu/Project", "gluPerspective", "(FFFF)V", owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "applyHandDepth", "()V");
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                return;
            }
            if (Names.glStateManager_pushMatrix.equals(owner, name, desc)) {
                this.mv.visitFieldInsn(178, "shadersmod/client/Shaders", "isSleeping", "Z");
                this.mv.visitVarInsn(54, 4);
                this.mv.visitFieldInsn(178, "shadersmod/client/Shaders", "isHandRendered", "Z");
                this.mv.visitJumpInsn(154, this.la1);
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                return;
            }
            if (Names.glStateManager_popMatrix.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitLabel(this.la1);
                this.mv.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
                this.mv.visitFieldInsn(178, "shadersmod/client/Shaders", "isCompositeRendered", "Z");
                this.mv.visitJumpInsn(154, this.la2);
                this.mv.visitInsn(177);
                this.mv.visitLabel(this.la2);
                this.mv.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(182, Names.entityRenderer_disableLightmap.clasName, Names.entityRenderer_disableLightmap.name, Names.entityRenderer_disableLightmap.desc);
                return;
            }
            if (Names.itemRenderer_renderItemInFirstPerson.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "renderItemFP", "(" + Names.itemRenderer_.desc + "F)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorld.class */
    private static class MVrenderWorld extends MethodVisitor {
        int state;

        public MVrenderWorld(MethodVisitor mv) {
            super(262144, mv);
            this.state = 0;
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            switch (this.state) {
                case 0:
                    if (Names.entityRenderer_getMouseOver.equals(owner, name, desc)) {
                        this.state++;
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitFieldInsn(180, Names.entityRenderer_mc.clasName, Names.entityRenderer_mc.name, Names.entityRenderer_mc.desc);
                        this.mv.visitVarInsn(23, 1);
                        this.mv.visitVarInsn(22, 2);
                        this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginRender", "(" + Names.minecraft_.desc + "FJ)V");
                        return;
                    }
                    break;
            }
            super.visitMethodInsn(opcode, owner, name, desc);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass.class */
    private static class MVrenderWorldPass extends MethodVisitor {
        MethodVisitor mvOut;
        Label labelNoSky;
        Label labelEndRender;

        public MVrenderWorldPass(MethodVisitor mv) {
            super(262144);
            this.labelNoSky = null;
            this.labelEndRender = null;
            this.mvOut = mv;
            setNextState(new State0());
        }

        void setNextState(MethodVisitor next) {
            this.mv = next;
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$MVState.class */
        class MVState extends MethodVisitor {
            public MVState() {
                super(262144, MVrenderWorldPass.this.mvOut);
            }

            public void visitEnd() {
                if (getClass() != StateEnd.class) {
                    SMCLog.error("  ends in bad state %s", getClass().getSimpleName());
                }
                this.mv.visitEnd();
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State0.class */
        class State0 extends MVState {
            State0() {
                super();
            }

            public void visitCode() {
                this.mv.visitCode();
                this.mv.visitVarInsn(21, 1);
                this.mv.visitVarInsn(23, 2);
                this.mv.visitVarInsn(22, 3);
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginRenderPass", "(IFJ)V");
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.glStateManager_viewport.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "setViewport", "(IIII)V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State1());
                } else {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State1.class */
        class State1 extends MVState {
            State1() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.glStateManager_clear.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "clearRenderBuffer", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State2());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State2.class */
        class State2 extends MVState {
            State2() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.entityRenderer_setupCameraTransform.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitVarInsn(23, 2);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "setCamera", "(F)V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State2a());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State2a.class */
        class State2a extends MVState {
            State2a() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.equals(Names.frustum_.clasName, "<init>", "()V", owner, name, desc)) {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State2b());
                } else {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State2b.class */
        class State2b extends MVState {
            State2b() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.iCamera_setPosition.equals(owner, name, desc) || Names.frustrum_setPosition.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "setFrustrumPosition", "(" + Names.frustum_.desc + "DDD)V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State3());
                } else {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State3.class */
        class State3 extends MVState {
            State3() {
                super();
            }

            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (Names.gameSettings_renderDistance.equals(owner, name)) {
                    this.mv.visitFieldInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State4());
                } else {
                    this.mv.visitFieldInsn(opcode, owner, name, desc);
                }
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.equals("Config", "isSkyEnabled", "()Z", owner, name, desc)) {
                    this.mv.visitFieldInsn(178, "shadersmod/client/Shaders", "isShadowPass", "Z");
                    MVrenderWorldPass.this.labelNoSky = new Label();
                    this.mv.visitJumpInsn(154, MVrenderWorldPass.this.labelNoSky);
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State6());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State4.class */
        class State4 extends MVState {
            State4() {
                super();
            }

            public void visitJumpInsn(int opcode, Label label) {
                if (opcode == 161) {
                    this.mv.visitInsn(88);
                    this.mv.visitFieldInsn(178, "shadersmod/client/Shaders", "isShadowPass", "Z");
                    this.mv.visitJumpInsn(154, label);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State5());
                    return;
                }
                this.mv.visitJumpInsn(opcode, label);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State5.class */
        class State5 extends MVState {
            State5() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderSky.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginSky", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endSky", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State8());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State6.class */
        class State6 extends MVState {
            State6() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderSky.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginSky", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endSky", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State7());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State7.class */
        class State7 extends State8 {
            State7() {
                super();
            }

            @Override // shadersmod.transform.STEntityRenderer.MVrenderWorldPass.State8
            public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
                this.mv.visitLabel(MVrenderWorldPass.this.labelNoSky);
                MVrenderWorldPass.this.labelNoSky = null;
                super.visitFrame(type, nLocal, local, nStack, stack);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State8.class */
        class State8 extends MVState {
            State8() {
                super();
            }

            public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
                this.mv.visitFrame(type, nLocal, local, nStack, stack);
                MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State9());
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State9.class */
        class State9 extends MVState {
            State9() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_setupTerrain.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "setupTerrain", "(" + Names.renderGlobal_.desc + Names.entity_.desc + "D" + Names.iCamera_.desc + "IZ)V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State10());
                } else {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State10.class */
        class State10 extends MVState {
            State10() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_updateChunks.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "updateChunks", "(" + Names.renderGlobal_.desc + "J)V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State11());
                } else {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State11.class */
        class State11 extends MVState {
            State11() {
                super();
            }

            public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
                this.mv.visitFrame(type, nLocal, local, nStack, stack);
                MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State12());
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State12.class */
        class State12 extends MVState {
            State12() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderBlockLayer4.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "beginTerrainSolid", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State12a());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State12a.class */
        class State12a extends MVState {
            State12a() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderBlockLayer4.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "beginTerrainCutoutMipped", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State12b());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State12b.class */
        class State12b extends MVState {
            State12b() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderBlockLayer4.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "beginTerrainCutout", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "endTerrain", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State12c());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State12c.class */
        class State12c extends MVState {
            State12c() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderEntities.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State13());
                } else if (owner.equals(Names.ofReflector_.clasName)) {
                    SMCEnvironment.hasOptiFine = true;
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                } else if (owner.equals(Names.forgeHooksClient_.clasName)) {
                    SMCEnvironment.hasForge = true;
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                } else {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State13.class */
        class State13 extends MVState {
            State13() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.effectRenderer_renderLitParticles.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginLitParticles", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State14());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State14.class */
        class State14 extends MVState {
            State14() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.effectRenderer_renderParticles.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginParticles", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endParticles", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State16());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State16.class */
        class State16 extends MVState {
            State16() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.entityRenderer_renderRainSnow.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginWeather", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endWeather", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State17());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State17.class */
        class State17 extends MVState {
            State17() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.glStateManager_depthMask.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitVarInsn(23, 2);
                    this.mv.visitVarInsn(21, 1);
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "renderHand0", "(" + Names.entityRenderer_.desc + "FI)V");
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "preWater", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State18());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State18.class */
        class State18 extends MVState {
            State18() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderBlockLayer4.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginWater", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endWater", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State21());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State19.class */
        class State19 extends MVState {
            State19() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderBlockLayer4.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginWater", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endWater", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State21());
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State21.class */
        class State21 extends MVState {
            State21() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.entityRenderer_renderCloudsCheck.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State22());
                } else {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State22.class */
        class State22 extends MVState {
            State22() {
                super();
            }

            public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
                this.mv.visitFrame(type, nLocal, local, nStack, stack);
                MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State23());
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State23.class */
        class State23 extends MVState {
            State23() {
                super();
            }

            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (Names.entityRenderer_shouldRenderHand.equals(owner, name)) {
                    this.mv.visitFieldInsn(opcode, owner, name, desc);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State28());
                } else {
                    this.mv.visitFieldInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State28.class */
        class State28 extends MVState {
            State28() {
                super();
            }

            public void visitJumpInsn(int opcode, Label label) {
                if (opcode == 153) {
                    this.mv.visitJumpInsn(opcode, label);
                    this.mv.visitFieldInsn(178, "shadersmod/client/Shaders", "isShadowPass", "Z");
                    this.mv.visitJumpInsn(154, label);
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitVarInsn(23, 2);
                    this.mv.visitVarInsn(21, 1);
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "renderHand1", "(" + Names.entityRenderer_.desc + "FI)V");
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "renderCompositeFinal", "()V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State29());
                    return;
                }
                this.mv.visitJumpInsn(opcode, label);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State29.class */
        class State29 extends MVState {
            State29() {
                super();
            }

            public void visitVarInsn(int opcode, int var) {
                if (opcode == 25) {
                    this.mv.visitVarInsn(opcode, var);
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State30());
                } else {
                    this.mv.visitVarInsn(opcode, var);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State30.class */
        class State30 extends MVState {
            State30() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.entityRenderer_renderHand.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "renderFPOverlay", "(" + Names.entityRenderer_.desc + "FI)V");
                    MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State31());
                } else {
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State31.class */
        class State31 extends MVState {
            State31() {
                super();
            }

            public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
                this.mv.visitFrame(type, nLocal, local, nStack, stack);
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endRender", "()V");
                MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new StateEnd());
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$State99.class */
        class State99 extends MVState {
            State99() {
                super();
            }

            /* renamed from: a */
            void m20a() {
                MVrenderWorldPass.this.setNextState(MVrenderWorldPass.this.new State99());
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STEntityRenderer$MVrenderWorldPass$StateEnd.class */
        class StateEnd extends MVState {
            StateEnd() {
                super();
            }

            @Override // shadersmod.transform.STEntityRenderer.MVrenderWorldPass.MVState
            public void visitEnd() {
                this.mv.visitEnd();
            }
        }
    }
}
