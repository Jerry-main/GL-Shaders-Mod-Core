package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal.class */
public class STRenderGlobal implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            return this.cv.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (Names.renderGlobal_setupTerrain.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVsetupTerrain(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.renderGlobal_renderSky.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVrenderSky(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.renderGlobal_renderBlockLayer1.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVrenderBlockLayer1(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.renderGlobal_renderEntities.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVrenderEntities(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.renderGlobal_preRenderBlockDamage.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVpreRenderBlockDamage(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.renderGlobal_postRenderBlockDamage.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVpostRenderBlockDamage(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.renderGlobal_drawSelectionBox.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVendisTexFog(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities.class */
    private static class MVrenderEntities extends MethodVisitor {
        MethodVisitor mvOut;

        public MVrenderEntities(MethodVisitor mv) {
            super(262144);
            this.mvOut = mv;
            this.mv = new State0();
        }

        void setNextState(MVState state) {
            this.mv = state;
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$MVState.class */
        class MVState extends MethodVisitor {
            MVState() {
                super(262144, MVrenderEntities.this.mvOut);
            }

            public void visitEnd() {
                if (getClass() != StateEnd.class) {
                    SMCLog.error("  ends in bad state %s", getClass().getSimpleName());
                }
                this.mv.visitEnd();
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State0.class */
        class State0 extends MVState {
            State0() {
                super();
            }

            public void visitLdcInsn(Object cst) {
                if (cst instanceof String) {
                    String scst = (String) cst;
                    if (scst.equals("entities")) {
                        MVrenderEntities.this.setNextState(MVrenderEntities.this.new State1());
                    }
                }
                this.mv.visitLdcInsn(cst);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State1.class */
        class State1 extends MVState {
            State1() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginEntities", "()V");
                MVrenderEntities.this.setNextState(MVrenderEntities.this.new State2());
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State2.class */
        class State2 extends MVState {
            State2() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderManager_renderEntityFirst.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "nextEntity", "()V");
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                } else {
                    if (Names.renderManager_renderEntitySecond.equals(owner, name, desc)) {
                        this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "nextEntity", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        MVrenderEntities.this.setNextState(MVrenderEntities.this.new State3());
                        return;
                    }
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State3.class */
        class State3 extends MVState {
            State3() {
                super();
            }

            public void visitLdcInsn(Object cst) {
                if (cst instanceof String) {
                    String scst = (String) cst;
                    if (scst.equals("blockentities")) {
                        MVrenderEntities.this.setNextState(MVrenderEntities.this.new State4());
                    }
                }
                this.mv.visitLdcInsn(cst);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State4.class */
        class State4 extends MVState {
            State4() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endEntities", "()V");
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "beginBlockEntities", "()V");
                MVrenderEntities.this.setNextState(MVrenderEntities.this.new State5());
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State5.class */
        class State5 extends MVState {
            State5() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                if (Names.equals("java/util/Iterator", "next", "()Ljava/lang/Object;", owner, name, desc)) {
                    MVrenderEntities.this.setNextState(MVrenderEntities.this.new State6());
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State6.class */
        class State6 extends MVState {
            State6() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                if (Names.equals("java/util/Iterator", "next", "()Ljava/lang/Object;", owner, name, desc)) {
                    MVrenderEntities.this.setNextState(MVrenderEntities.this.new State7());
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State7.class */
        class State7 extends MVState {
            State7() {
                super();
            }

            public void visitVarInsn(int opcode, int var) {
                this.mv.visitVarInsn(opcode, var);
                if (opcode == 58) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "nextBlockEntity", "()V");
                    MVrenderEntities.this.setNextState(MVrenderEntities.this.new State11());
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State11.class */
        class State11 extends MVState {
            State11() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_preRenderBlockDamage.equals(owner, name, desc)) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "endBlockEntities", "()V");
                    MVrenderEntities.this.setNextState(MVrenderEntities.this.new State12());
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$State12.class */
        class State12 extends MVState {
            State12() {
                super();
            }

            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                if (Names.renderGlobal_postRenderBlockDamage.equals(owner, name, desc)) {
                    MVrenderEntities.this.setNextState(MVrenderEntities.this.new StateEnd());
                }
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderEntities$StateEnd.class */
        class StateEnd extends MVState {
            StateEnd() {
                super();
            }

            @Override // shadersmod.transform.STRenderGlobal.MVrenderEntities.MVState
            public void visitEnd() {
                this.mv.visitEnd();
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVendisTexFog.class */
    private static class MVendisTexFog extends MethodVisitor {
        public MVendisTexFog(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            this.mv.visitMethodInsn(opcode, owner, name, desc);
            if (Names.glStateManager_enableFog.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "enableFog", "()V");
                return;
            }
            if (Names.glStateManager_disableFog.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "disableFog", "()V");
            } else if (Names.glStateManager_enableTexture2D.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "enableTexture2D", "()V");
            } else if (Names.glStateManager_disableTexture2D.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "disableTexture2D", "()V");
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderSky.class */
    private static class MVrenderSky extends MethodVisitor {
        int state;
        int lastVar;

        public MVrenderSky(MethodVisitor mv) {
            super(262144, mv);
            this.state = 0;
            this.lastVar = 0;
        }

        public void visitVarInsn(int opcode, int var) {
            this.mv.visitVarInsn(opcode, var);
            if (opcode == 25) {
                this.lastVar = var;
            } else {
                this.lastVar = 0;
            }
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            switch (this.state) {
                case 0:
                    if (Names.vec3_xCoord.equals(owner, name)) {
                        this.state++;
                        this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "setSkyColor", "(" + Names.vec3d_.desc + ")V");
                        this.mv.visitVarInsn(25, this.lastVar);
                        break;
                    }
                    break;
            }
            this.mv.visitFieldInsn(opcode, owner, name, desc);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            switch (this.state) {
                case 1:
                    if (Names.glStateManager_color3.equals(owner, name, desc)) {
                        this.state++;
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }
                    break;
                case 2:
                    if (Names.glStateManager_color3.equals(owner, name, desc)) {
                        this.state++;
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "preSkyList", "()V");
                        return;
                    }
                    break;
                case 3:
                    if (Names.worldClient_getRainStrength.equals(owner, name, desc)) {
                        this.state++;
                        break;
                    }
                    break;
            }
            this.mv.visitMethodInsn(opcode, owner, name, desc);
            if (Names.glStateManager_enableFog.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "enableFog", "()V");
                return;
            }
            if (Names.glStateManager_disableFog.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "disableFog", "()V");
                return;
            }
            if (Names.glStateManager_enableTexture2D.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "enableTexture2D", "()V");
                return;
            }
            if (Names.glStateManager_disableTexture2D.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "disableTexture2D", "()V");
                return;
            }
            if (Names.glStateManager_rotate.equals(owner, name, desc)) {
                if (this.state == 4) {
                    this.state++;
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "preCelestialRotate", "()V");
                } else if (this.state == 5) {
                    this.state++;
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "postCelestialRotate", "()V");
                }
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVpreRenderBlockDamage.class */
    private static class MVpreRenderBlockDamage extends MethodVisitor {
        public MVpreRenderBlockDamage(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "beginBlockDamage", "()V");
            }
            this.mv.visitInsn(opcode);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVpostRenderBlockDamage.class */
    private static class MVpostRenderBlockDamage extends MethodVisitor {
        public MVpostRenderBlockDamage(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "endBlockDamage", "()V");
            }
            this.mv.visitInsn(opcode);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVrenderBlockLayer1.class */
    private static class MVrenderBlockLayer1 extends MethodVisitor {
        public MVrenderBlockLayer1(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.chunkRenderContainer_renderChunkLayer.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "preRenderChunkLayer", "()V");
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersRender", "postRenderChunkLayer", "()V");
                return;
            }
            this.mv.visitMethodInsn(opcode, owner, name, desc);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVsetupTerrain.class */
    private static class MVsetupTerrain extends MethodVisitor {
        MethodVisitor mvOut;

        public MVsetupTerrain(MethodVisitor mv) {
            super(262144);
            this.mvOut = mv;
            setNextState(new State0());
        }

        void setNextState(MethodVisitor next) {
            this.mv = next;
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVsetupTerrain$MVState.class */
        class MVState extends MethodVisitor {
            public MVState() {
                super(262144, MVsetupTerrain.this.mvOut);
            }

            public void visitEnd() {
                if (getClass() != StateEnd.class) {
                    SMCLog.error("  ends in bad state %s", getClass().getSimpleName());
                }
                this.mv.visitEnd();
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVsetupTerrain$State0.class */
        class State0 extends MVState {
            State0() {
                super();
            }

            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (Names.renderGlobal_renderDispatcher.equals(owner, name, desc)) {
                    this.mv.visitInsn(87);
                    this.mv.visitFieldInsn(178, "shadersmod/client/Shaders", "isShadowPass", "Z");
                    Label label1 = new Label();
                    this.mv.visitJumpInsn(153, label1);
                    this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "mcProfilerEndSection", "()V");
                    this.mv.visitInsn(177);
                    this.mv.visitLabel(label1);
                    this.mv.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
                    this.mv.visitVarInsn(25, 0);
                    MVsetupTerrain.this.setNextState(MVsetupTerrain.this.new StateEnd());
                }
                this.mv.visitFieldInsn(opcode, owner, name, desc);
            }
        }

        /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderGlobal$MVsetupTerrain$StateEnd.class */
        class StateEnd extends MVState {
            StateEnd() {
                super();
            }

            @Override // shadersmod.transform.STRenderGlobal.MVsetupTerrain.MVState
            public void visitEnd() {
                this.mv.visitEnd();
            }
        }
    }
}
