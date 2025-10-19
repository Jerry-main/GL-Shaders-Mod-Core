package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTileEntityBeaconRenderer.class */
public class STTileEntityBeaconRenderer implements IClassTransformer {
    private static final int vertexSize = 14;

    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTileEntityBeaconRenderer$CVTransform.class */
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
            if (Names.tileEntityBeaconRenderer_renderBeaconBeam.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVrenderBeaconBeam(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTileEntityBeaconRenderer$MVrenderBeaconBeam.class */
    private static class MVrenderBeaconBeam extends MethodVisitor {
        int count;

        public MVrenderBeaconBeam(MethodVisitor mv) {
            super(262144, mv);
            this.count = 0;
        }

        public void visitCode() {
            super.visitCode();
            this.mv.visitFieldInsn(178, "shadersmod/client/Shaders", "isShadowPass", "Z");
            Label l1 = new Label();
            this.mv.visitJumpInsn(153, l1);
            this.mv.visitInsn(177);
            this.mv.visitLabel(l1);
            this.mv.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
            this.mv.visitMethodInsn(184, Names.shadersRender_.clasName, "beaconBeamBegin", "()V");
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.glStateManager_enableBlend.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitMethodInsn(184, Names.shadersRender_.clasName, "beaconBeamEnableBlend", "()V");
            } else {
                if (Names.vertexBuffer_begin.equals(owner, name, desc)) {
                    if (this.count == 0) {
                        this.count++;
                        this.mv.visitMethodInsn(184, Names.shadersRender_.clasName, "beaconBeamBegin1", "()V");
                    } else if (this.count == 1) {
                        this.count++;
                        this.mv.visitMethodInsn(184, Names.shadersRender_.clasName, "beaconBeamBegin2", "()V");
                    }
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitMethodInsn(184, Names.shadersRender_.clasName, "beaconBeamEnd", "()V");
            }
            super.visitInsn(opcode);
        }
    }
}
