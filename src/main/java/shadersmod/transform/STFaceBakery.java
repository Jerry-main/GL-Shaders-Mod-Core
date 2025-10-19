package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STFaceBakery.class */
public class STFaceBakery implements IClassTransformer {
    private static final int vertexSize = 14;

    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STFaceBakery$CVTransform.class */
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
            if (Names.faceBakery_makeQuadVertexData.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVmakeQuadVertexData(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.faceBakery_storeVertexData.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVstoreVertex(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.faceBakery_getFacingFromVertexData.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVgetFacing(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.faceBakery_applyFacing.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVapplyFacing(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STFaceBakery$MVmakeQuadVertexData.class */
    private static class MVmakeQuadVertexData extends MethodVisitor {
        int count;

        public MVmakeQuadVertexData(MethodVisitor mv) {
            super(262144, mv);
            this.count = 0;
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 16 && operand == 28) {
                this.mv.visitIntInsn(opcode, 56);
                this.count++;
                if (this.count > 1) {
                    SMCLog.warning("Unexpected number of 28.");
                    return;
                }
                return;
            }
            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitInsn(int opcode) {
            if (opcode == 176) {
                this.mv.visitMethodInsn(184, Names.sBakedQuad_onMakeQuadData.clasName, Names.sBakedQuad_onMakeQuadData.name, Names.sBakedQuad_onMakeQuadData.desc);
            }
            super.visitInsn(opcode);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STFaceBakery$MVstoreVertex.class */
    private static class MVstoreVertex extends MethodVisitor {
        int count;

        public MVstoreVertex(MethodVisitor mv) {
            super(262144, mv);
            this.count = 0;
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 16 && operand == 7) {
                this.mv.visitIntInsn(opcode, 14);
                this.count++;
                if (this.count > 1) {
                    SMCLog.warning("Unexpected number of 7.");
                    return;
                }
                return;
            }
            this.mv.visitIntInsn(opcode, operand);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STFaceBakery$MVgetFacing.class */
    private static class MVgetFacing extends MethodVisitor {
        int count;

        public MVgetFacing(MethodVisitor mv) {
            super(262144, mv);
            this.count = 0;
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 16 && operand >= 7 && operand <= 16) {
                this.mv.visitIntInsn(opcode, ((operand / 7) * 14) + (operand % 7));
                this.count++;
                if (this.count > 6) {
                    SMCLog.warning("Unexpected number of 7-16.");
                    return;
                }
                return;
            }
            this.mv.visitIntInsn(opcode, operand);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STFaceBakery$MVapplyFacing.class */
    private static class MVapplyFacing extends MethodVisitor {
        int count;

        public MVapplyFacing(MethodVisitor mv) {
            super(262144, mv);
            this.count = 0;
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 16 && operand == 7) {
                this.mv.visitIntInsn(opcode, 14);
                this.count++;
                if (this.count > 3) {
                    SMCLog.warning("Unexpected number of 7.");
                    return;
                }
                return;
            }
            this.mv.visitIntInsn(opcode, operand);
        }
    }
}
